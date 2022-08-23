package org.soundwhere.backend.audio;

import org.soundwhere.backend.audio.qo.DurationInfoQO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;
import java.util.List;

public interface AudioRepo extends JpaRepository<Audio, Long> {
    @Query("select new org.soundwhere.backend.audio.qo.DurationInfoQO(" +
        "a, al.value) from Audio a " +
        "join AudioLabel al on a = al.audio where " +
        "al.type = :#{T(org.soundwhere.backend.audio.MetaType).Property} " +
        "and al.metaKey='Blank' " +
        "and a.sampleN>(a.sampleRate * 1.0 * :start) " +
        "and a.sampleN<(a.sampleRate * 1.0 * :end)")
    HashSet<DurationInfoQO> durationInfo(double start, double end);

    @Query("select a from Audio a where a.id in :audioSet")
    List<Audio> batchById(HashSet<Long> audioSet);
}
