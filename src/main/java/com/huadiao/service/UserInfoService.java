package com.huadiao.service;

import com.huadiao.entity.Result;
import com.huadiao.entity.dto.user.UserShareDto;

/**
 * 业务层: 与用户信息相关的操作的接口
 * @author flowerwine
 */

public interface UserInfoService {

    /**
     * 新增用户信息, 如果用户信息存在则更改用户信息
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param nickname 昵称
     * @param canvases 个人简介
     * @param sex 性别
     * @param bornDate 出生日期
     * @param school 学校
     * @throws Exception 可能抛出异常
     * @return 返回错误提示
     */
    Result<?> insertOrUpdateUserInfo(Integer uid, String userId, String nickname,
                                  String canvases, String sex,
                                  Long bornDate, String school) throws Exception;

    /**
     * 获取用户信息
     * @param myUid 用户 uid
     * @param uid 要查询的用户 uid
     * @param userId 用户 id
     * @return 返回用户详细信息
     */
    Result<?> getUserInfo(Integer myUid, Integer uid, String userId);

    /**
     * 获取用户共享信息
     *
     * @param uid 用户 uid
     * @return 返回共享信息
     */
    UserShareDto getUserShareInfo(Integer uid);

    /**
     * 获取用户账户信息
     * @param uid 用户 uid
     * @return 返回账户信息
     */
    Result<?> getAccountInfo(Integer uid);
}
