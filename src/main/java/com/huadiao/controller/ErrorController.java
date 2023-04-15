package com.huadiao.controller;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 错误处理控制器接口
 * @author flowerwine
 */
public interface ErrorController extends Controller {

    /**
     * 资源未找到匹配路径
     */
    String NOT_FOUND_DISPATCHER_PATH = "/notFound";

    /**
     * 没有相应资源错误
     * @param message 错误信息
     * @return 返回错误信息响应对象
     */
    ResponseEntity<String> notFoundError(String message);

}
