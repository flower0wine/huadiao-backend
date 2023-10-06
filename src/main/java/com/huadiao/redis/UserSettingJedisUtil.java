package com.huadiao.redis;

import com.huadiao.entity.account.AccountSettings;

import java.util.Set;

import java.util.Set;

/**
 * redis 用户设置接口类
 * @author flowerwine
 */
public interface UserSettingJedisUtil {
    /**
     * 通过 uid 获取用户账号设置, 首先查询 redis 中是否存在该用户的设置, 没有再查询数据库, 并将查询结果保存至 redis
     *
     * @param uid                用户 uid
     * @return 返回用户设置
     */
    AccountSettings getAccountSettings(Integer uid);

    /**
     * 设置用户设置
     * @param uid 用户 uid
     * @param settingsArr 用户设置, public_xxx 格式
     * @param settingsSet 用户设置, publicXxx 格式
     * @throws Exception 可能会抛出异常
     */
    void setAccountSettings(Integer uid, String[] settingsArr, Set<String> settingsSet) throws Exception;
}
