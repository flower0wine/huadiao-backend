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
    int getUserStarAmount(Integer uid);

    /**
     * 生成笔记收藏分组唯一 id
     * @return 返回分组 id
     */
    int generateNoteStarGroupId();

    /**
     * 生成番剧收藏分组唯一 id
     * @return 返回分组 id
     */
    int generateAnimeStarGroupId();
}
