package org.soundwhere.backend.audio.qo;

import org.soundwhere.backend.audio.Audio;

public class DurationInfoQO {
    public Audio audio;
    public long frontBlank;
    public long backBlank;

    public DurationInfoQO(Audio audio, String blank) {
        this.audio = audio;
        var blanks = blank.split("_");
        this.frontBlank = Long.parseLong(blanks[0]);
        this.backBlank = Long.parseLong(blanks[1]);
    }

    @Override
    public String toString() {
        return "DurationInfoQO{" +
            "audio=" + audio +
            ", frontBlank=" + frontBlank +
            ", backBlank=" + backBlank +
            '}';
    }
}
