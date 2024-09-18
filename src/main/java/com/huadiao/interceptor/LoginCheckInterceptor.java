package com.huadiao.interceptor;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.huadiao.constant.CookieProperties;
import com.huadiao.constant.HuadiaoProperties;
import com.huadiao.entity.Result;
import com.huadiao.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    protected CookieProperties cookieProperties;

    @Autowired
    protected HuadiaoProperties huadiaoProperties;

    @Autowired
    protected CookieUtil cookieUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isLogin = isLogin(request);

        if(isLogin) {
            log.debug("用户 IP: {}, 已登录!", request.getRemoteAddr());
            return true;
        }
        log.debug("用户 IP: {} 未登录!", request.getRemoteAddr());

        // 用户未登录，清除 cookie
        ResponseCookie cookie = cookieUtil.getUserCookieBuilder("").maxAge(0).build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // 用户未登录, 状态码仍为 200
        response.setStatus(HttpStatus.HTTP_OK);
        response.getWriter().write(JSONUtil.toJsonStr(Result.notAuthorize()));
        return false;
    }

    private boolean isLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object uid = session.getAttribute(huadiaoProperties.uidKey);
        return uid != null;
    }

}
