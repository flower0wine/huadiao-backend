package com.huadiao.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.huadiao.controller.ErrorController;
import com.huadiao.entity.dto.userdto.*;
import com.huadiao.mapper.FollowFanMapper;
import com.huadiao.mapper.UserInfoMapper;
import com.huadiao.mapper.UserMapper;
import com.huadiao.mapper.UserSettingsMapper;
import com.huadiao.redis.UserInfoJedisUtil;
import com.huadiao.service.AbstractUserInfoService;
import com.huadiao.service.AbstractUserService;
import com.huadiao.util.CreateHuadiaoUserId;
import com.huadiao.util.GeneratorCookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 业务层: 与账号相关的操作的实现类
 */
@Slf4j
@Service
public class UserServiceImpl extends AbstractUserService {
    private UserMapper userMapper;
    private FollowFanMapper followFanMapper;
    private UserInfoMapper userInfoMapper;
    private UserSettingsMapper userSettingsMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, FollowFanMapper followFanMapper, UserInfoMapper userInfoMapper, UserSettingsMapper userSettingsMapper) {
        this.userMapper = userMapper;
        this.followFanMapper = followFanMapper;
        this.userInfoMapper = userInfoMapper;
        this.userSettingsMapper = userSettingsMapper;
    }

    @Override
    public UserAbstractDto getHuadiaoHeaderUserInfo(Integer uid) {
        log.debug("尝试获取 session 中的用户 uid 并且获取结果为 {}", uid);
        UserAbstractDto userAbstractDto = userInfoJedisUtil.getUserInfoByUid(uid);
        // 用户已登录
        userAbstractDto.setLogin(USER_LOGIN_STATUS);
        log.info("用户具有登录状态, uid 为 {} 的用户获取了自己在花凋首页的相关信息", uid);
        return userAbstractDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) throws ServletException, IOException {
        UserBaseDto userBaseDto = userMapper.selectUserByUsernameAndPassword(username, password);
        log.debug("用户使用用户名为 {} 和密码为 {} 的账号进行登录", username, password);
        // 不存在该用户
        if (userBaseDto == null) {
            log.debug("用户名为 {} 和密码为 {} 的账号不存在", username, password);
            // 返回 404 状态码
            request.getRequestDispatcher(ErrorController.NOT_FOUND_DISPATCHER_PATH).forward(request, response);
        }
        // 存在该用户
        else {
            log.info("用户名为 {} 的用户登录成功!", username);
            Integer uid = userBaseDto.getUid();
            // 更新登录时间
            userMapper.updateUserLatestLoginTime(uid);
            // 添加 cookie 维持用户登录状态
            response.addCookie(GeneratorCookie.newMoreProCookie(COOKIE_KEY_USER_ID, userBaseDto.getUserId(), COOKIE_SURVIVAL_TIME, COOKIE_HTTP_ONLY, COOKIE_PATH));
            HttpSession session = request.getSession();
            session.setAttribute("uid", uid);
            session.setAttribute("userId", userBaseDto.getUserId());
            session.setAttribute("nickname", userBaseDto.getUsername());
            session.setMaxInactiveInterval(SESSION_SURVIVAL_TIME);
        }
    }

    @Override
    public void logoutHuadiao(Cookie cookie, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object uid = session.getAttribute("uid");
        Object userId = session.getAttribute("userId");
        Object nickname = session.getAttribute("nickname");
        log.info("uid 为 {}, nickname 为 {}, userId 为 {} 的用户尝试退出登录", uid, nickname, userId);
        // 销毁 session
        session.invalidate();
        // 删除 cookie
        cookie.setMaxAge(0);
        log.info("uid 为 {}, nickname 为 {}, userId 为 {} 的用户退出登录成功", uid, nickname, userId);
    }

    @Override
    public void getCheckCode(HttpSession session, HttpServletResponse response) throws Exception {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(CODE_IMAGE_WIDTH, CODE_IMAGE_HEIGHT, CODE_LENGTH, CODE_DISTURB_COUNT);
        circleCaptcha.write(response.getOutputStream());
        String code = circleCaptcha.getCode();
        session.setAttribute("checkCode", code);
        log.debug("用户获取验证码为 {}", code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String registerHuadiao(HttpSession session, String username, String password, String confirmPassword, String checkCode) throws Exception {
        log.info("用户尝试注册花凋账号, 用户名 {}, 验证码 {}", username, checkCode);
        if (username == null) {
            return NULL_USERNAME;
        } else if (password == null) {
            return NULL_PASSWORD;
        } else if (checkCode == null) {
            return NULL_CHECK_CODE;
        }
        // 检查用户名
        String checkUsername = checkUsername(username);
        if (checkUsername != null) {
            log.debug("用户输入的用户名不符合要求, 为 {}, 错误标识为 {}", username, checkUsername);
            return checkUsername;
        }
        // 检查密码
        String checkPassword = checkPassword(password, confirmPassword);
        if (checkPassword != null) {
            log.debug("用户输入的密码不符合要求, 第一次密码为 {}, 第二次密码为 {}, 错误标识为 {}", password, confirmPassword, checkPassword);
            return checkPassword;
        }
        // 检查验证码
        Object code = session.getAttribute("checkCode");
        if (!checkCode.equals(code)) {
            log.debug("用户输入的验证码不一致, 正确的验证码为 {}, 用户输入的验证码为 {}", code, checkCode);
            return WRONG_CODE;
        }

        // 检查用户名是否重复
        UserBaseDto userBaseDto = userMapper.selectUserByUsername(username);
        if (userBaseDto != null) {
            log.debug("用户名输入的用户名已存在, 用户名为 {}", username);
            return SAME_USERNAME;
        }
        log.debug("用户通过所有的注册检查! 下面开始注册新花凋用户");
        boolean end = false;
        String userId = null;
        // 计数不会产生 null
        int uid = userMapper.countAllUser() + 1;
        log.debug("第 {} (uid) 位用户即将加入!", uid);
        while (!end) {
            userId = CreateHuadiaoUserId.createUserId();
            String existedUserId = userMapper.selectUserIdByUid(uid);
            if(userId.equals(existedUserId)) {
                log.trace("为新用户创建的 userId {} 重复, 准备再次创建", userId);
                continue;
            }
            end = true;
        }
        // 新增花凋用户
        userMapper.insertNewHuadiaoUser(uid + 1, userId, username, password);
        // 新增用户信息
        userInfoMapper.insertOrUpdateUserInfoByUid(uid, userId, AbstractUserInfoService.DEFAULT_USER_CANVASES, AbstractUserInfoService.DEFAULT_USER_SEX, AbstractUserInfoService.DEFAULT_USER_BORN_DATE, AbstractUserInfoService.DEFAULT_USER_SCHOOL);
        log.trace("新增用户信息成功 (uid: {}, userId: {})", uid, userId);
        // 新增用户个人主页
        userInfoMapper.insertUserHomepageByUid(uid);
        log.trace("新增用户个人主页信息成功 (uid: {}, userId: {})", uid, userId);
        // 新增用户设置
        userSettingsMapper.insertOrUpdateUserSettingsByUid(uid, null);
        log.trace("新增用户账号信息成功 (uid: {}, userId{})", uid, userId);
        log.info("用户 uid {}, nickname: {}, userId: {} 注册成功", uid, username, userId);
        return SUCCEED_REGISTER;
    }

    @Override
    public UserShareDto getUserShareInfo(Integer uid) {
        log.debug("uid 为 {} 的用户尝试获取共享信息", uid);
        UserShareDto userShareDto = userMapper.selectUserShareDtoByUid(uid);
        log.debug("uid 为 {} 的用户成功获取共享信息, userShareDto: {}", uid, userShareDto);
        return userShareDto;
    }

    @Override
    public boolean userExist(Integer uid) {
        return userMapper.selectUserIdByUid(uid) != null;
    }
}
