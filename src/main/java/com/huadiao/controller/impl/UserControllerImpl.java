package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.UserController;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.entity.dto.userdto.UserShareDto;
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
public class UserControllerImpl extends AbstractController implements UserController {
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
        Integer uid = (Integer) session.getAttribute(uidKey);
        return userService.getHuadiaoHeaderUserInfo(uid);
    }

    @Override
    @PostMapping("/common/login")
    public Result<String> huadiaoUserLogin(@RequestBody Map<String, String> map) throws Exception {
        return userService.huadiaoUserLogin(map.get("username"), map.get("password"));
    }

    @Override
    @GetMapping("/logoutHuadiao")
    public void logoutHuadiao(@CookieValue("User_ID") Cookie cookie, HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        String nickname = (String) session.getAttribute(nicknameKey);
        userService.logoutHuadiao(cookie, uid, userId, nickname);
        session.invalidate();
    }

    @Override
    @GetMapping("/common/registerCode")
    public void getCheckCode(HttpSession session) throws Exception {
        userService.getCheckCode(session);
    }

    @Override
    @PostMapping("/common/register")
    public String registerHuadiao(@RequestBody Map<String, String> map, HttpSession session) throws Exception {
        return userService.registerHuadiao(session, map.get("username"), map.get("password"), map.get("confirmPassword"), map.get("checkCode"));
    }

    @Override
    @PostMapping("/userInfo")
    public Result<?> insertOrUpdateUserInfo(@RequestBody Map<String, String> map, HttpSession session) throws Exception {
        Integer uid = (Integer) session.getAttribute(uidKey);
        Date bornDate = new Date(Long.parseLong(map.get("bornDate")));
        String sex = map.get("sex");
        String school = map.get("school");
        String canvases = map.get("canvases");
        String nickname = map.get(nicknameKey);
        String userId = session.getAttribute(userIdKey).toString();
        return userInfoService.insertOrUpdateUserInfo(uid, userId, nickname, canvases, sex, bornDate, school);
    }

    @Override
    @GetMapping("/userInfo")
    public Result<?> getUserInfo(HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return userInfoService.getMineInfo(uid, userId);
    }

    @Override
    @GetMapping("/setting/message/get")
    public Result<?> getUserMessageSettings(HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return userSettingsService.getMessageSettings(uid, userId);
    }

    @Override
    @GetMapping("/setting/account/get")
    public Result<?> getUserAccountSettings(HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return userSettingsService.getUserSettings(uid, userId);
    }

    @Override
    @PostMapping("/setting/modify")
    public Result<?> modifyUserSettings(HttpSession session, @RequestBody Map<String, String> settingMap) throws Exception {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        if(settingMap == null) {
            return Result.blankParam();
        }
        // set 集合去重, 防止可能的错误, 或者入侵
        Set<String> settingsSet = new HashSet<>(settingMap.values());
        return userSettingsService.modifyAccountSettings(uid, userId, settingsSet);
    }

    @Override
    @GetMapping("/share")
    public UserShareDto getUserShare(HttpSession httpSession) {
        Integer uid = (Integer) httpSession.getAttribute(uidKey);
        return userService.getUserShareInfo(uid);
    }
}
