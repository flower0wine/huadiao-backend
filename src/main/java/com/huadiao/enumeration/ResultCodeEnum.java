package com.huadiao.enumeration;

/**
 * 结果状态码, enum
 * @author flowerwine
 */
public enum ResultCodeEnum {
    /** 状态码 */
    SUCCEED(1000, "succeed"),
    EXCEED_LIMIT(1001, "exceed limit"),
    ERROR_PARAM(2000, "error params"),
    BLANK_PARAM(2001, "blank params"),
    NOT_AUTHORITATIVE(2003, "not authoritative"),
    /**
     * 页面不存在
     */
    PAGE_NOT_EXIST(3000, "page not exist"),
    EXISTED(3001, "existed"),
    NOT_ALLOW(3002, "not allowed"),
    /**
     * 没有获取到数据
     */
    EMPTY_DATA(3003, "empty data"),
    /**
     * 数据不存在, 但不跳转到错误页面, 由前端自行解决
     */
    NOT_EXIST(3004, "not exist"),
    SERVER_ERROR(5000, "server error"),
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
