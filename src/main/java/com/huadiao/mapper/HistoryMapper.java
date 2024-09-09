package com.huadiao.mapper;

import com.huadiao.entity.history.AnimeHistory;
import com.huadiao.entity.NoteHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 09 月 17 日
 */
public interface HistoryMapper {
    /**
     * 新增笔记访问记录
     * @param uid 访问者
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     */
    void insertNoteViewByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 获取笔记历史访问记录
     * @param uid 用户 uid
     * @param noteTitle 笔记标题
     * @param row 获取行数
     * @param offset 偏移量
     * @return 返回笔记访问记录
     */
    List<NoteHistory> selectNoteHistoryByUid(@Param("uid") Integer uid, @Param("noteTitle") String noteTitle, @Param("row") Integer row, @Param("offset") Integer offset);

    /**
     * 根据 用户 uid 查找所有的笔记 id
     * @param uid 用户 uid
     * @return 返回所有的笔记 id
     */
    List<Integer> selectAllNoteIdHistoryByUid(@Param("uid") Integer uid);

    /**
     * 获取番剧馆访问历史记录
     * @param uid 用户 uid
     * @param row 行数
     * @param offset 偏移量
     * @return 返回获取过程中的提示
     */
    List<AnimeHistory> selectAnimeHistoryByUid(@Param("uid") Integer uid, @Param("row") Integer row, @Param("offset") Integer offset);

    /**
     * 删除笔记访问历史记录
     * @param uid 用户 uid
     * @param authorUid 作者 uid
     * @param noteId 笔记 id
     */
    void deleteSpecificNoteHistory(@Param("uid") Integer uid, @Param("authorUid") Integer authorUid, @Param("noteId") Integer noteId);

    /**
     * 删除所有的笔记访问历史记录
     * @param uid 用户 uid
     */
    void deleteAllNoteHistory(@Param("uid") Integer uid);

    /**
     * 删除特定的番剧馆访问历史记录
     * @param uid 用户 uid
     * @param viewedUid 被浏览者 uid
     */
    void deleteSpecificAnimeHistory(@Param("uid") Integer uid, @Param("viewedUid") Integer viewedUid);

    /**
     * 删除所有的番剧馆访问历史记录
     * @param uid 用户 uid
     */
    void deleteAllAnimeHistory(@Param("uid") Integer uid);
}
