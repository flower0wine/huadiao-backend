package com.huadiao.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author flowerwine
 * @date 2024 年 04 月 28 日
 */
@Slf4j
public class CsrfCheckInterceptor implements HandlerInterceptor {

    @Value("${huadiao.userIdKey}")
    private String userIdKey;

    @Value("${user.cookieName}")
    private String cookieName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isCsrf = isCsrf(request);
        if(isCsrf) {
            String remoteAddress = request.getRemoteAddr();
            log.debug("客户端 IP: {}, 当前请求为 CSRF 请求!", remoteAddress);
            return false;
        }
        return true;
    }

    /**
     * 检查是否为 CSRF 攻击
     * @param request 请求对象
     * @return 如果是 CSRF 攻击, 返回 true, 否则返回 false
     */
    private boolean isCsrf(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userId = (String) request.getSession().getAttribute(userIdKey);

        // 用户登录时已将 User_ID 这个 cookie 设置为 SameSite，不会在第三方网站上发送
        // 因此这里要检查是否有这个 cookie, 并且它的值与服务端 session 存储的值一致
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)) {
                if(cookie.getValue().equals(userId)) {
                    return false;
                }
            }
        }
        return true;
    }
}
