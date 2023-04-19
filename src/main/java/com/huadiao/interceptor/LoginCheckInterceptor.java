package com.huadiao.interceptor;

import com.huadiao.controller.ErrorController;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 检查用户是否登录
 */
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object uid = session.getAttribute("uid");
        if(uid == null) {
            request.setAttribute("notFoundMessage", "noLoginStatus");
            request.getRequestDispatcher(ErrorController.NOT_FOUND_DISPATCHER_PATH).forward(request, response);
            return false;
        }
        return true;
    }
}
