package org.soundwhere.backend.audio;

import javax.persistence.*;
import java.util.List;

@Entity
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audio_id_seq")
    private Long id;
    private String source;
    private int sampleRate;
    private long sampleN;
    private int channelN;
    private int bitDepth;
    private String repr;
    private AudioFileFormat format;
    private State state = State.Registered;
    @OneToMany(mappedBy = "audio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AudioLabel> labels;

    enum State {
        Registered,
        Available,
        Invalid,
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public long getSampleN() {
        return sampleN;
    }

    public void setSampleN(long sampleN) {
        this.sampleN = sampleN;
    }

    public int getChannelN() {
        return channelN;
    }

    public void setChannelN(int channelN) {
        this.channelN = channelN;
    }

    public int getBitDepth() {
        return bitDepth;
    }

    public void setBitDepth(int bitDepth) {
        this.bitDepth = bitDepth;
    }

    public String getRepr() {
        return repr;
    }

    public void setRepr(String repr) {
        this.repr = repr;
    }

    public AudioFileFormat getFormat() {
        return format;
    }

    public void setFormat(AudioFileFormat format) {
        this.format = format;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<AudioLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<AudioLabel> labels) {
        this.labels = labels;
    }


}
