package com.huadiao.controller;

import com.huadiao.entity.dto.user.UserAbstractDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

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
     * 退出登录
     *
     * @param cookie  对应的 User_ID cookie
     * @param session session 对象
     */
    void logoutHuadiao(Cookie cookie, HttpSession session);

}
