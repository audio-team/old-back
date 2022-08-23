package org.soundwhere.backend.user;

import org.soundwhere.backend.err.ErrorCode;
import org.soundwhere.backend.err.LogicError;
import org.soundwhere.backend.user.dto.RegisterDTO;
import org.soundwhere.backend.user.dto.UserInfoDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder encoder;
    private final UserRepo repo;
    private final UserMap map;

    public UserService(PasswordEncoder encoder, UserRepo repo, UserMap map) {
        this.encoder = encoder;
        this.repo = repo;
        this.map = map;
    }

    public void register(RegisterDTO dto) throws LogicError {
        if (repo.existsByUsername(dto.username)) throw new LogicError(ErrorCode.UsernameUsed);
        var user = new User();
        user.setUsername(dto.username);
        user.setPassword(encoder.encode(dto.password));
        user.setEmail(dto.email);
        repo.save(user);
    }

    public UserInfoDTO infoByName(String name) throws LogicError {
        var maybeUser = repo.findInfo(name);
        if (maybeUser.isEmpty()) throw new LogicError(ErrorCode.UserNotExisted);
        return map.toInfo(maybeUser.get());
    }
}
