package org.soundwhere.backend.audio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;

public interface AudioLabelRepo extends JpaRepository<AudioLabel, Long> {
    @Query("select distinct a.id from Audio a join AudioLabel al on a = al.audio " +
        "where al.type=:#{T(org.soundwhere.backend.audio.MetaType).TaskLabel} and al.metaKey=:typeName")
    HashSet<Long> withTaskLabel(String typeName);
}
