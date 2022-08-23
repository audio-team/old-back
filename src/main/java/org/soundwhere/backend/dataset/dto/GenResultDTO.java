package org.soundwhere.backend.dataset.dto;

public class GenResultDTO {
    public String name;
    public String tmpSecretId;
    public String tmpSecretKey;
    public String sessionToken;

    public GenResultDTO(String name, String tmpSecretId, String tmpSecretKey, String sessionToken) {
        this.name = name;
        this.tmpSecretId = tmpSecretId;
        this.tmpSecretKey = tmpSecretKey;
        this.sessionToken = sessionToken;
    }
}
