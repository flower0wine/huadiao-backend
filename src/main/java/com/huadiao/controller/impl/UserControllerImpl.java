package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.UserController;
import com.huadiao.entity.dto.user.UserAbstractDto;
import com.huadiao.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户信息控制器实现类
 */
@RestController
public class UserControllerImpl extends AbstractController implements UserController {
    private UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
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
    @GetMapping("/header")
    public UserAbstractDto getHuadiaoHeaderUserInfo(HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        return userService.getHuadiaoHeaderUserInfo(uid);
    }

}
