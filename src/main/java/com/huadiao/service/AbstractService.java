package com.huadiao.service;

import com.huadiao.redis.IDGeneratorJedisUtil;
import com.huadiao.redis.StarJedisUtil;
import com.huadiao.redis.UserInfoJedisUtil;
import com.huadiao.redis.UserSettingJedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public abstract class AbstractService implements Service {
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected UserInfoJedisUtil userInfoJedisUtil;

    @Autowired
    protected UserSettingJedisUtil userSettingJedisUtil;

    @Autowired
    protected StarJedisUtil starJedisUtil;

    @Autowired
    protected IDGeneratorJedisUtil idGeneratorJedisUtil;

    /**
     * 用户设置私密 map 的键名, map 将返回
     */
    public static String PRIVATE_SETTINGS_KEY = "private";

    /**
     * 用户隐私信息, 用户已设置为不可公开, 私密 map 的键值
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
     * 无效的参数, 一般是不能确定原因
     */
    public static String INVALID_PARAM = "invalidParam";


}
