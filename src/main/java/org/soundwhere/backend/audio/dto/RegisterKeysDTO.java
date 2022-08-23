package org.soundwhere.backend.audio.dto;

public class RegisterKeysDTO {
    public AudioTuple[] audios;
    public String datasetID;

    public static class AudioTuple {
        public String id;
        public CustomMetaDTO[] customMeta;
    }

}
