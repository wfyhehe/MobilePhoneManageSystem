package com.wfy.web.common;

/**
 * Created by Administrator on 2017/8/7.
 */
public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    FORBIDDEN(403, "FORBIDDEN");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
