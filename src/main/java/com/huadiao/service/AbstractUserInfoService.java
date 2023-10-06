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
     * 昵称不能为空或只包含空格
     */
    public static String WRONG_NULL_NICKNAME = "wrongNullNickname";

    /**
     * 昵称最长为 20
     */
    public static int NICKNAME_MAX_LENGTH = 20;

    /**
     * 昵称长度最大为 20 字符
     */
    public static String WRONG_LENGTH_NICKNAME = "wrongLengthNickname";

    /**
     * 个人简介长度最长为 50 字符
     */
    public static String WRONG_LENGTH_CANVASES = "wrongLengthCanvases";

    /**
     * 个人简介长度最长为 50 字符
     */
    public static int CANVASES_MAX_LENGTH = 50;

    /**
     * 错误的性别
     */
    public static String WRONG_SEX = "wrongSex";

    /**
     * 学校长度最大为 30 字符
     */
    public static String WRONG_LENGTH_SCHOOL = "wrongLengthSchool";

    /**
     * 学校最大长度为 30
     */
    public static int SCHOOL_MAX_LENGTH = 30;

    /**
     * 出生日期正则表达式
     */
    public static Pattern bornDateReg = Pattern.compile("^\\d{4}([-/])\\d{1,2}\\1\\d{1,2}$");


}
