package com.huadiao.mapper;

import com.huadiao.entity.dao.EmoteDao;

import java.util.List;

/**
 * @author flowerwine
 * @date 2024 年 07 月 20 日
 */
public interface EmoteMapper {

    /**
     * 插入表情
     * @param emoteDao 表情对象
     */
    void insertEmotes(EmoteDao emoteDao);

    /**
     * 查询表情
     * @return 表情列表
     */
    List<EmoteDao> selectEmote();
}
