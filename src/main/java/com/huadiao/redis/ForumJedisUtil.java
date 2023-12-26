package com.huadiao.redis;

import com.huadiao.entity.note.ForumRankNote;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 12 月 24 日
 */
public interface ForumJedisUtil {

    /**
     * 添加笔记 id
     * @param noteId 笔记 id
     */
    void addNoteId(Integer noteId);

    /**
     * 随机获取指定数量的笔记 id
     * @param count 数量
     * @return 返回随机的笔记 id
     */
    List<Integer> randomGetNoteId(int count);

    /**
     * 更新笔记排行榜, 每次调用都会删除之前的数据
     * @param noteRankMaxLength 排行榜上的总数
     */
    void updateForumRankNote(int noteRankMaxLength);

    /**
     * 获取指定范围的排行榜中的笔记
     * @param noteRankMaxLength 排行榜上的笔记总数
     * @return 返回对应的笔记
     */
    List<ForumRankNote> getRangeNoteRank(int noteRankMaxLength);
}
