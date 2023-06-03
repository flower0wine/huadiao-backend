package com.huadiao.redis.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.huadiao.entity.AccountSettings;
import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.UserSettingJedisUtil;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Component
public class UserSettingJedis extends AbstractJedis implements UserSettingJedisUtil {
    /**
     * redis 存储的键名, 格式为 huadiao.userSettings.uid, uid 为数字,
     * 这里使用占位符 {} 取代
     */
    public static String REDIS_KEY_USER_SETTINGS = "huadiao.userSettings.{}";


    @Override
    public AccountSettings getAccountSettings(Integer uid) {
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

    @Override
    public void setAccountSettings(Integer uid, AccountSettings accountSettings) {
        Jedis jedis = jedisPool.getResource();
        // 格式化字符串
        String jedisKey = StrUtil.format(REDIS_KEY_USER_SETTINGS, uid);
        jedis.set(jedisKey, JSONUtil.toJsonStr(accountSettings));
        jedisPool.returnResource(jedis);
    }
}
