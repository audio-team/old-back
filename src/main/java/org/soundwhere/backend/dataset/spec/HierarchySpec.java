package org.soundwhere.backend.dataset.spec;

import org.soundwhere.backend.audio.Audio;
import org.soundwhere.backend.audio.AudioService;

import java.util.HashSet;

public class HierarchySpec extends GenSpec {

    enum SubclassLevel {
        None,
        SameHierarchy,
        All,
    }

    private String hierarchy;
    private SubclassLevel subclassLevel;

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    @Override
    public boolean degrade() {
        switch (subclassLevel) {
            case None:
                subclassLevel = SubclassLevel.SameHierarchy;
                return true;
            case SameHierarchy:
                subclassLevel = SubclassLevel.All;
                return true;
            case All:
                return false;
        }
        return true;
    }

    @Override
    public HashSet<Long> audioSet(AudioService audioService) {
        return null;
    }
}
