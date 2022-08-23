package org.soundwhere.backend.audio.dto;

import org.soundwhere.backend.audio.AudioFileFormat;

public class AudioMetaDTO {

    public boolean _success;
    public int sampleRate;
    public long sampleN;
    public int channelN;
    public int bitDepth;
    public String repr;
    public AudioFileFormat format;
    
    public long frontBlank; // sample
    public long backBlank; // sample
}
