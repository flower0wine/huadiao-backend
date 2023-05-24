package com.huadiao.service;

import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.entity.dto.userdto.UserShareDto;
import com.huadiao.mapper.FollowFanMapper;
import com.huadiao.mapper.UserMapper;

import java.util.List;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public abstract class AbstractService implements Service {


    /**
     * 用户设置私密 map 的键名, map 将返回
     */
    public static String PRIVATE_SETTINGS_KEY = "private";

    /**
     * 用户隐私信息, 用户已设置为不可公开
     */
    public static String PRIVATE_USER_INFO = "privateUserInfo";

    /**
     * 错误提示信息键名
     */
    public static String WRONG_MESSAGE_KEY = "wrongMessage";


    /**
     * 错误的 uid, 该 uid 不存在
     */
    public static String NO_EXIST_UID = "noExistUid";

    /**
     * 提供了 null 的 uid
     */
    public static String NULL_UID = "nullUid";

    /**
     * 获取用户基本完整的用户信息
     * @param userMapper 用户映射
     * @param followFanMapper 关注映射
     * @param uid 用户 uid
     * @return 返回基本完整的用户信息
     */
    protected UserAbstractDto getUserInfoByUid(UserMapper userMapper, FollowFanMapper followFanMapper, Integer uid) {
        // 获取并填充用户信息
        UserShareDto userShareDto = userMapper.selectUserShareDtoByUid(uid);
        // 用户关注与粉丝
        List<Integer> followFans = followFanMapper.countFollowAndFansByUid(uid);
        return UserAbstractDto.loadUserAbstractInfo(userShareDto, followFans);
    }
}
