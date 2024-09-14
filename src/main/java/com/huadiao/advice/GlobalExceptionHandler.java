package com.huadiao.advice;

import cn.hutool.json.JSONUtil;
import com.huadiao.entity.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        response.setStatus(500);
        response.setContentType("application/json;charset=UTF-8");
        ex.printStackTrace();
        Result<?> result = Result.serverError(ex.getMessage());
        try {
            response.getWriter().write(JSONUtil.toJsonStr(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}