package com.huadiao.util;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.Cookie;

/**
 * 该类用于更加方便地生成 cookie
 * @author flowerwine
 */
public class GeneratorCookie {
    /**
     * 仅以用户 id 作为 cookie 键名来记录用户登录状态
     */
    @Value("${user.cookieKey}")
    protected static String COOKIE_KEY_USER_ID = "User_ID";

    /**
     * 能访问 cookie 的页面, 下面设置为根目录(根目录如: localhost:9090/huadiao/)
     */
    protected static String COOKIE_PATH = "/";

    /**
     * cookie 是否只能通过 http 获取
     */
    protected static boolean COOKIE_HTTP_ONLY = true;

    /**
     * 可以访问 cookie 的域名, 如设置为 .google.com, 则以 google.com 结尾的域名访问该域名
     */
    protected static String COOKIE_DOMAIN = "localhost";

    /**
     * 维持用户登录状态的 cookie 存活时间, 以秒为单位, 存活时间为一个月
     */
    @Value("${user.cookieSurvivalTime}")
    protected static int COOKIE_SURVIVAL_TIME = 2592000;

    /**
     * 生成身份 cookie
     * @param userId 用户 id
     * @return 返回身份 cookie
     */
    public static Cookie createIdentityCookie (String userId) {
        return newMoreProCookie(COOKIE_KEY_USER_ID, userId, COOKIE_SURVIVAL_TIME, COOKIE_HTTP_ONLY, COOKIE_PATH);
    }

    public static Cookie newBlankCookie(String key, String value){
        return new Cookie(key, value);
    }

    public static Cookie newProCookie(String key, String value, int time, boolean httpOnly){
        Cookie cookie = newBlankCookie(key, value);
        cookie.setMaxAge(time);
        cookie.setHttpOnly(httpOnly);
        return cookie;
    }

    public static Cookie newMoreProCookie(String key, String value, int time, boolean httpOnly, String path){
        Cookie cookie = newProCookie(key, value, time, httpOnly);
        cookie.setPath(path);
        return cookie;
    }

    public static Cookie newDetailCookie(String key, String value, int time, boolean httpOnly, String domain, String path){
        Cookie cookie = newMoreProCookie(key, value, time, httpOnly, path);
        cookie.setDomain(domain);
        return cookie;
    }
}
