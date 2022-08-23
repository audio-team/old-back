package org.soundwhere.backend.dataset.spec;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.soundwhere.backend.audio.Audio;
import org.soundwhere.backend.audio.AudioService;

import java.util.HashSet;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "name", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ScaleSpec.class, name = "Dataset Scale"),
    @JsonSubTypes.Type(value = HierarchySpec.class, name = "Hierarchy"),
    @JsonSubTypes.Type(value = DurationSpec.class, name = "Audio Duration"),
    @JsonSubTypes.Type(value = QualitySpec.class, name = "Audio Quality"),
    @JsonSubTypes.Type(value = LanguageSpec.class, name = "Language"),
})
public abstract class GenSpec {
    public String name;

    public abstract boolean degrade();
    public abstract HashSet<Long> audioSet(AudioService audioService);
}
