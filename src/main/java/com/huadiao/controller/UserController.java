package com.huadiao.controller;

import com.huadiao.entity.dto.UserDetailDto;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * 用户信息控制器接口
 * @author flowerwine
 */
public interface UserController extends Controller {
    /**
     * 获取花凋首页信息: 用户信息, 诗词等
     * @param userId 用户id
     * @param request 请求对象
     * @param response 响应对象
     * @throws Exception 可能抛出异常
     * @return 返回花凋首页信息
     */
    Map<String, Object> getUserHuadiaoIndexInfo(String userId, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 或吊用户进行登录
     * @param request 请求对象
     * @param response 响应对象
     * @param map 请求体参数集合
     * @throws Exception 可能抛出异常
     */
    void huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, Map<String, String> map) throws Exception;

    /**
     * 退出登录
     * @param cookie 对应的 User_ID cookie
     * @param request 请求对象
     */
    void logoutHuadiao(Cookie cookie, HttpServletRequest request);

    /**
     * 获取注册账号的验证码
     * @param session session
     * @param response 响应对象
     * @throws Exception 可能抛出异常
     */
    void getCheckCode(HttpSession session, HttpServletResponse response) throws Exception;

    /**
     * 注册花凋账号
     * @param map 账号, 密码, 验证码的集合
     * @param session session 对象
     * @param request 请求对象
     * @param response 响应对象
     * @return 返回错误或正确标识
     * @throws Exception 可能抛出异常
     */
    String registerHuadiao(Map<String, String> map, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 新增用户信息, 如果用户信息存在则更改用户信息
     * @param map 用户信息集合
     * @param session session 对象
     * @param request 请求对象
     * @param response 响应对象
     * @return 返回错误提示信息, 如日期错误返回 wrongBornDate
     * @throws Exception 可能抛出异常
     */
    String insertOrUpdateUserInfo(Map<String, String> map, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 获取用户信息
     * @param request 请求对象
     * @param response 响应对象
     * @param session session 对象
     * @return 返回用户详细信息
     * @throws Exception 可能抛出错误
     */
    UserDetailDto getUserInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception;
}
