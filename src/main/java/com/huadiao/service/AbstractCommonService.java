package com.huadiao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;

/**
 * @author flowerwine
 * @date 2024 年 02 月 09 日
 */
@Slf4j
public abstract class AbstractCommonService extends AbstractService implements CommonService {
    /**
     * 维持用户登录状态的 session, 服务端 session 存活时间, 以秒为单位, 存活时间为一个月
     */
    @Value("${user.sessionSurvivalTime}")
    protected Integer sessionSurvivalTime;

    /**
     * 验证码图片宽度
     */
    @Value("${user.codeImageWidth}")
    protected Integer codeImageWidth;

    /**
     * 验证码图片高度
     */
    @Value("${user.codeImageHeight}")
    protected Integer codeImageHeight;

    /**
     * 验证码长度
     */
    @Value("${user.codeLength}")
    protected Integer codeLength;

    /**
     * 干扰字符的数量
     */
    @Value("${user.codeDisturbCount}")
    protected Integer codeDisturbCount;


    /**
     * 仅以用户 id 作为 cookie 键名来记录用户登录状态
     */
    @Value("${user.cookieName}")
    private String cookieName;

    /**
     * 能访问 cookie 的页面, 下面设置为根目录(根目录如: localhost:9090/huadiao/)
     */
    @Value("${user.cookiePath}")
    private String cookiePath;

    /**
     * cookie 是否只能通过 http 获取
     */
    @Value("${user.cookieHttpOnly}")
    private Boolean cookieHttpOnly;

    /**
     * 可以访问 cookie 的域名, 如设置为 .google.com, 则以 google.com 结尾的域名访问该域名
     */
    @Value("${user.cookieDomain}")
    private String cookieDomain;

    /**
     * 维持用户登录状态的 cookie 存活时间, 以秒为单位, 存活时间为 60 * 60 * 24 * 30 == 2592000 秒, 即 30 天
     */
    @Value("${user.cookieSurvivalTime}")
    private Integer cookieMaxAge;

    @Value("${user.sameSite}")
    private String sameSite;

    @Value("${user.cookieSecure}")
    private Boolean cookieSecure;

    /**
     * 获取用户身份标识的 Cookie
     * @param value cookie 值
     * @return 返回生成的 cookie
     */
    protected ResponseCookie getUserCookie(String value) {
        ResponseCookie cookie = ResponseCookie.from(cookieName, value)
                .sameSite(sameSite)
                .domain(cookieDomain)
                .httpOnly(cookieHttpOnly)
                .maxAge(cookieMaxAge)
                .path(cookiePath)
                .secure(cookieSecure)
                .build();
        return cookie;
    }

    /**
     * 恭喜! 注册成功!
     */
    protected static final String SUCCEED_REGISTER = "succeedRegister";


}
