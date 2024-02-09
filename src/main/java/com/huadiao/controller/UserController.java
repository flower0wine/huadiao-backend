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
     * 退出登录
     *
     * @param cookie  对应的 User_ID cookie
     * @param session session 对象
     */
    void logoutHuadiao(Cookie cookie, HttpSession session);

}
