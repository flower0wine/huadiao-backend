package com.huadiao.mapper;

import com.huadiao.entity.note.ForumNote;
import com.huadiao.entity.note.ForumRankNote;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 12 月 25 日
 */
public interface ForumNoteMapper {

    /**
     * 获取笔记排行前二十, 算法是 浏览量 + 点赞数 * 2 作为分数
     * @param noteRankMaxLength 排行榜最大条数
     * @return 返回查询到的笔记
     */
    List<ForumRankNote> selectForumRankNote(@Param("noteRankMaxLength") Integer noteRankMaxLength);

    /**
     * 根据指定的笔记 id 来获取对应的论坛笔记
     * @param noteIdList 笔记 id
     * @return 返回查询到的笔记
     */
    List<ForumNote> selectForumNoteByNoteId(@Param("noteIdList") List<Integer> noteIdList);

    /**
     * 根据 offset 和 limit 来获取笔记
     * @param offset 偏移量
     * @param row 条数
     * @param tag 标签
     * @return 返回查询到的笔记
     */
    List<ForumNote> selectForumNote(@Param("offset") Integer offset,
                                    @Param("row") Integer row,
                                    @Param("tag") Integer tag);
}
