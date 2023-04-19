package com.huadiao.service;

import com.huadiao.entity.dto.userinfodto.UserInfoDto;

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
    String insertOrUpdateUserInfo(Integer uid, String userId, String nickname,
                                  String canvases, String sex,
                                  String bornDate, String school) throws Exception;

    /**
     * 获取用户信息
     * @param uid 用户 uid
     * @param userId 用户 id
     * @return 返回用户详细信息
     */
    UserInfoDto getUserInfo(Integer uid, String userId);

}
