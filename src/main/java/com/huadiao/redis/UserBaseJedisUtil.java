package com.huadiao.redis;

/**
 * @author flowerwine
 * @date 2023 年 10 月 01 日
 */
public interface UserBaseJedisUtil {

    /**
     * 生成用户 uid, 从 1 开始
     * @return 返回唯一的 uid
     */
    int generateUid();
}
