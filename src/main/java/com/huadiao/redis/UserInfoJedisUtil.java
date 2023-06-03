package com.huadiao.redis;

import com.huadiao.entity.dto.userdto.UserAbstractDto;

public interface UserInfoJedisUtil {

    /**
     * 修改用户信息
     * @param uid 用户 uid
     */
    void modifyUserInfoByUid(Integer uid);

    /**
     * 获取用户基本完整的用户信息, 使用了 redis 缓存用户信息
     * @param uid 用户 uid
     * @return 返回基本完整的用户信息
     */
    UserAbstractDto getUserInfoByUid(Integer uid);
}
