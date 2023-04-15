package com.huadiao.controller.impl;

import com.huadiao.controller.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 错误请求实现类
 */
@Controller
public class ErrorControllerImpl implements ErrorController {

    @Override
    @RequestMapping("/notFoundError")
    public ResponseEntity<String> notFoundError(@RequestAttribute String notFoundMessage) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundMessage);
    }

}
