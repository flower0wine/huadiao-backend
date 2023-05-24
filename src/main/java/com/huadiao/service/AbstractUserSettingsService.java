package com.huadiao.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.huadiao.entity.AccountSettings;
import com.huadiao.mapper.UserSettingsMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.regex.Pattern;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户设置抽象类
 */
public abstract class AbstractUserSettingsService extends AbstractService implements UserSettingsService {

    /**
     * 用户账号设置更新成功
     */
    public static String ACCOUNT_SETTING_UPDATE_SUCCEED = "accountSettingUpdateSucceed";

    /**
     * 用户设置更新失败
     */
    public static String ACCOUNT_SETTING_UPDATE_FAIL = "accountSettingUpdateFail";

    /**
     * 匹配设置字段, 要求全部为英文字母
     */
    public static Pattern pattern = Pattern.compile("^\\w+$");

    /**
     * redis 存储的键名, 格式为 huadiao.userSettings.uid, uid 为数字,
     * 这里使用占位符 {} 取代
     */
    public static String REDIS_KEY_USER_SETTINGS = "huadiao.userSettings.{}";


    /**
     * 通过 uid 获取用户账号设置, 首先查询 redis 中是否存在该用户的设置, 没有再查询数据库, 并将查询结果保存至 redis
     *
     * @param uid                用户 uid
     * @param jedisPool          redis 连接池
     * @param userSettingsMapper 查询用户设置的 mapper 映射接口
     * @return 返回用户设置
     */
    public static AccountSettings getAccountSettings(Integer uid, JedisPool jedisPool, UserSettingsMapper userSettingsMapper) {
        Jedis jedis = jedisPool.getResource();
        String jedisKey = StrUtil.format(REDIS_KEY_USER_SETTINGS, uid);
        String settings = jedis.get(jedisKey);
        if (settings != null) {
            jedisPool.returnResource(jedis);
            return JSONUtil.toBean(settings, AccountSettings.class);
        } else {
            AccountSettings accountSettings = userSettingsMapper.selectAccountSettingsByUid(uid);
            jedis.set(jedisKey, JSONUtil.toJsonStr(accountSettings));
            jedisPool.returnResource(jedis);
            return accountSettings;
        }
    }

    /**
     * 根据 uid 设置用户设置到 redis 中, 如果数据为 null 或者用户设置在 redis 中能找到则跳过
     *
     * @param uid             用户 uid
     * @param jedisPool       redis 连接池
     * @param accountSettings 用户设置字符串
     */
    public static void setAccountSettings(Integer uid, JedisPool jedisPool, String accountSettings) {
        Jedis jedis = jedisPool.getResource();
        String jedisKey = StrUtil.format(REDIS_KEY_USER_SETTINGS, uid);
        if(accountSettings != null && jedis.get(jedisKey) == null) {
            jedis.set(jedisKey, accountSettings);
        }
        jedisPool.returnResource(jedis);
    }

    /**
     * 根据指定的 uid 生成 jedisKey
     * @param uid 用户 uid
     * @return 返回格式形似 huadiao.userSettings.uid
     */
    public static AccountSettings getJedisKey(Jedis jedis, Integer uid) {
        String jedisKey = StrUtil.format(REDIS_KEY_USER_SETTINGS, uid);
        return JSONUtil.toBean(jedis.get(jedisKey), AccountSettings.class);
    }
}
