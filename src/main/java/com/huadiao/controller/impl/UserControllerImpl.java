package com.huadiao.controller.impl;

import com.huadiao.controller.ErrorController;
import com.huadiao.controller.UserController;
import com.huadiao.entity.dto.UserDetailDto;
import com.huadiao.service.AbstractUserService;
import com.huadiao.service.PoemService;
import com.huadiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    public UserControllerImpl(UserService userService, PoemService poemService) {
        this.userService = userService;
        this.poemService = poemService;
    }

    @Override
    @GetMapping("/huadiaoIndex")
    public Map<String, Object> getUserHuadiaoIndexInfo(@CookieValue("User_ID") String userId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>(2);
        map.put("userHuadiaoIndexInfo", userService.getUserHuadiaoIndexInfo(request, response));
        map.put("huadiaoIndexPoem", poemService.getPoem());
        return map;
    }

    @Override
    @PostMapping("/login")
    public void huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception {
        userService.huadiaoUserLogin(request, response, map.get("username"), map.get("password"));
    }

    @Override
    @GetMapping("/logoutHuadiao")
    public void logoutHuadiao(@CookieValue("User_ID") Cookie cookie, HttpServletRequest request) {
        userService.logoutHuadiao(cookie, request);
    }

    @Override
    @GetMapping("/registerCode")
    public void getCheckCode(HttpSession session, HttpServletResponse response) throws Exception {
        userService.getCheckCode(session, response);
    }

    @Override
    @PostMapping("/register")
    public String registerHuadiao(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return userService.registerHuadiao(session, map.get("username"), map.get("password"), map.get("confirmPassword"), map.get("checkCode"));
    }

    @Override
    @PostMapping("/userInfo")
    public String insertOrUpdateUserInfo(Map<String, String> map, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer uid = (Integer) session.getAttribute("uid");
        if(uid == null) {
            // 没有登录, 重定向到主页
            response.sendRedirect(HOST);
        } else {
            String bornDate = map.get("bornDate");
            // 检查出生日期是否符合要求
            Matcher matcher = AbstractUserService.bornDateReg.matcher(bornDate);
            if(!matcher.matches()) {
                return AbstractUserService.WRONG_BORN_DATE;
            }
            String sex = map.get("sex");
            String school = map.get("school");
            String canvases = map.get("canvases");
            String nickname = map.get("nickname");
            String userId = session.getAttribute("userId").toString();
            userService.insertOrUpdateUserInfo(uid, userId, nickname, canvases, sex, bornDate, school);
        }
        return null;
    }

    @Override
    @GetMapping("/userInfo")
    public UserDetailDto getUserInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        Integer uid = (Integer) session.getAttribute("uid");
        if(uid != null) {
            return userService.getUserInfo(uid);
        } else {
            // 没有用户返回 404 状态码
            request.getRequestDispatcher(ErrorController.NOT_FOUND_DISPATCHER_PATH).forward(request, response);
            return null;
        }
    }


}
