package org.soundwhere.backend.dataset.spec;

import org.soundwhere.backend.audio.Audio;
import org.soundwhere.backend.audio.AudioService;

import java.util.HashSet;

public abstract class GenSpecForDataset extends GenSpec {
    public abstract boolean actOn(HashSet<Long> dataset);

    @Override
    public HashSet<Long> audioSet(AudioService audioService) {
        return null;
    }
}
