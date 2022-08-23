package org.soundwhere.backend.util;

import org.soundwhere.backend.err.ErrorCode;

public class Res {
    public ErrorCode code;
    public Object data;

    public static Res ok() {
        return Res.ok(null);
    }

    public static Res ok(Object data) {
        var r = new Res();
        r.code = ErrorCode.Ok;
        r.data = data;
        return r;
    }

    public static Res err(ErrorCode code) {
        var r = new Res();
        r.code = code;
        r.data = null;
        return r;
    }
}
