package com.huadiao.service;

import com.huadiao.entity.AccountSettings;
import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.entity.dto.userdto.UserShareDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 业务层: 与账号相关的操作的接口
 * @author flowerwine
 */
public interface UserService {

    /**
     * 获取花凋头部面板用户信息
     * @param uid 用户 uid
     * @return 返回用户信息
     */
    UserAbstractDto getHuadiaoHeaderUserInfo(Integer uid);

    /**
     * 花凋用户登录
     * @param username 用户名
     * @param password 密码
     * @throws Exception 可能抛出异常
     */
    void huadiaoUserLogin(String username, String password) throws Exception;

    /**
     * 退出登录
     * @param cookie 退出登录, 要删除的 cookie -> User_ID
     * @param request 请求对象
     */
    void logoutHuadiao(Cookie cookie, HttpServletRequest request);

    /**
     * 获取注册账号的验证码
     * @param session session
     * @throws Exception 可能抛出异常
     */
    void getCheckCode(HttpSession session) throws Exception;

    /**
     * 注册花凋新用户
     * @param session session 对象
     * @param username 用户名
     * @param password 密码
     * @param confirmPassword 再次确认密码
     * @param checkCode 验证码
     * @return 返回错误或正确标识
     * @throws Exception 可能抛出异常
     */
    String registerHuadiao(HttpSession session, String username, String password, String confirmPassword, String checkCode) throws Exception;

    /**
     * 获取用户共享信息
     * @param uid 用户 uid
     * @return 返回共享信息
     */
    UserShareDto getUserShareInfo(Integer uid);

    /**
     * 根据 uid 判断用户是否存在
     * @param uid 用户 uid
     * @return 存在返回 true, 否则返回 false
     */
    boolean userExist(Integer uid);
}
