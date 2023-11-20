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
    @GetMapping("/header")
    public UserAbstractDto getHuadiaoHeaderUserInfo(HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        return userService.getHuadiaoHeaderUserInfo(uid);
    }

    @Override
    @PostMapping("/common/login")
    public Result<String> huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception {
        return userService.huadiaoUserLogin(request, response, map.get("username"), map.get("password"));
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
    public void getCheckCode(HttpServletResponse response,
                             HttpSession session,
                             @CookieValue("JSESSIONID") String jsessionid) throws Exception {
        userService.getCheckCode(response, session, jsessionid);
    }

    @Override
    @PostMapping("/common/register")
    public Result<?> registerHuadiao(@RequestBody Map<String, String> map,
                                     HttpSession session,
                                     @CookieValue("JSESSIONID") String jsessionid) throws Exception {
        return userService.registerHuadiao(
                session,
                map.get("username"),
                map.get("password"),
                map.get("confirmPassword"),
                map.get("checkCode"),
                jsessionid
        );
    }

}
