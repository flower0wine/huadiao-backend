package com.huadiao.advice;

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
        ex.printStackTrace();
        try {
            response.getWriter().write(ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}