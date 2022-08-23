package org.soundwhere.backend.err;

import org.soundwhere.backend.util.Res;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrHandler {
    @ExceptionHandler(LogicError.class)
    public Res handle(LogicError error) {
        return Res.err(error.code);
    }
}
