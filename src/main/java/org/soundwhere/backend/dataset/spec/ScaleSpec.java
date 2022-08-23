package org.soundwhere.backend.dataset.spec;

import org.soundwhere.backend.audio.Audio;
import org.soundwhere.backend.audio.AudioService;

import java.util.HashSet;

public class ScaleSpec extends GenSpecForDataset {
    private int lower;
    private int upper;
    private int lowerRatio = 1;

    public int getLower() {
        return lower;
    }

    public void setLower(int lower) {
        this.lower = lower;
    }

    public int getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }

    @Override
    public boolean actOn(HashSet<Long> dataset) {
        if (dataset.size() < lower) return false;
        // TODO
        return true;
    }

    @Override
    public boolean degrade() {
        if (lowerRatio > 0.1) lowerRatio -= 0.1;
        return false;
    }

}
