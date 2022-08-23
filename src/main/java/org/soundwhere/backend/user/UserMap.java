package org.soundwhere.backend.user;

import org.mapstruct.Mapper;
import org.soundwhere.backend.user.dto.UserInfoDTO;

@Mapper(componentModel = "spring")
public interface UserMap {
    UserInfoDTO toInfo(User user);
}
