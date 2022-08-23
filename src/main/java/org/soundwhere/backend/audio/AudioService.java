package org.soundwhere.backend.audio;

import org.soundwhere.backend.audio.dto.AudioMetaDTO;
import org.soundwhere.backend.audio.dto.RegisterKeysDTO;
import org.soundwhere.backend.audio.qo.DurationInfoQO;
import org.soundwhere.backend.dataset.dto.TaskType;
import org.soundwhere.backend.err.ErrorCode;
import org.soundwhere.backend.err.LogicError;
import org.soundwhere.backend.util.ClientCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipInputStream;

@Service
public class AudioService {
    private final File audioPath;
    private final AudioRepo repo;
    private final AudioLabelRepo labelRepo;
    private final ClientCreator clientCreator;
    private final AudioMap audioMap;

    public AudioService(@Value("${soundwhere.localPath}") String localPath, AudioRepo repo, AudioLabelRepo labelRepo, ClientCreator clientCreator, AudioMap audioMap) {
        audioPath = new File(localPath, "audio");

        this.repo = repo;
        this.labelRepo = labelRepo;
        this.clientCreator = clientCreator;
        this.audioMap = audioMap;
    }

    public void storeFile(MultipartFile file) throws LogicError {
        try (var zipStream = new ZipInputStream(file.getInputStream())) {
            while (true) {
                var entry = zipStream.getNextEntry();
                if (entry == null) break;
                if (entry.isDirectory()) continue;

                var parts = entry.getName().split("/");
                var key = parts[parts.length - 1];
                var destFile = new File(audioPath, key);

                try (var fileStream = new FileOutputStream(destFile)) {
                    var buffer = new byte[1024];
                    while (true) {
                        int len = zipStream.read(buffer);
                        if (len <= 0) break;
                        fileStream.write(buffer, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new LogicError(ErrorCode.SystemPathError);
                }

                var client = clientCreator.create();
                var res = client.get().uri("/meta?audioId=" + key).retrieve();
                var meta = res.bodyToMono(AudioMetaDTO.class).block();
                assert meta != null;
                if (!meta._success) {
                    System.err.println("Flask error to audio file with key " + key);
                    continue;
                }
                var mayAudio = repo.findById(Long.valueOf(key));
                mayAudio.ifPresent(audio -> {
                    audioMap.updateAudioMeta(meta, audio);
                    var blankValue = meta.frontBlank + "_" + meta.backBlank;
                    var blankLabel = new AudioLabel(blankValue, "Blank", MetaType.Property);
                    blankLabel.setAudio(audio);
                    labelRepo.save(blankLabel);
                    audio.setState(Audio.State.Available);
                    repo.save(audio);
                });

            }
        } catch (IOException e) {
            throw new LogicError(ErrorCode.CorruptedInput);
        }

        System.out.println("END");
    }

    public HashMap<String, Long> keys(RegisterKeysDTO registerKeysDTO) {
        var keyMap = new HashMap<String, Long>();
        for (var tuple : registerKeysDTO.audios) {
            var audio = new Audio();
            audio.setSource(registerKeysDTO.datasetID);
            repo.save(audio);
            keyMap.put(tuple.id, audio.getId());
            for (var metaDTO : tuple.customMeta) {
                var audioMeta = audioMap.fromCustom(metaDTO);
                audioMeta.setAudio(audio);
                labelRepo.save(audioMeta);
            }
        }
        return keyMap;
    }

    public HashSet<Long> audioSetWithTaskLabel(TaskType type) {
        return labelRepo.withTaskLabel(type.name());
    }

    public HashSet<DurationInfoQO> durationInfo(double duration, double deltaRatio) {
        return repo.durationInfo(duration * (1 - deltaRatio), duration * (1 + deltaRatio));
    }

    public List<Audio> convert(HashSet<Long> audioSet) {
        return repo.batchById(audioSet);
    }

    public void classiFile(MultipartFile audio){

    }
}
