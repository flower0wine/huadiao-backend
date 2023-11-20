package com.huadiao.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.huadiao.entity.Result;
import com.huadiao.entity.SexEnum;
import com.huadiao.entity.dto.userdto.*;
import com.huadiao.entity.elasticsearch.UserEs;
import com.huadiao.mapper.*;
import com.huadiao.service.AbstractUserService;
import com.huadiao.util.CreateHuadiaoUserId;
import com.huadiao.util.GeneratorCookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private HuadiaoHouseMapper huadiaoHouseMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, FollowFanMapper followFanMapper, UserInfoMapper userInfoMapper, UserSettingsMapper userSettingsMapper, HuadiaoHouseMapper huadiaoHouseMapper) {
        this.userMapper = userMapper;
        this.followFanMapper = followFanMapper;
        this.userInfoMapper = userInfoMapper;
        this.userSettingsMapper = userSettingsMapper;
        this.huadiaoHouseMapper = huadiaoHouseMapper;
    }

    @Override
    public UserAbstractDto getHuadiaoHeaderUserInfo(Integer uid) {
        UserShareDto userShareDto = userMapper.selectUserShareDtoByUid(uid);
        List<Integer> followFans = followFanMapper.countFollowAndFansByUid(uid);
        UserAbstractDto userAbstractDto = UserAbstractDto.loadUserAbstractInfo(userShareDto, followFans);
        // 用户已登录
        userAbstractDto.setLogin(USER_LOGIN_STATUS);
        log.debug("uid 为 {} 的用户已登录, 并获取了导航栏信息", uid);
        return userAbstractDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) throws ServletException, IOException {
        UserBaseDto userBaseDto = userMapper.selectUserByUsernameAndPassword(username, password);
        log.debug("用户使用用户名为 {} 和密码为 {} 的账号进行登录", username, password);
        // 不存在该用户
        if (userBaseDto == null) {
            log.debug("用户名为 {} 和密码为 {} 的账号不存在", username, password);
            // 返回 404 状态码
            return Result.notExist();
        }
        // 存在该用户
        else {
            log.info("用户名为 {} 的用户登录成功!", username);
            Integer uid = userBaseDto.getUid();
            // 更新登录时间
            userMapper.updateUserLatestLoginTime(uid);
            // 添加 cookie 维持用户登录状态
            response.addCookie(GeneratorCookie.createIdentityCookie(userBaseDto.getUserId()));

            HttpSession session = request.getSession();
            session.setAttribute("uid", uid);
            session.setAttribute("userId", userBaseDto.getUserId());
            session.setAttribute("nickname", userBaseDto.getUsername());
            session.setMaxInactiveInterval(sessionSurvivalTime);
            return Result.ok(null);
        }
    }

    @Override
    public void logoutHuadiao(Cookie cookie, Integer uid, String userId, String nickname) {
        log.info("uid 为 {}, nickname 为 {}, userId 为 {} 的用户尝试退出登录", uid, nickname, userId);
        // 删除 cookie
        cookie.setMaxAge(0);
        log.info("uid 为 {}, nickname 为 {}, userId 为 {} 的用户退出登录成功", uid, nickname, userId);
    }

    @Override
    public void getCheckCode(HttpServletResponse response, HttpSession session, String jsessionid) throws Exception {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(codeImageWidth, codeImageHeight, codeLength, codeDisturbCount);
        circleCaptcha.write(response.getOutputStream());
        String code = circleCaptcha.getCode();
        // 保存验证码到 redis
        userBaseJedisUtil.setCheckCode(jsessionid, code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> registerHuadiao(HttpSession session, String username, String password, String confirmPassword, String checkCode, String jsessionid) throws Exception {
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
        log.debug("用户通过所有的注册检查! 下面开始注册新花凋用户");
        boolean end = false;
        String userId = null;
        int uid = userBaseJedisUtil.generateUid();
        log.debug("第 {} (uid) 位用户即将加入!", uid);
        // 生成的 userId 如果重复, 循环生成
        while (!end) {
            userId = CreateHuadiaoUserId.createUserId();
            Integer existedUid = userMapper.selectUidByUserId(userId);
            if(existedUid != null) {
                log.trace("为新用户创建的 userId {} 重复, 准备再次创建", userId);
                continue;
            }
            end = true;
        }
        // 新增花凋用户
        userMapper.insertNewHuadiaoUser(uid, userId, username, password);
        // 新增用户信息
        userInfoMapper.insertOrUpdateUserInfoByUid(uid, userId, null, SexEnum.NO_KNOWN.value, null, null);
        log.trace("新增用户信息成功 (uid: {}, userId: {})", uid, userId);
        // 新增用户个人主页
        userInfoMapper.insertUserHomepageByUid(uid);
        log.trace("新增用户个人主页信息成功 (uid: {}, userId: {})", uid, userId);
        // 新增用户设置
        userSettingsMapper.insertOrUpdateUserSettingsByUid(uid, null);
        log.trace("新增用户账号信息成功 (uid: {}, userId{})", uid, userId);
        // 新增用户番剧信息
        huadiaoHouseMapper.insertHuadiaoHouseInfoByUid(uid, null, null, null, null, null, null);
        UserEs userEs = new UserEs();
        userEs.setUid(uid);
        userEs.setUserId(userId);
        // 保存至 elasticsearch
        userRepository.save(userEs);
        log.info("用户 uid {}, username: {}, userId: {} 注册成功", uid, username, userId);
        // 注册成功删除验证码
        userBaseJedisUtil.deleteCheckCode(jsessionid);
        return Result.ok(succeedRegister);
    }

    @Override
    public boolean userExist(Integer uid) {
        return userMapper.selectUserIdByUid(uid) != null;
    }
}
