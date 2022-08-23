package org.soundwhere.backend.dataset.spec;

import org.soundwhere.backend.audio.Audio;
import org.soundwhere.backend.audio.AudioService;
import org.soundwhere.backend.dataset.SpeechLanguage;

import java.util.HashSet;

public class LanguageSpec extends GenSpec {
    private SpeechLanguage language;
    private boolean mixedLang;

    public SpeechLanguage getLanguage() {
        return language;
    }

    public void setLanguage(SpeechLanguage language) {
        this.language = language;
    }

    @Override
    public boolean degrade() {
        if (mixedLang) return false;
        else {
            mixedLang = true;
            return true;
        }
    }

    @Override
    public HashSet<Long> audioSet(AudioService audioService) {
        return null;
    }
}
