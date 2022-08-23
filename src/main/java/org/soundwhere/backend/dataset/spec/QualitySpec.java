package org.soundwhere.backend.dataset.spec;

import org.soundwhere.backend.audio.Audio;
import org.soundwhere.backend.audio.AudioService;

import java.util.HashSet;

public class QualitySpec extends GenSpec {
    private int[] sampleRate;
    private boolean lossless;

    public int[] getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int[] sampleRate) {
        this.sampleRate = sampleRate;
    }

    public boolean isLossless() {
        return lossless;
    }

    public void setLossless(boolean lossless) {
        this.lossless = lossless;
    }

    @Override
    public boolean degrade() {
        return false;
    }

    @Override
    public HashSet<Long> audioSet(AudioService audioService) {
        return null;
    }
}
