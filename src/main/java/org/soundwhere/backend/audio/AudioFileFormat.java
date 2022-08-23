package org.soundwhere.backend.audio;

public enum AudioFileFormat {
    Flac, Wav, Mp3, Unknown;

    public String suffix() {
        switch (this) {
            case Flac:
                return ".flac";
            case Wav:
                return ".wav";
            case Mp3:
                return ".mp3";
            case Unknown:
                return "";
        }
        return "";
    }
}
