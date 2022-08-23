package org.soundwhere.backend.security;

import org.soundwhere.backend.err.LogicError;
import org.soundwhere.backend.user.UserService;
import org.soundwhere.backend.util.Res;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/rest/tokens")
public class TokenCont {

    private final UserService userService;

    public TokenCont(UserService userService) {this.userService = userService;}

    @PostMapping("/access")
    private Res login(Principal principal) throws LogicError {
        var name = principal.getName();
        var info = userService.infoByName(name);
        return Res.ok(info);
    }

}
