package com.huadiao.redis.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.huadiao.entity.AccountSettings;
import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.UserSettingJedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Slf4j
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
            jedis.close();
            return JSONUtil.toBean(settings, AccountSettings.class);
        } else {
            AccountSettings accountSettings = userSettingsMapper.selectAccountSettingsByUid(uid);
            jedis.set(jedisKey, JSONUtil.toJsonStr(accountSettings));
            jedis.close();
            return accountSettings;
        }
    }

    @Override
    public void setAccountSettings(Integer uid, String[] settingsArr, Set<String> settingsSet) throws Exception {
        Jedis jedis = jedisPool.getResource();
        String jedisKey = StrUtil.format(REDIS_KEY_USER_SETTINGS, uid);
        String settings = jedis.get(jedisKey);
        AccountSettings accountSettings;
        if(settings != null) {
            accountSettings = JSONUtil.toBean(settings, AccountSettings.class);
            try {
                reflectSetField(accountSettings, settingsSet);
            } catch (Exception e) {
                log.info("修改用户设置时出现字段处理异常");
                throw new Exception(e);
            }
            // redis 有数据, 先删除 redis, 再更新数据库, 再写入 redis
            jedis.del(jedisKey);
            userSettingsMapper.insertOrUpdateUserSettingsByUid(uid, settingsArr);
        } else {
            // redis 无数据, 先更新数据库, 再写入数据库
            userSettingsMapper.insertOrUpdateUserSettingsByUid(uid, settingsArr);
            accountSettings = userSettingsMapper.selectAccountSettingsByUid(uid);
        }
        jedis.set(jedisKey, JSONUtil.toJsonStr(accountSettings));
        jedis.close();
    }

    private void reflectSetField(AccountSettings accountSettings, Set<String> stringSet) throws NoSuchFieldException, IllegalAccessException {
        Class<? extends AccountSettings> accountSettingsClass = accountSettings.getClass();
        for (String filedName : stringSet) {
            Field field = accountSettingsClass.getDeclaredField(filedName);
            field.setAccessible(true);
            Boolean bool = (Boolean) field.get(accountSettings);
            field.set(accountSettings, !bool);
        }
    }
}
