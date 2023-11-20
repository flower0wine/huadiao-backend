package com.huadiao.entity;

/**
 * 结果状态码, enum
 * @author flowerwine
 */
public enum ResultCodeEnum {
    /** 状态码 */
    SUCCEED(1000, "succeed"),
    EXCEED_LIMIT(1001, "exceedLimit"),
    ERROR_PARAM(2000, "error params"),
    BLANK_PARAM(2001, "blank params"),
    NOT_AUTHORITATIVE(2003, "not authoritative"),
    NOT_EXIST(3000, "not exist"),
    EXISTED(3001, "existed"),
    NOT_ALLOW(3002, "notAllowed"),
    ;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return message;
    }

    private Integer code;
    private String message;

}
