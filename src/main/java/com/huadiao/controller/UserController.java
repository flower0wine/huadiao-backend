package com.huadiao.controller;

import com.huadiao.entity.Result;
import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.entity.dto.userdto.UserShareDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 用户信息控制器接口
 *
 * @author flowerwine
 */
public interface UserController extends Controller {

    /**
     * 获取花凋头部面板用户信息
     *
     * @param session session 对象
     * @return 返回用户信息
     */
    UserAbstractDto getHuadiaoHeaderUserInfo(HttpSession session);

    /**
     * 或吊用户进行登录
     *
     * @param request 请求
     * @param response 响应
     * @param map 请求体参数集合
     * @return 返回提示信息
     * @throws Exception 可能抛出异常
     */
    Result<String> huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, Map<String, String> map) throws Exception;

    /**
     * 退出登录
     *
     * @param cookie  对应的 User_ID cookie
     * @param session session 对象
     */
    void logoutHuadiao(Cookie cookie, HttpSession session);

    /**
     * 获取注册账号的验证码
     *
     * @param response 响应
     * @param session session
     * @param jsessionid jsessionid
     * @throws Exception 可能抛出异常
     */
    void getCheckCode(HttpServletResponse response, HttpSession session, String jsessionid) throws Exception;

    /**
     * 注册花凋账号
     *
     * @param map     账号, 密码, 验证码的集合
     * @param session session 对象
     * @param jsessionid jsessionid
     * @return 返回错误或正确标识
     * @throws Exception 可能抛出异常
     */
    Result<?> registerHuadiao(Map<String, String> map, HttpSession session, String jsessionid) throws Exception;

}
