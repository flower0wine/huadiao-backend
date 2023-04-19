package com.huadiao.controller.impl;

import com.huadiao.controller.UserController;
import com.huadiao.entity.AccountSettings;
import com.huadiao.entity.dto.accountsettings.MessageSettingsDto;
import com.huadiao.entity.dto.accountsettings.PublicInfoSettingsDto;
import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.entity.dto.userinfodto.UserInfoDto;
import com.huadiao.service.PoemService;
import com.huadiao.service.UserInfoService;
import com.huadiao.service.UserService;
import com.huadiao.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public UserAbstractDto getHuadiaoHeaderUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer uid = (Integer) session.getAttribute("uid");
        return userService.getHuadiaoHeaderUserInfo(uid);
    }

    @Override
    @PostMapping("/common/login")
    public void huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception {
        userService.huadiaoUserLogin(request, response, map.get("username"), map.get("password"));
    }

    @Override
    @GetMapping("/logoutHuadiao")
    public void logoutHuadiao(@CookieValue("User_ID") Cookie cookie, HttpServletRequest request) {
        userService.logoutHuadiao(cookie, request);
    }

    @Override
    @GetMapping("/common/registerCode")
    public void getCheckCode(HttpSession session, HttpServletResponse response) throws Exception {
        userService.getCheckCode(session, response);
    }

    @Override
    @PostMapping("/common/register")
    public String registerHuadiao(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return userService.registerHuadiao(session, map.get("username"), map.get("password"), map.get("confirmPassword"), map.get("checkCode"));
    }

    @Override
    @PostMapping("/userInfo")
    public String insertOrUpdateUserInfo(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer uid = (Integer) session.getAttribute("uid");
        String bornDate = map.get("bornDate");
        String sex = map.get("sex");
        String school = map.get("school");
        String canvases = map.get("canvases");
        String nickname = map.get("nickname");
        String userId = session.getAttribute("userId").toString();
        return userInfoService.insertOrUpdateUserInfo(uid, userId, nickname, canvases, sex, bornDate, school);
    }

    @Override
    @GetMapping("/userInfo")
    public UserInfoDto getUserInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return userInfoService.getUserInfo(uid, userId);
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
    public String modifyUserSettings(HttpServletRequest request, @RequestBody Map<String, String> settingMap) {
        HttpSession session = request.getSession();
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        // set 集合去重, 防止可能的错误, 或者入侵
        Set<String> settingsSet = new HashSet<>(settingMap.values());
        return userSettingsService.modifyAccountSettings(uid, userId, settingsSet);
    }
}
