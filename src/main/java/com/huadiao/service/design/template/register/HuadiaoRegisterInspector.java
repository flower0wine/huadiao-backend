package com.huadiao.service.design.template.register;

import com.huadiao.entity.Result;
import com.huadiao.entity.dto.user.UserBaseDto;
import com.huadiao.entity.elasticsearch.UserEs;
import com.huadiao.enumeration.SexEnum;
import com.huadiao.service.design.template.AbstractInspector;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author flowerwine
 * @date 2024 年 02 月 10 日
 */
@Slf4j
@Setter
@Getter
@NoArgsConstructor
@Component
public class HuadiaoRegisterInspector extends AbstractInspector implements RegisterInspector {

    /**
     * 考虑到该类只有一个实例，所以用 ThreadLocal 存储单个会话数据
     */
    private ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<>();

    /**
     * 用户名为 null 时的错误标识
     */
    private static final String nullUsername = "nullUsername";

    /**
     * 密码为 null 时的错误标识
     */
    private static final String nullPassword = "nullPassword";

    /**
     * 验证码为 null 时的错误标识
     */
    private static final String nullCheckCode = "nullCheckCode";

    /**
     * 验证码错误时的错误标识
     */
    private static final String wrongCode = "wrongCode";

    /**
     * 存在相同用户名时的错误标识
     */
    private static final String sameUsername = "sameUsername";

    /**
     * 用户名最小长度
     */
    private static int usernameMinLength = 8;

    /**
     * 用户名最大长度
     */
    private static int usernameMaxLength = 20;

    /**
     * 密码最小长度
     */
    private static int passwordMinLength = 8;

    /**
     * 密码最大长度
     */
    private static int passwordMaxLength = 32;

    /**
     * 用户名不符合要求
     */
    private static final String wrongUsername = "wrongUsername";

    /**
     * 密码不符合要求!
     */
    private static final String wrongPassword = "wrongPassword";

    /**
     * 用户名长度不符合要求!
     */
    private static final String wrongUsernameLength = "wrongUsernameLength";

    /**
     * 密码长度不符合要求!
     */
    private static final String wrongPasswordLength = "wrongPasswordLength";

    /**
     * 两次输入的密码不一样!
     */
    private static final String noSamePassword = "noSamePassword";

    /**
     * 用户名正则表达式
     */
    private static Pattern usernameReg = Pattern.compile("(?=.*[0-9])(?=.*[a-zA-Z])^[a-z0-9A-Z_]{8,20}$");

    /**
     * 密码正则表达式
     */
    private static Pattern passwordReg = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])^[a-zA-Z0-9_,.+~&^%$#=-]{8,32}$");

    @Override
    public void flushThreadLocal() {
        threadLocal.remove();
    }

    @Override
    public Result<?> check() {
        Map<String, String> map = threadLocal.get();
        String username = map.get("username");
        String password = map.get("password");
        String confirmPassword = map.get("confirmPassword");
        String checkCode = map.get("checkCode");
        String jsessionid = map.get("jsessionid");

        log.info("用户尝试注册花凋账号, 用户名 {}, 验证码 {}", username, checkCode);
        if (username == null) {
            return Result.errorParam(nullUsername);
        } else if (password == null) {
            return Result.errorParam(nullPassword);
        } else if (checkCode == null) {
            return Result.errorParam(nullCheckCode);
        }
        // 检查用户名
        Result<?> result = checkUsername(username);
        if (!result.succeed()) {
            log.debug("用户输入的用户名不符合要求, 为 {}, 错误标识为 {}", username, result.getData());
            return result;
        }
        // 检查密码
        result = checkPassword(password, confirmPassword);
        if (!result.succeed()) {
            log.debug("用户输入的密码不符合要求, 第一次密码为 {}, 第二次密码为 {}, 错误标识为 {}", password, confirmPassword, result.getData());
            return result;
        }
        // 检查验证码
        String code = userBaseJedisUtil.getCheckCode(jsessionid);
        if (!checkCode.equals(code)) {
            log.debug("用户输入的验证码不一致, 正确的验证码为 {}, 用户输入的验证码为 {}", code, checkCode);
            return Result.errorParam(wrongCode);
        }

        // 检查用户名是否重复
        UserBaseDto userBaseDto = userMapper.selectUserByUsername(username);
        if (userBaseDto != null) {
            log.debug("用户名输入的用户名已存在, 用户名为 {}", username);
            return Result.errorParam(sameUsername);
        }
        return Result.ok(null);
    }

    @Override
    public void addUser(Integer uid, String userId, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = threadLocal.get();
        String username = map.get("username");
        String password = map.get("password");
        String jsessionid = map.get("jsessionid");

        // 新增花凋用户
        userMapper.insertNewHuadiaoUser(uid, userId, username, password);

        // 新增用户个人主页
        homepageMapper.insertUserHomepageByUid(uid, null);
        log.trace("新增用户个人主页信息成功 (uid: {}, userId: {})", uid, userId);

        // 新增用户信息
        userInfoMapper.insertOrUpdateUserInfoByUid(uid, userId, null, SexEnum.NO_KNOWN.value, null, null);
        log.trace("新增用户信息成功 (uid: {}, userId: {})", uid, userId);

        // 注册成功删除验证码
        userBaseJedisUtil.deleteCheckCode(jsessionid);

        UserEs userEs = new UserEs();
        userEs.setUid(uid);
        userEs.setUserId(userId);
        // 保存至 elasticsearch
        userRepository.save(userEs);
    }

    /**
     * 检查用户名是否符合要求
     *
     * @param username 用户名
     * @return 返回错误标识, 符合要求返回 null
     */
    private static Result<?> checkUsername(String username) {
        if (!(usernameMinLength <= username.length() && username.length() <= usernameMaxLength)) {
            log.debug("用户名长度不符合要求!");
            return Result.errorParam(wrongUsernameLength);
        }
        // 检查账号是否符合要求
        Matcher matcher = usernameReg.matcher(username);
        if (matcher.find()) {
            return Result.ok(null);
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
    private static Result<?> checkPassword(String password, String confirmPassword) {
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
            return Result.ok(null);
        }
        return Result.errorParam(wrongPassword);
    }
}
