package com.huadiao.service;

import com.huadiao.entity.dto.user.UserAbstractDto;

import javax.servlet.http.Cookie;

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
     * 退出登录
     *
     * @param cookie   退出登录, 要删除的 cookie -> User_ID
     * @param uid      用户 uid
     * @param userId   用户 id
     * @param nickname 用户昵称
     */
    void logoutHuadiao(Cookie cookie, Integer uid, String userId, String nickname);
}
