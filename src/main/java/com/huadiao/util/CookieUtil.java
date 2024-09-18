package com.huadiao.util;

import com.huadiao.constant.CookieProperties;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author flowerwine
 * @date 2024 年 09 月 18 日
 */
@Component
public class CookieUtil {

    @Resource
    protected CookieProperties cookieProperties;

    public ResponseCookie getUserCookie(String value) {
        return getUserCookieBuilder(value).build();
    }

    public ResponseCookie.ResponseCookieBuilder getUserCookieBuilder(String value) {
        return ResponseCookie.from(cookieProperties.cookieName, value)
                .sameSite(cookieProperties.sameSite)
                .domain(cookieProperties.cookieDomain)
                .httpOnly(cookieProperties.cookieHttpOnly)
                .maxAge(cookieProperties.cookieMaxAge)
                .path(cookieProperties.cookiePath)
                .secure(cookieProperties.cookieSecure);
    }
}
