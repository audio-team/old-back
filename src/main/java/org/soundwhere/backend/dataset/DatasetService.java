package org.soundwhere.backend.dataset;

import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.transfer.TransferManager;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Credentials;
import org.soundwhere.backend.audio.AudioService;
import org.soundwhere.backend.dataset.dto.GenResultDTO;
import org.soundwhere.backend.dataset.dto.TaskType;
import org.soundwhere.backend.dataset.spec.GenSpec;
import org.soundwhere.backend.dataset.spec.GenSpecForDataset;
import org.soundwhere.backend.err.ErrorCode;
import org.soundwhere.backend.err.LogicError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DatasetService {

    @Value("${soundwhere.secretId}")
    private String secretId;
    @Value("${soundwhere.secretKey}")
    private String secretKey;
    @Value("${soundwhere.bucketName}")
    private String bucketName;
    @Value("${soundwhere.bucketRegion}")
    private String bucketRegion;

    private final File genTemp;
    private final File audioPath;
    private final AudioService audioService;
    private final TransferManager transferManager;

    public DatasetService(@Value("${soundwhere.localPath}") String localPath, AudioService audioService, TransferManager transferManager) {
        genTemp = new File(localPath, "generation-temporary");
        audioPath = new File(localPath, "audio");

        this.audioService = audioService;
        this.transferManager = transferManager;
    }

    public GenResultDTO generate(GenSpec[] specs) throws LogicError {
        var audioSpecs = new ArrayList<GenSpec>();
        GenSpecForDataset scaleSpec = null;
        for (var spec : specs) {
            if (spec.name.equals("Dataset Scale")) {
                scaleSpec = (GenSpecForDataset) spec;
            } else {
                audioSpecs.add(spec);
            }
        }
        if (scaleSpec == null) {
            throw new LogicError(ErrorCode.NoScaleSpec);
        }

        var all = audioService.audioSetWithTaskLabel(TaskType.ASR); // TODO

        var okWithSpec = new HashMap<GenSpec, HashSet<Long>>();
        for (var audioSpec : audioSpecs) {
            okWithSpec.put(audioSpec, audioSpec.audioSet(audioService));
        }

        HashSet<Long> okWithAll;
        var nextToDegrade = specs.length - 1;
        while (true) {
            okWithAll = new HashSet<>(all);
            for (var audioSet : okWithSpec.values()) {
                okWithAll.retainAll(audioSet);
            }
            var ok = scaleSpec.actOn(okWithAll);
            if (ok) break;
            while (true) {
                var nextSpec = specs[nextToDegrade];
                ok = nextSpec.degrade();
                if (!ok) {
                    if (nextToDegrade == 0) {
                        throw new LogicError(ErrorCode.FailToGenerate);
                    }
                    nextToDegrade -= 1;
                    continue;
                }
                var newSet = nextSpec.audioSet(audioService);
                if (null != newSet) okWithSpec.put(nextSpec, newSet);
                break;
            }
        }

        var randomName = buildDataset(okWithAll);
        var credentials = uploadToCOS(randomName);

        return new GenResultDTO(
            randomName, credentials.tmpSecretId, credentials.tmpSecretKey, credentials.sessionToken);
    }

    private String buildDataset(HashSet<Long> audioSet) {
        var audioInfoSet = audioService.convert(audioSet);

        var name = UUID.randomUUID() + ".zip";
        try (
            var zipFile = new FileOutputStream(new File(genTemp, name));
            var zip = new ZipOutputStream(zipFile)
        ) {
            for (var audio : audioInfoSet) {
                var id = audio.getId();
                var file = new File(audioPath, id.toString());
                try (var is = new FileInputStream(file)) {
                    var entry = new ZipEntry(name + "/Speech/" + id + audio.getFormat().suffix());
                    zip.putNextEntry(entry);
                    is.transferTo(zip);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    private Credentials uploadToCOS(String name) {
        var file = new File(genTemp, name);
        var request = new PutObjectRequest(bucketName, name, file);
        try {
            var upload = transferManager.upload(request);
            var result = upload.waitForUploadResult();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        var tokenConfig = new TreeMap<String, Object>();
        try {
            tokenConfig.put("secretId", secretId);
            tokenConfig.put("secretKey", secretKey);
            tokenConfig.put("duration", 86400);
            tokenConfig.put("bucket", bucketName);
            tokenConfig.put("region", bucketRegion);
            tokenConfig.put("allowPrefixes", new String[]{
                name
            });
            tokenConfig.put("allowActions", new String[]{
                "name/cos:GetObject"
            });
            var response = CosStsClient.getCredential(tokenConfig);
            return response.credentials;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("No valid token generated.");
        }
    }
}
