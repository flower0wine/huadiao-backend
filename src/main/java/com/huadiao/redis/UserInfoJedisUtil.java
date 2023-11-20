package com.huadiao.redis;

import com.huadiao.entity.dto.userdto.UserAbstractDto;

import java.util.Date;

public interface UserInfoJedisUtil {

    /**
     * 修改用户信息
     * @param uid 用户 uid
     * @param nickname 昵称
     * @param canvases 个人简介
     * @param sex 性别
     * @param bornDate 出生日期
     * @param school 学校
     */
    void modifyUserInfoByUid(Integer uid, String nickname, String canvases, String sex, Date bornDate, String school);

    /**
     * 修改头像
     * @param uid 用户 uid
     * @param userAvatar 头像文件名称
     */
    void modifyUserAvatarByUid(Integer uid, String userAvatar);

    /**
     * 获取用户基本完整的用户信息, 使用了 redis 缓存用户信息
     * @param uid 用户 uid
     * @return 返回基本完整的用户信息
     */
    UserAbstractDto getUserAbstractDto(Integer uid);
}
