package org.soundwhere.backend.err;

import org.soundwhere.backend.util.Res;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/errors")
public class ErrorCont {
    @GetMapping("/unauthenticated")
    public Res unauthenticated() {
        return Res.err(ErrorCode.Unauthenticated);
    }
}
