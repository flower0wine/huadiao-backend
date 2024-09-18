package com.huadiao.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author flowerwine
 * @date 2024 年 09 月 18 日
 */
@Data
@Component
public class CookieProperties {

    /**
     * 维持用户登录状态的 session, 服务端 session 存活时间, 以秒为单位, 存活时间为一个月
     */
    @Value("${user.sessionSurvivalTime}")
    public Integer sessionSurvivalTime;

    /**
     * 仅以用户 id 作为 cookie 键名来记录用户登录状态
     */
    @Value("${user.cookieName}")
    public String cookieName;

    /**
     * 能访问 cookie 的页面, 下面设置为根目录(根目录如: localhost:9090/huadiao/)
     */
    @Value("${user.cookiePath}")
    public String cookiePath;

    /**
     * cookie 是否只能通过 http 获取
     */
    @Value("${user.cookieHttpOnly}")
    public Boolean cookieHttpOnly;

    /**
     * 可以访问 cookie 的域名, 如设置为 .google.com, 则以 google.com 结尾的域名访问该域名
     */
    @Value("${user.cookieDomain}")
    public String cookieDomain;

    /**
     * 维持用户登录状态的 cookie 存活时间, 以秒为单位, 存活时间为 60 * 60 * 24 * 30 == 2592000 秒, 即 30 天
     */
    @Value("${user.cookieSurvivalTime}")
    public Integer cookieMaxAge;

    @Value("${user.sameSite}")
    public String sameSite;

    @Value("${user.cookieSecure}")
    public Boolean cookieSecure;
}
