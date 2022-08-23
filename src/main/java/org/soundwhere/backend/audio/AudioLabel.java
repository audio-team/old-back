package org.soundwhere.backend.audio;

import org.soundwhere.backend.dataset.dto.TaskType;

import javax.persistence.*;

@Entity
public class AudioLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audio_label_id_seq")
    private Long id;
    @ManyToOne
    private Audio audio;
    @Column(length = 2047)
    private String value;
    private String metaKey;
    private MetaType type;

    public static AudioLabel taskLabel(TaskType type, String value) {
        return new AudioLabel(value, type.name(), MetaType.TaskLabel);
    }

    public AudioLabel() {
    }

    public AudioLabel(String value, String metaKey, MetaType type) {
        this.value = value;
        this.metaKey = metaKey;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String key) {
        this.metaKey = key;
    }

    public MetaType getType() {
        return type;
    }

    public void setType(MetaType type) {
        this.type = type;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }
}
