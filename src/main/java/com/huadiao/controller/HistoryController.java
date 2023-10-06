package com.huadiao.controller;

import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 09 月 18 日
 */
public interface HistoryController {

    /**
     * 获取笔记历史记录
     * @param session session 对象
     * @param row 行数
     * @param offset 偏移量
     * @return 返回获取过程中的提示
     */
    Result<?> getNoteHistory(HttpSession session, Integer row, Integer offset);

    /**
     * 获取番剧访问历史记录
     * @param session session 对象
     * @param row 行数
     * @param offset 偏移量
     * @return 返回获取过程中的提示
     */
    Result<?> getAnimeHistory(HttpSession session, Integer row, Integer offset);

    /**
     * 删除笔记历史记录
     * @param session session 对象
     * @param authorUid 被访问者
     * @param noteId 笔记 id
     * @return 返回删除过程的提示
     */
    Result<?> deleteNoteHistory(HttpSession session, Integer authorUid, Integer noteId);

    /**
     * 删除特定的番剧馆访问历史记录
     * @param session session 对象
     * @param viewedUid 被访问者 uid
     * @return 返回删除过程中的提示
     */
    Result<?> deleteAnimeHistory(HttpSession session, Integer viewedUid);
}
