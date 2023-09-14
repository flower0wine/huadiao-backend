package com.huadiao.redis;

import com.huadiao.entity.CardBorderImage;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023年09月12日
 */
public interface HuadiaoHouseJedisUtil {
    /**
     * 生成唯一递增番剧 id, 与 getAnimeId 不同的是它既会获取, 也会增加 id
     * @return 番剧 id
     */
    long generateAnimeId();

    /**
     * 获取当前的番剧 id, 与 generateAnimeId 不同的是它不会增加 id, 只是获取
     * @return 番剧 id
     */
    long getAnimeId();

    /**
     * 获取封面边框
     * @return 返回封面边框
     */
    List<CardBorderImage> getHuadiaoCardBorderImage();
}
