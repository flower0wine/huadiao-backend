package com.huadiao.controller.impl;

import com.huadiao.controller.UserController;
import com.huadiao.entity.AccountSettings;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.accountsettings.MessageSettingsDto;
import com.huadiao.entity.dto.accountsettings.PublicInfoSettingsDto;
import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.entity.dto.userdto.UserShareDto;
import com.huadiao.entity.dto.userinfodto.UserInfoDto;
import com.huadiao.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户信息控制器实现类
 */
@RestController
@CrossOrigin
public class UserControllerImpl implements UserController {
    private UserService userService;
    private PoemService poemService;
    private UserInfoService userInfoService;
    private UserSettingsService userSettingsService;

    @Autowired
    public UserControllerImpl(UserService userService, PoemService poemService, UserInfoService userInfoService, UserSettingsService userSettingsService) {
        this.userService = userService;
        this.poemService = poemService;
        this.userInfoService = userInfoService;
        this.userSettingsService = userSettingsService;
    }

    @Override
    @GetMapping("/huadiaoHeader")
    public UserAbstractDto getHuadiaoHeaderUserInfo(HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        return userService.getHuadiaoHeaderUserInfo(uid);
    }

    @Override
    @PostMapping("/common/login")
    public Result<String> huadiaoUserLogin(@RequestBody Map<String, String> map) throws Exception {
        return userService.huadiaoUserLogin(map.get("username"), map.get("password"));
    }

    @Override
    @GetMapping("/logoutHuadiao")
    public void logoutHuadiao(@CookieValue("User_ID") Cookie cookie, HttpServletRequest request) {
        userService.logoutHuadiao(cookie, request);
    }

    @Override
    @GetMapping("/common/registerCode")
    public void getCheckCode(HttpSession session) throws Exception {
        userService.getCheckCode(session);
    }

    @Override
    @PostMapping("/common/register")
    public String registerHuadiao(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return userService.registerHuadiao(session, map.get("username"), map.get("password"), map.get("confirmPassword"), map.get("checkCode"));
    }

    @Override
    @PostMapping("/userInfo")
    public Result<?> insertOrUpdateUserInfo(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer uid = (Integer) session.getAttribute("uid");
        Date bornDate = new Date(Long.parseLong(map.get("bornDate")));
        String sex = map.get("sex");
        String school = map.get("school");
        String canvases = map.get("canvases");
        String nickname = map.get("nickname");
        String userId = session.getAttribute("userId").toString();
        return userInfoService.insertOrUpdateUserInfo(uid, userId, nickname, canvases, sex, bornDate, school);
    }

    @Override
    @GetMapping("/userInfo")
    public Result<?> getUserInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return userInfoService.getMineInfo(uid, userId);
    }

    @Override
    @GetMapping("/messageSettings")
    public MessageSettingsDto getUserMessageSettings(HttpServletRequest request) {
        return null;
    }

    @Override
    @GetMapping("/accountSettings")
    public AccountSettings getUserAccountSettings(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return userSettingsService.getUserSettings(uid, userId);
    }

    @Override
    @PostMapping("/accountSettings")
    public String modifyUserSettings(HttpServletRequest request, @RequestBody Map<String, String> settingMap) throws Exception {
        HttpSession session = request.getSession();
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        if(settingMap == null) {
            return AbstractUserSettingsService.ACCOUNT_SETTING_UPDATE_FAIL;
        }
        // set 集合去重, 防止可能的错误, 或者入侵
        Set<String> settingsSet = new HashSet<>(settingMap.values());
        return userSettingsService.modifyAccountSettings(uid, userId, settingsSet);
    }

    @Override
    @GetMapping("/share")
    public UserShareDto getUserShare(HttpSession httpSession) {
        Integer uid = (Integer) httpSession.getAttribute("uid");
        return userService.getUserShareInfo(uid);
    }
}
