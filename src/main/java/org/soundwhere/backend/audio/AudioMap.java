package org.soundwhere.backend.audio;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.soundwhere.backend.audio.dto.AudioMetaDTO;
import org.soundwhere.backend.audio.dto.CustomMetaDTO;

@Mapper(componentModel = "spring")
public interface AudioMap {
    void updateAudioMeta(AudioMetaDTO dto, @MappingTarget Audio audio);

    AudioLabel fromCustom(CustomMetaDTO dto);
}
