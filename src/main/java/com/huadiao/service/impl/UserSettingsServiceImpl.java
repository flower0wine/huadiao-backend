package com.huadiao.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.huadiao.entity.AccountSettings;
import com.huadiao.entity.dto.accountsettings.MessageSettingsDto;
import com.huadiao.entity.dto.accountsettings.PublicInfoSettingsDto;
import com.huadiao.mapper.UserSettingsMapper;
import com.huadiao.service.AbstractUserSettingsService;
import com.huadiao.service.UserSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Service
@Slf4j
public class UserSettingsServiceImpl extends AbstractUserSettingsService {

    private UserSettingsMapper userSettingsMapper;
    private JedisPool jedisPool;

    @Autowired
    public UserSettingsServiceImpl(UserSettingsMapper userSettingsMapper, JedisPool jedisPool) {
        this.userSettingsMapper = userSettingsMapper;
        this.jedisPool = jedisPool;
    }

    @Override
    public AccountSettings getUserSettings(Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取自己的账号设置", uid, userId);
        AccountSettings accountSettings = userSettingsMapper.selectAccountSettingsByUid(uid);

        // 用户设置存储到 redis 中
        if (accountSettings != null) {
            Jedis jedis = jedisPool.getResource();
            // 格式化字符串
            String jedisKey = StrUtil.format(REDIS_KEY_USER_SETTINGS, uid);
            jedis.set(jedisKey, JSONUtil.toJsonStr(accountSettings));
            jedisPool.returnResource(jedis);
            log.debug("已将 uid, userId 分别为 {}, {} 的用户刚查询的用户设置存储到 redis 中, 为 settings: {}", uid, userId, accountSettings);
        }

        log.debug("uid, userId 分别为 {}, {} 的用户获取账号设置成功", uid, userId);
        return accountSettings;
    }

    @Override
    public String modifyAccountSettings(Integer uid, String userId, Set<String> settingsSet) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试更新自己的账号设置, 传递的参数为 settingsList: {}", uid, userId, settingsSet);
        String[] settingsArr = settingsSet.toArray(new String[0]);
        String settingReplaceRegex = "([a-z])([A-Z])";
        String replace = "$1_$2";
        // 格式转换, 例如将 publicStarStatus --> public_star_status
        for (int index = 0; index < settingsArr.length; index++) {
            String str = settingsArr[index];
            // 如果不是全英文返回更新失败
            if (!pattern.matcher(str).find()) {
                log.warn("uid, userId 分别为 {}, {} 的用户传入的参数存在问题, settingsArr[{}]: {}", uid, userId, index, settingsArr[index]);
                return ACCOUNT_SETTING_UPDATE_FAIL;
            }
            settingsArr[index] = str.replaceAll(settingReplaceRegex, replace).toLowerCase();
            log.debug("将用户传递的参数格式进行转换, {} -> {}", str, settingsArr[index]);
        }
        userSettingsMapper.insertOrUpdateUserSettingsByUid(uid, settingsArr);

        log.debug("更新后设置已经写入到数据库, 下面尝试将其写入到 redis 中");
        // 修改 redis 存储的用户设置
        Jedis jedis = jedisPool.getResource();
        String jedisKey = StrUtil.format(REDIS_KEY_USER_SETTINGS, uid);
        String settings = jedis.get(jedisKey);
        if(settings != null) {
            log.debug("redis 中已存在用户设置, 对其进行更新");
            // 根据提供的参数将 redis 中的设置更新
            JSONObject jsonObject = JSONUtil.parseObj(settings);
            for (String setting : settingsArr) {
                Boolean settingStatus = jsonObject.getBool(setting);
                jsonObject.set(setting, !settingStatus);
            }
            jedis.set(jedisKey, JSONUtil.toJsonStr(jsonObject));
            log.debug("redis 数据更新成功");
        } else {
            log.debug("redis 中不存在用户设置, 将从数据库中获取存入 redis 中");
            AccountSettings accountSettings = userSettingsMapper.selectAccountSettingsByUid(uid);
            jedis.set(jedisKey, JSONUtil.toJsonStr(accountSettings));
            log.debug("从数据库获取用户设置并存入 redis 成功");
        }
        log.debug("更新后设置已经写入 redis 中");
        jedisPool.returnResource(jedis);
        log.debug("uid, userId 分别为 {}, {} 的用户更新账号设置成功, settingsList: {}", uid, userId, settingsSet);
        return ACCOUNT_SETTING_UPDATE_SUCCEED;
    }


}
