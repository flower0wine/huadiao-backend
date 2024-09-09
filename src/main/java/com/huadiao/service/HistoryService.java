package com.huadiao.service;

import com.huadiao.entity.Result;

/**
 * @author flowerwine
 * @date 2023 年 09 月 17 日
 */
public interface HistoryService {

    /**
     * 获取笔记历史记录
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param noteTitle 笔记标题
     * @param row 行数
     * @param offset 偏移量
     * @return 返回获取过程中的提示
     */
    Result<?> getNoteHistory(Integer uid, String userId, String noteTitle, Integer row, Integer offset);

    /**
     * 获取番剧访问历史记录
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param row 行数
     * @param offset 偏移量
     * @return 返回获取过程中的提示
     */
    Result<?> getAnimeHistory(Integer uid, String userId, Integer row, Integer offset);

    /**
     * 删除特定的笔记历史记录
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param authorUid 被访问者
     * @param noteId 笔记 id
     * @return 返回删除过程的提示
     */
    Result<?> deleteNoteHistory(Integer uid, String userId, Integer authorUid, Integer noteId);

    /**
     * 删除特定的番剧馆访问历史记录
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param viewedUid 被访问者 uid
     * @return 返回删除过程中的提示
     */
    Result<?> deleteAnimeHistory(Integer uid, String userId, Integer viewedUid);

}
