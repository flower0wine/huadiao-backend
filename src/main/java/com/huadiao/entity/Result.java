package com.huadiao.entity;

import com.huadiao.enumeration.ResultCodeEnum;
import lombok.*;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Data
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

    public Result(ResultCodeEnum code, String message, T data) {
        this.code = code.getCode();
        this.message = message;
        this.data = data;
    }

    public boolean succeed() {
        return ResultCodeEnum.SUCCEED.getCode() == this.getCode();
    }

    public static <T> Result<T> ok() {
        return new Result<>(ResultCodeEnum.SUCCEED, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultCodeEnum.SUCCEED, data);
    }

    public static <T> Result<T> ok(String message) {
        return new Result<>(ResultCodeEnum.SUCCEED, message,  null);
    }

    public static <T> Result<T> errorParam() {
        return new Result<>(ResultCodeEnum.ERROR_PARAM, null);
    }

    public static <T> Result<T> errorParam(T data) {
        return new Result<>(ResultCodeEnum.ERROR_PARAM, data);
    }

    public static <T> Result<T> errorParam(String message) {
        return new Result<>(ResultCodeEnum.ERROR_PARAM, message, null);
    }

    public static <T> Result<T> blankParam() {
        return new Result<>(ResultCodeEnum.BLANK_PARAM, null);
    }

    public static <T> Result<T> pageNotExist() {
        return new Result<>(ResultCodeEnum.PAGE_NOT_EXIST, null);
    }

    public static <T> Result<T> notExist() {
        return new Result<>(ResultCodeEnum.NOT_EXIST, null);
    }

    public static <T> Result<T> emptyData() {
        return new Result<>(ResultCodeEnum.EMPTY_DATA, null);
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

    public static <T> Result<T> notAuthorize(String message) {
        return new Result<>(ResultCodeEnum.NOT_AUTHORITATIVE, message, null);
    }

    public static <T> Result<T> serverError() {
        return new Result<>(ResultCodeEnum.SERVER_ERROR, null);
    }

    public static <T> Result<T> serverError(String message) {
        return new Result<>(ResultCodeEnum.SERVER_ERROR, message, null);
    }
}
