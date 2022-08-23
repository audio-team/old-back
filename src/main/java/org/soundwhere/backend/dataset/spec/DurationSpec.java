package org.soundwhere.backend.dataset.spec;

import org.soundwhere.backend.audio.Audio;
import org.soundwhere.backend.audio.AudioService;

import java.util.HashMap;
import java.util.HashSet;

public class DurationSpec extends GenSpec {
    private int duration;
    private double deltaRatio = 0.1;
    private HashMap<Long, TrimParam> params = new HashMap<>();

    static class TrimParam {
        public long head;
        public long tail;

        public TrimParam(long head, long tail) {
            this.head = head;
            this.tail = tail;
        }
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean degrade() {
        if (deltaRatio < 0.5) {
            deltaRatio += 0.1;
            return true;
        }
        return false;
    }

    @Override
    public HashSet<Long> audioSet(AudioService audioService) {
        var infos = audioService.durationInfo(duration, deltaRatio);

        var audioSet = new HashSet<Long>();

        for (var info : infos) {
            var audio = info.audio;
            var expectedSampleN = duration * audio.getSampleRate();
            var diff = audio.getSampleN() - expectedSampleN;
            if (diff > (info.backBlank + info.frontBlank)) {continue;}
            var padding = (expectedSampleN - (audio.getSampleN() - info.frontBlank - info.backBlank)) / 2;
            var frontDiff = padding - info.frontBlank;
            var backDiff = padding - info.backBlank;
            System.out.println(info);
            System.out.println(expectedSampleN);
            System.out.println(padding);
            System.out.println(frontDiff);
            System.out.println(backDiff);
            System.out.println();
            audioSet.add(audio.getId());
            params.put(audio.getId(), new TrimParam(frontDiff, backDiff));
        }
        System.out.println(audioSet);
        return audioSet;
    }
}
