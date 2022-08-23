package org.soundwhere.backend.user;

import org.soundwhere.backend.err.LogicError;
import org.soundwhere.backend.user.dto.RegisterDTO;
import org.soundwhere.backend.util.Res;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/users")
public class UserCont {

    private final UserService service;

    public UserCont(UserService service) {this.service = service;}

    @PostMapping("/")
    public Res register(@RequestBody RegisterDTO dto) throws LogicError {
        service.register(dto);
        return Res.ok();
    }
    
    @GetMapping("/pwd")
    public Res unsafe_show_username(Authentication principal) {
        System.err.println(principal.getName());
        return Res.ok();
    }
}
