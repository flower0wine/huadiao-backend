package com.huadiao.service;

import com.huadiao.entity.Result;
import com.huadiao.entity.dto.userdto.UserAbstractDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 业务层: 与账号相关的操作的接口
 *
 * @author flowerwine
 */
public interface UserService {

    /**
     * 获取花凋头部面板用户信息
     *
     * @param uid 用户 uid
     * @return 返回用户信息
     */
    UserAbstractDto getHuadiaoHeaderUserInfo(Integer uid);

    /**
     * 花凋用户登录
     *
     * @param request  请求
     * @param response 响应
     * @param username 用户名
     * @param password 密码
     * @return 返回提示信息
     * @throws Exception 可能抛出异常
     */
    Result<String> huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) throws Exception;

    /**
     * 退出登录
     *
     * @param cookie   退出登录, 要删除的 cookie -> User_ID
     * @param uid      用户 uid
     * @param userId   用户 id
     * @param nickname 用户昵称
     */
    void logoutHuadiao(Cookie cookie, Integer uid, String userId, String nickname);

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
     * @param session         session 对象
     * @param username        用户名
     * @param password        密码
     * @param confirmPassword 再次确认密码
     * @param checkCode       验证码
     * @param jsessionid      jsessionid
     * @return 返回错误或正确标识
     * @throws Exception 可能抛出异常
     */
    Result<?> registerHuadiao(HttpSession session, String username, String password, String confirmPassword, String checkCode, String jsessionid) throws Exception;


    /**
     * 根据 uid 判断用户是否存在
     *
     * @param uid 用户 uid
     * @return 存在返回 true, 否则返回 false
     */
    boolean userExist(Integer uid);
}
