package org.soundwhere.backend.audio.dto;

import java.util.HashMap;

public class KeysDTO {
    public HashMap<String, Long> keys;

    public KeysDTO(HashMap<String, Long> keys) {
        this.keys = keys;
    }

    public KeysDTO() {
    }
}
