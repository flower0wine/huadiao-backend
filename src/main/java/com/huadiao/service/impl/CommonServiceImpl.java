package com.huadiao.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.user.UserShareDto;
import com.huadiao.mapper.HomepageMapper;
import com.huadiao.mapper.HuadiaoHouseMapper;
import com.huadiao.mapper.UserMapper;
import com.huadiao.mapper.UserSettingsMapper;
import com.huadiao.service.AbstractCommonService;
import com.huadiao.service.design.template.login.LoginInspector;
import com.huadiao.service.design.template.register.HuadiaoRegisterInspector;
import com.huadiao.service.design.template.register.RegisterInspector;
import com.huadiao.util.CookieUtil;
import com.huadiao.util.CreateHuadiaoUserId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2024 年 02 月 09 日
 */
@Slf4j
@Service
@AllArgsConstructor
public class CommonServiceImpl extends AbstractCommonService {
    private UserMapper userMapper;
    private HomepageMapper homepageMapper;
    private UserSettingsMapper userSettingsMapper;
    private HuadiaoHouseMapper huadiaoHouseMapper;
    private CookieUtil cookieUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, LoginInspector loginInspector) {
        Result<?> result = loginInspector.check();
        if (!result.succeed()) {
            return result;
        }
        Integer uid = loginInspector.getUid();
        String userId = loginInspector.getUserId();

        this.login(uid, userId, request, response);
        return Result.ok(null);
    }

    private void login(Integer uid, String userId, HttpServletRequest request, HttpServletResponse response) {
        // 更新登录时间
        userMapper.updateUserLatestLoginTime(uid);
        // 添加 cookie 维持用户登录状态
        ResponseCookie cookie = cookieUtil.getUserCookie(userId);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        HttpSession session = request.getSession();
        session.setAttribute("uid", uid);
        session.setAttribute("userId", userId);
        session.setAttribute("nickname", userId);
        session.setMaxInactiveInterval(cookieProperties.sessionSurvivalTime);
    }

    @Override
    public void getCheckCode(HttpServletResponse response, HttpSession session, String jsessionid) throws Exception {
        Integer codeImageWidth = codeProperties.codeImageWidth;
        Integer codeImageHeight = codeProperties.codeImageHeight;
        Integer codeLength = codeProperties.codeLength;
        Integer codeDisturbCount = codeProperties.codeDisturbCount;

        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(codeImageWidth, codeImageHeight, codeLength, codeDisturbCount);
        circleCaptcha.write(response.getOutputStream());
        String code = circleCaptcha.getCode();
        // 保存验证码到 redis
        userBaseJedisUtil.setCheckCode(jsessionid, code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> registerHuadiao(HttpServletRequest request, HttpServletResponse response, RegisterInspector registerInspector) {
        Result<?> result = registerInspector.check();

        if(!result.succeed()) {
            return result;
        }
        // 非本站账号注册即登录
        if (result.getData() != null && !(registerInspector instanceof HuadiaoRegisterInspector)) {
            UserShareDto userShareDto = (UserShareDto) result.getData();
            Integer uid = userShareDto.getUid();
            String userId = userShareDto.getUserId();
            this.login(uid, userId, request, response);
            log.debug("{} 账号注册成功",registerInspector.getLoginType());
            return Result.ok("注册成功");
        }
        log.debug("用户通过所有的注册检查! 下面开始注册新花凋用户");

        boolean end = false;
        String userId = null;
        int uid = userBaseJedisUtil.generateUid();
        log.debug("uid 为 {} 的用户即将加入 ~~<< 花凋 >>~~", uid);

        // 生成的 userId 如果重复, 循环生成
        while (!end) {
            userId = CreateHuadiaoUserId.createUserId();
            Integer existedUid = userMapper.selectUidByUserId(userId);
            if (existedUid != null) {
                log.debug("为新用户创建的 userId {} 重复, 准备再次创建", userId);
                continue;
            }
            end = true;
        }

        // 新增用户设置
        userSettingsMapper.insertOrUpdateUserSettingsByUid(uid, null);
        log.trace("新增用户账号信息成功 (uid: {}, userId: {})", uid, userId);
        // 新增用户番剧信息
        huadiaoHouseMapper.insertHuadiaoHouseInfoByUid(uid);

        registerInspector.addUser(uid, userId, request, response);

        log.info("用户 uid {}, userId: {} 注册成功", uid, userId);

        // 非本站账号注册即登录
        if (!(registerInspector instanceof HuadiaoRegisterInspector)) {
            this.login(uid, userId, request, response);
        }
        return Result.ok("注册成功");
    }
}
