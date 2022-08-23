package org.soundwhere.backend.err;

public enum ErrorCode {
    Ok,
    Unauthenticated,
    SystemBusy,
    UsernameUsed,
    CorruptedInput,
    SystemPathError, 
    UserNotExisted, 
    NoScaleSpec, FailToGenerate,
}
