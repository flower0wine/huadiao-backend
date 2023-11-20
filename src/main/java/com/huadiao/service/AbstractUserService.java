package com.huadiao.service;

import com.huadiao.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 这里主要聚集了用户的配置
 * @author flowerwine
 */
@Slf4j
public abstract class AbstractUserService extends AbstractService implements UserService {

    /**
     * 确认登录状态为已登录
     */
    protected static boolean USER_LOGIN_STATUS = true;

    /**
     * 维持用户登录状态的 session, 服务端 session 存活时间, 以秒为单位, 存活时间为一个月
     */
    @Value("${user.sessionSurvivalTime}")
    protected int sessionSurvivalTime;

    /**
     * 验证码图片宽度
     */
    @Value("${user.codeImageWidth}")
    protected int codeImageWidth;

    /**
     * 验证码图片高度
     */
    @Value("${user.codeImageHeight}")
    protected int codeImageHeight;

    /**
     * 验证码长度
     */
    @Value("${user.codeLength}")
    protected int codeLength;

    /**
     * 干扰字符的数量
     */
    @Value("${user.codeDisturbCount}")
    protected int codeDisturbCount;

    /**
     * 用户名最小长度
     */
    @Value("${user.usernameMinLength}")
    protected int usernameMinLength;

    /**
     * 用户名最大长度
     */
    @Value("${user.usernameMaxLength}")
    protected int usernameMaxLength;

    /**
     * 密码最小长度
     */
    @Value("${user.passwordMinLength}")
    protected int passwordMinLength;

    /**
     * 密码最大长度
     */
    @Value("${user.passwordMaxLength}")
    protected int passwordMaxLength;

    /**
     * 这个用户名太受欢迎了, 已经有人使用, 换一个吧!
     */
    @Value("${user.sameUsername}")
    protected String sameUsername;

    /**
     * 恭喜! 注册成功!
     */
    @Value("${user.succeedRegister}")
    protected String succeedRegister;

    /**
     * 用户名不能包含数字、字母和下划线以外的字符!
     */
    @Value("${user.wrongUsername}")
    protected String wrongUsername;

    /**
     * 密码必须包含数字, 小写字母, 大写字母, 并且不能包含数字、字母和下划线以及 !, -, @ 以外的字符!
     */
    @Value("${user.wrongPassword}")
    protected String wrongPassword;

    /**
     * 验证码错误!
     */
    @Value("${user.wrongCode}")
    protected String wrongCode;

    /**
     * 用户名长度应为 8 至 20 之间!
     */
    @Value("${user.wrongUsernameLength}")
    protected String wrongUsernameLength;

    /**
     * 密码长度应为 8 至 32 之间!
     */
    @Value("${user.wrongPasswordLength}")
    protected String wrongPasswordLength;

    /**
     * 两次输入的密码不一样! 按下 ctrl + alt 可以返回重新输入!
     */
    @Value("${user.noSamePassword}")
    protected String noSamePassword;

    /**
     * 请填写用户名!
     */
    @Value("${user.nullUsername}")
    protected String nullUsername;

    /**
     * 请填写密码!
     */
    @Value("${user.nullPassword}")
    protected String nullPassword;

    /**
     * 请填写验证码!
     */
    @Value("${user.nullCheckCode}")
    protected String nullCheckCode;

    /**
     * 用户名符合要求
     */
    @Value("${user.conformUsername}")
    protected String conformUsername;

    /**
     * 密码符合要求
     */
    @Value("${user.conformPassword}")
    protected String conformPassword;

    @Value("${user.usernameRegStr.regexp}")
    private String usernameRegStr;

    @Value("${user.passwordRegStr.regexp}")
    private String passwordRegStr;

    /**
     * 用户名正则表达式
     */
    public Pattern usernameReg;

    /**
     * 密码正则表达式
     */
    public Pattern passwordReg;

    @PostConstruct
    private void init() {
        this.usernameReg = Pattern.compile(usernameRegStr);
        this.passwordReg = Pattern.compile(passwordRegStr);
    }

    /**
     * 检查用户名是否符合要求
     *
     * @param username 用户名
     * @return 返回错误标识, 符合要求返回 null
     */
    protected Result<?> checkUsername(String username) {
        if (!(usernameMinLength <= username.length() && username.length() <= usernameMaxLength)) {
            log.debug("用户名长度不符合要求!");
            return Result.errorParam(wrongUsernameLength);
        }
        // 检查账号是否符合要求
        Matcher matcher = usernameReg.matcher(username);
        if (matcher.find()) {
            return Result.ok(conformUsername);
        }
        log.debug("username: {}, 用户名不符合要求!", username);
        return Result.errorParam(wrongUsername);
    }

    /**
     * 检查密码是否符合要求
     *
     * @param password        密码
     * @param confirmPassword 再次确认的密码
     * @return 返回错误标识, 符合要求返回 null
     */
    protected Result<?> checkPassword(String password, String confirmPassword) {
        if (password == null) {
            return Result.errorParam(nullPassword);
        }
        if (!password.equals(confirmPassword)) {
            return Result.errorParam(noSamePassword);
        }
        if (!(passwordMinLength <= password.length() && password.length() <= passwordMaxLength)) {
            return Result.errorParam(wrongPasswordLength);
        }
        // 检查密码是否符合要求
        Matcher matcher = passwordReg.matcher(password);
        if (matcher.find()) {
            return Result.ok(conformPassword);
        }
        return Result.errorParam(wrongPassword);
    }
}
