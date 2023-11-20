package com.huadiao.interceptor;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.huadiao.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
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
@Order(1)
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Value("${huadiao.uidKey}")
    private String uidKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean login = loginCheck(request);
        if(!login) {
            // 用户未登录, 状态码仍为 200
            response.setStatus(HttpStatus.HTTP_OK);
            response.getWriter().write(JSONUtil.toJsonStr(Result.notAuthorize()));
        }
        return login;
    }

    private boolean loginCheck(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object uid = session.getAttribute(uidKey);
        return uid != null;
    }

}
