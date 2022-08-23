package org.soundwhere.backend.audio;

import org.soundwhere.backend.audio.dto.AudioMetaDTO;
import org.soundwhere.backend.audio.dto.KeysDTO;
import org.soundwhere.backend.audio.dto.RegisterKeysDTO;
import org.soundwhere.backend.audio.dto.TagDTO;
import org.soundwhere.backend.err.LogicError;
import org.soundwhere.backend.util.ClientCreator;
import org.soundwhere.backend.util.Res;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rest/audio")
public class AudioCont {

    private final AudioService service;
    private final ClientCreator clientCreator;
    private final RestTemplate restTemplate;

    public AudioCont(AudioService service, ClientCreator clientCreator,RestTemplate restTemplate) {
        this.service = service;
        this.clientCreator = clientCreator;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/binary")
    public Res uploadBinary(
        @RequestPart(value = "key", required = false) String key,
        @RequestPart("file") MultipartFile file
    ) throws LogicError {
        service.storeFile(file);
        return Res.ok();
    }

    @PostMapping("/keys")
    public Res registerKeys(@RequestBody RegisterKeysDTO registerKeysDTO) {
        var keyMap = service.keys(registerKeysDTO);
        return Res.ok(new KeysDTO(keyMap));
    }

    @GetMapping("/test")
    public Res test() {
        var client = clientCreator.create();
        var res = client.get().uri("/meta?audioId=12").retrieve();
        var meta = res.bodyToMono(AudioMetaDTO.class).block();
        System.out.println(meta);
        return Res.ok(meta);
    }

    @PostMapping("/classification")
    public Res classification(
        @RequestPart("file") MultipartFile audio
    ) {
        OutputStream os = null;
        InputStream inputStream = null;
        String fileName = null;
        try {
            inputStream = audio.getInputStream();
            fileName = audio.getOriginalFilename();
            System.out.println(inputStream);
            System.out.println(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String path = "local/audio"; //随机生成文件名
            byte[] bs = new byte[1024]; // 1K的数据缓冲
            int len;
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String url = "http://127.0.0.1:5000/getTag?path={path}";
        Map map = new HashMap();
        map.put("path", "D:/school/audio/back/backend/local/audio");
        String res = restTemplate.getForObject(url, String.class, map);
        //String res=restTemplate.postForObject(url,entity,String.class);
        System.out.println(res);
        String[] tagIndex=res.split(" ");
        TagDTO tag=new TagDTO();
        tag.setBig_class(Integer.valueOf(tagIndex[1]));
        tag.setSmall_class(Integer.valueOf(tagIndex[0]));
        return Res.ok(tag);
    }
}
