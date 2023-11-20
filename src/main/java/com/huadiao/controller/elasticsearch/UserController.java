package com.huadiao.controller.elasticsearch;

import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
public interface UserController {

    /**
     * 根据 昵称 查找用户
     *
     * @param session        session 对象
     * @param nickname 要搜索的昵称
     * @param offset         页码
     * @param row            每页显示数量
     * @return 匹配的用户
     */
    Result<?> findUserByNickname(HttpSession session, String nickname, Integer offset, Integer row);

    /**
     * 根据 用户 id 获取用户
     *
     * @param session      session 对象
     * @param userId 要搜索的用户 id
     * @return 返回匹配的用户
     */
    Result<?> findUserByUserId(HttpSession session, String userId);
}
