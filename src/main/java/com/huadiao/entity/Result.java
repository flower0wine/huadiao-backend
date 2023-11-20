package com.huadiao.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Setter
@Getter
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(ResultCodeEnum code, T data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean succeed() {
        return ResultCodeEnum.SUCCEED.getCode() == this.getCode();
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultCodeEnum.SUCCEED, data);
    }

    public static <T> Result<T> errorParam() {
        return new Result<>(ResultCodeEnum.ERROR_PARAM,  null);
    }

    public static <T> Result<T> errorParam(T data) {
        return new Result<>(ResultCodeEnum.ERROR_PARAM,  data);
    }

    public static <T> Result<T> blankParam() {
        return new Result<>(ResultCodeEnum.BLANK_PARAM, null);
    }

    public static <T> Result<T> notExist() {
        return new Result<>(ResultCodeEnum.NOT_EXIST, null);
    }

    public static <T> Result<T> existed() {
        return new Result<>(ResultCodeEnum.EXISTED, null);
    }

    public static <T> Result<T> notAllowed() {
        return new Result<>(ResultCodeEnum.NOT_ALLOW, null);
    }

    public static <T> Result<T> exceedLimit() {
        return new Result<>(ResultCodeEnum.EXCEED_LIMIT, null);
    }

    public static <T> Result<T> notAuthorize() {
        return new Result<>(ResultCodeEnum.NOT_AUTHORITATIVE, null);
    }
}
