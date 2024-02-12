package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.CommonController;
import com.huadiao.entity.Result;
import com.huadiao.service.CommonService;
import com.huadiao.service.design.template.login.HuadiaoLoginInspector;
import com.huadiao.service.design.template.register.GithubRegisterInspector;
import com.huadiao.service.design.template.register.HuadiaoRegisterInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @Autowired
    public CommonControllerImpl(CommonService commonService, HuadiaoLoginInspector huadiaoLoginInspector, HuadiaoRegisterInspector registerInspector, GithubRegisterInspector githubRegisterInspector) {
        this.commonService = commonService;
        this.huadiaoLoginInspector = huadiaoLoginInspector;
        this.registerInspector = registerInspector;
        this.githubRegisterInspector = githubRegisterInspector;
    }

    @Override
    @GetMapping("/register/github")
    public String githubRegister(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String code) throws Exception {
        githubRegisterInspector.flushThreadLocal();

        githubRegisterInspector.getCodeThreadLocal().set(code);
        commonService.registerHuadiao(request, response, githubRegisterInspector);
        return "redirect:" + HUADIAO_URI;
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
}
