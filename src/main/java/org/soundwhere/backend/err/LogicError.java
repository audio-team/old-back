package org.soundwhere.backend.err;

public class LogicError extends Exception {
    public ErrorCode code;

    public LogicError(ErrorCode code) {
        this.code = code;
    }
}
