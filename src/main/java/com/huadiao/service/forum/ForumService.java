package com.huadiao.service.forum;

import com.huadiao.entity.Result;
import com.huadiao.entity.req.RandomGetNote;
import com.huadiao.service.Service;

/**
 * @author flowerwine
 * @date 2023 年 12 月 24 日
 */
public interface ForumService extends Service {

    /**
     * 随机获取笔记
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param randomGetNote 随机获取笔记参数
     * @return 返回随机获取的笔记
     */
    Result<?> randomGetNote(Integer uid, String userId, RandomGetNote randomGetNote);

    /**
     * 获取笔记排行榜指定范围的笔记
     * @param uid 用户 uid
     * @param userId 用户 id
     * @return 返回排行榜上的笔记
     */
    Result<?> getNoteRank(Integer uid, String userId);
}
