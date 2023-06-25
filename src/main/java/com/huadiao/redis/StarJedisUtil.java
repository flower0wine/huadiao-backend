package com.huadiao.redis;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public interface StarJedisUtil {
    /**
     * 获取用户收藏数量
     * @param uid 用户 uid
     * @return 收藏数量
     */
    Integer getUserStarAmount(Integer uid);
}
