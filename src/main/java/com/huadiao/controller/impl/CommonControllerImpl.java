package com.huadiao.controller.impl;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.huadiao.controller.AbstractController;
import com.huadiao.controller.CommonController;
import com.huadiao.entity.Result;
import com.huadiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2024 年 02 月 09 日
 */
@RestController
@RequestMapping("/common")
public class CommonControllerImpl extends AbstractController implements CommonController {
    private UserService userService;

    @Autowired
    public CommonControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/register/github")
    public Result<?> githubRegister(String code) {
        String clientId = "cdbdc26e987d1cf6c058";
        String clientSecret = "8f928bdd4c8cef961aa3b8387ee50d950be4cde3";

        Map<String, Object> map = new HashMap<>(8);
        map.put("client_id", clientId);
        map.put("client_secret", clientSecret);
        map.put("code", code);

        String result = HttpRequest.post("https://github.com/login/oauth/access_token")
                .header(Header.ACCEPT, "application/json")
                .form(map)
                .execute()
                .body();
        System.out.println(result);
        return null;
    }

    @Override
    @PostMapping("/login")
    public Result<String> huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception {
        return userService.huadiaoUserLogin(request, response, map.get("username"), map.get("password"));
    }

    @Override
    @GetMapping("/registerCode")
    public void getCheckCode(HttpServletResponse response,
                             HttpSession session,
                             @CookieValue("JSESSIONID") String jsessionid) throws Exception {
        userService.getCheckCode(response, session, jsessionid);
    }

    @Override
    @PostMapping("/register")
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
