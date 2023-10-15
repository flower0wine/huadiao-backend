package com.huadiao.service;

import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.entity.dto.userdto.UserShareDto;
import com.huadiao.mapper.FollowFanMapper;
import com.huadiao.mapper.UserMapper;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public abstract class AbstractUserInfoService extends AbstractService implements UserInfoService {

    /**
     * 昵称最长为 20
     */
    public static int NICKNAME_MAX_LENGTH = 20;

    /**
     * 个人简介长度最长为 50 字符
     */
    public static int CANVASES_MAX_LENGTH = 50;

    /**
     * 学校最大长度为 30
     */
    public static int SCHOOL_MAX_LENGTH = 30;

}
