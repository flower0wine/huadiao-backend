package com.huadiao.service;

import com.huadiao.entity.Result;
import com.huadiao.service.design.template.login.LoginInspector;
import com.huadiao.service.design.template.register.RegisterInspector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2024 年 02 月 09 日
 */
public interface CommonService extends Service {

    /**
     * 花凋用户登录
     *
     * @param request  请求
     * @param response 响应
     * @return 返回提示信息
     * @throws Exception 可能抛出异常
     */
    Result<?> huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, LoginInspector loginInspector) throws Exception;

    /**
     * 获取注册账号的验证码
     *
     * @param response   响应
     * @param session    session
     * @param jsessionid jsessionid
     * @throws Exception 可能抛出异常
     */
    void getCheckCode(HttpServletResponse response, HttpSession session, String jsessionid) throws Exception;

    /**
     * 注册花凋新用户
     *
     * @param request  请求
     * @param response 响应
     * @param registerInspector 检查注册信息
     * @return 返回错误或正确标识
     * @throws Exception 可能抛出异常
     */
    Result<?> registerHuadiao(HttpServletRequest request, HttpServletResponse response, RegisterInspector registerInspector) throws Exception;

}
