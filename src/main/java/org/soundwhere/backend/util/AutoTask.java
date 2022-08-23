package org.soundwhere.backend.util;

import org.soundwhere.backend.audio.AudioService;
import org.soundwhere.backend.err.LogicError;
import org.soundwhere.backend.user.UserService;
import org.soundwhere.backend.user.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
@EnableScheduling
public class AutoTask {

    @Value("${soundwhere.localPath}")
    private String localPath;
    
    private final UserService userService;
    private final AudioService audioService;

    public AutoTask(UserService userService, AudioService audioService) {
        this.userService = userService;
        this.audioService = audioService;
    }

    @PostConstruct
    public void post() {
        createDir();
        templateData();
    }

    private void createDir() {
        var genTempPath = new File(localPath, "generation-temporary");
        if (!genTempPath.exists()) {
            genTempPath.mkdirs();
        }
        var audioPath = new File(this.localPath, "audio");
        if (!audioPath.exists()) {
            audioPath.mkdirs();
        }
    }

    private void templateData() {
        try {
            userService.register(new RegisterDTO("user", "x", "user@qq.com"));
        } catch (LogicError error) {
            System.err.println("Impossible");
        }
    }
}
