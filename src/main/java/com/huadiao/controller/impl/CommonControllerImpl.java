package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.CommonController;
import com.huadiao.entity.Result;
import com.huadiao.service.CommonService;
import com.huadiao.service.design.template.login.HuadiaoLoginInspector;
import com.huadiao.service.design.template.register.GithubRegisterInspector;
import com.huadiao.service.design.template.register.GoogleRegisterInspector;
import com.huadiao.service.design.template.register.HuadiaoRegisterInspector;
import com.huadiao.service.design.template.register.RegisterInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2024 年 02 月 09 日
 */
@Controller
@RequestMapping("/common")
public class CommonControllerImpl extends AbstractController implements CommonController {
    private CommonService commonService;
    private HuadiaoLoginInspector huadiaoLoginInspector;
    private HuadiaoRegisterInspector registerInspector;
    private GithubRegisterInspector githubRegisterInspector;
    private GoogleRegisterInspector googleRegisterInspector;

    @Autowired
    public CommonControllerImpl(CommonService commonService, HuadiaoLoginInspector huadiaoLoginInspector, HuadiaoRegisterInspector registerInspector, GithubRegisterInspector githubRegisterInspector, GoogleRegisterInspector googleRegisterInspector) {
        this.commonService = commonService;
        this.huadiaoLoginInspector = huadiaoLoginInspector;
        this.registerInspector = registerInspector;
        this.githubRegisterInspector = githubRegisterInspector;
        this.googleRegisterInspector = googleRegisterInspector;
    }

    @Override
    @GetMapping("/register/github")
    public String githubRegister(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String code) throws Exception {
        return this.oauthRegister(request, response, code, githubRegisterInspector);
    }

    @Override
    @GetMapping("/register/google")
    public String googleRegister(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String code) throws Exception {
        return this.oauthRegister(request, response, code, googleRegisterInspector);
    }

    private String oauthRegister(HttpServletRequest request, HttpServletResponse response, String code, RegisterInspector registerInspector) throws Exception {
        String proxyHost = System.getProperty("https.proxyHost");
        String proxyPort = System.getProperty("https.proxyPort");

        if (proxyHost != null && proxyPort != null) {
            System.out.println("代理地址：" + proxyHost);
            System.out.println("代理端口：" + proxyPort);
        } else {
            System.out.println("未检测到代理设置。");
        }
        registerInspector.flushThreadLocal();

        code = URLDecoder.decode(code, StandardCharsets.UTF_8.name());
        registerInspector.getCodeThreadLocal().set(code);
        commonService.registerHuadiao(request, response, registerInspector);
        return "redirect:" + frontendHost;
    }

    @Override
    @ResponseBody
    @PostMapping("/login")
    public Result<?> huadiaoUserLogin(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestBody Map<String, String> map,
                                      HttpSession session) throws Exception {
        huadiaoLoginInspector.flushThreadLocal();
        huadiaoLoginInspector.getRequestBodyThreadLocal().set(map);
        return commonService.huadiaoUserLogin(request, response, huadiaoLoginInspector);
    }

    @Override
    @ResponseBody
    @GetMapping("/registerCode")
    public void getCheckCode(HttpServletResponse response,
                             HttpSession session,
                             @CookieValue("JSESSIONID") String jsessionid) throws Exception {
        commonService.getCheckCode(response, session, jsessionid);
    }

    @Override
    @ResponseBody
    @PostMapping("/register")
    public Result<?> registerHuadiao(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestBody Map<String, String> map,
                                     @CookieValue("JSESSIONID") String jsessionid) throws Exception {
        map.put("jsessionid", jsessionid);

        registerInspector.flushThreadLocal();
        registerInspector.getThreadLocal().set(map);
        return commonService.registerHuadiao(request, response, registerInspector);
    }

    @Override
    @GetMapping("/validate")
    public Result<?> validateUser(HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");

        if (uid != null) {
            return Result.ok("当前用户已登录");
        } else {
            return Result.notAuthorize("当前用户未登录");
        }
    }
}
