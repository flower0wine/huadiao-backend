package com.huadiao.service.elasticsearch;

import com.huadiao.entity.Result;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
public interface UserService {

    /**
     * 根据 昵称 获取用户
     *
     * @param uid            用户 uid
     * @param userId         用户 id
     * @param searchNickname 要搜索的昵称
     * @param offset         页码
     * @param row            每页大小
     * @return 返回匹配的用户
     */
    Result<?> findUserByNickname(Integer uid, String userId, String searchNickname, Integer offset, Integer row);

    /**
     * 根据 用户 id 获取用户
     *
     * @param uid          用户 uid
     * @param userId       用户 id
     * @param searchUserId 要搜索的用户 id
     * @return 返回匹配的用户
     */
    Result<?> findUserByUserId(Integer uid, String userId, String searchUserId);
}
