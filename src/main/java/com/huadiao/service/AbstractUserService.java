package com.huadiao.service;

import org.springframework.beans.factory.annotation.Value;

import java.beans.ConstructorProperties;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public abstract class AbstractUserService extends AbstractService implements UserService {


    /**
     * 确认登录状态为已登录
     */
    protected static boolean USER_LOGIN_STATUS = true;

    /**
     * 维持用户登录状态的 session, 服务端 session 存活时间, 以秒为单位, 存活时间为一个月
     */
    protected static int SESSION_SURVIVAL_TIME = 2592000;

    /**
     * 验证码图片宽度
     */
    protected static int CODE_IMAGE_WIDTH = 200;

    /**
     * 验证码图片高度
     */
    protected static int CODE_IMAGE_HEIGHT = 100;

    /**
     * 验证码长度
     */
    protected static int CODE_LENGTH = 6;

    /**
     * 干扰字符的数量
     */
    protected static int CODE_DISTURB_COUNT = 30;

    /**
     * 这个用户名太受欢迎了, 已经有人使用, 换一个吧!
     */
    protected static final String SAME_USERNAME = "sameUsername";

    /**
     * 恭喜! 注册成功!
     */
    protected static final String SUCCEED_REGISTER = "succeedRegister";

    /**
     * 用户名不能包含数字、字母和下划线以外的字符!
     */
    protected static final String WRONG_USERNAME = "wrongUsername";

    /**
     * 密码必须包含数字, 小写字母, 大写字母, 并且不能包含数字、字母和下划线以及 !, -, @ 以外的字符!
     */
    protected static final String WRONG_PASSWORD = "wrongPassword";

    /**
     * 验证码错误!
     */
    protected static final String WRONG_CODE = "wrongCode";

    /**
     * 用户名长度应为 8 至 20 之间!
     */
    protected static final String WRONG_USERNAME_LENGTH = "wrongUsernameLength";

    /**
     * 密码长度应为 8 至 32 之间!
     */
    protected static final String WRONG_PASSWORD_LENGTH = "wrongPasswordLength";

    /**
     * 两次输入的密码不一样! 按下 ctrl + alt 可以返回重新输入!
     */
    protected static final String NO_SAME_PASSWORD = "noSamePassword";

    /**
     * 请填写用户名!
     */
    protected static final String NULL_USERNAME = "nullUsername";

    /**
     * 请填写密码!
     */
    protected static final String NULL_PASSWORD = "nullPassword";

    /**
     * 请填写验证码!
     */
    protected static final String NULL_CHECK_CODE = "nullCheckCode";

    /**
     * 用户名正则表达式
     */
    public static Pattern usernameReg = Pattern.compile("[^0-9a-zA-Z_]");

    /**
     * 密码正则表达式
     */
    public static Pattern passwordReg = Pattern.compile("[^0-9a-zA-Z_!@-]");

    /**
     * 检查用户名是否符合要求
     * @param username 用户名
     * @return 返回错误标识, 符合要求返回 null
     */
    protected String checkUsername(String username) {
        final int usernameMinLength = 8;
        final int usernameMaxLength = 20;
        if(!(usernameMinLength <= username.length() && username.length() <= usernameMaxLength)) {
            return WRONG_USERNAME_LENGTH;
        }
        // 检查账号是否符合要求
        Matcher matcher = usernameReg.matcher(username);
        if(matcher.find()) {
            return WRONG_USERNAME;
        }
        return null;
    }

    /**
     * 检查密码是否符合要求
     * @param password 密码
     * @param confirmPassword 再次确认的密码
     * @return 返回错误标识, 符合要求返回 null
     */
    protected String checkPassword(String password, String confirmPassword) {
        if(password == null) {
            return NULL_PASSWORD;
        }
        if(!password.equals(confirmPassword)) {
            return NO_SAME_PASSWORD;
        }
        final int passwordMinLength = 8;
        final int passwordMaxLength = 32;
        if(!(passwordMinLength <= password.length() && password.length() <= passwordMaxLength)) {
            return WRONG_PASSWORD_LENGTH;
        }
        // 检查密码是否符合要求
        Matcher matcher = passwordReg.matcher(password);
        if(matcher.find()) {
            return WRONG_PASSWORD;
        }
        final String capital = "capital";
        final String ordinary = "ordinary";
        final String number = "number";
        // 至少要求包含一个
        Map<String, Boolean> map = new HashMap<String, Boolean>(4){{
            // 大写字母
            put(capital, true);
            // 小写字母
            put(ordinary, false);
            // 数字
            put(number, false);
        }};
        for(char c : password.toCharArray()) {
            if ('0' <= c && c <= '9') {
                map.put(number, true);
            } else if ('A' <= c && c <= 'Z') {
                map.put(capital, true);
            } else if ('a' <= c && c <= 'z') {
                map.put(ordinary, true);
            }
        }
        if(!(map.get(capital) && map.get(ordinary) && map.get(number))) {
            return WRONG_PASSWORD;
        }
        return null;
    }
}
