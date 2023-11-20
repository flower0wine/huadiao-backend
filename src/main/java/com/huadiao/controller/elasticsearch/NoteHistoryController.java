package com.huadiao.controller.elasticsearch;

import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 11 月 13 日
 */
public interface NoteHistoryController {
    /**
     * 根据 笔记标题 搜索笔记历史
     * @param session session 对象
     * @param title 要搜索的笔记标题
     * @param offset 偏移量
     * @param row 行数
     * @return 返回匹配的笔记历史
     */
    Result<?> findNoteHistoryByNoteTitle(HttpSession session, String title, Integer offset, Integer row);

    /**
     * 删除用户的所有笔记访问记录
     *
     * @param session session 对象
     * @return 返回删除过程中的提示
     */
    Result<?> deleteAllByUid(HttpSession session);

    /**
     * 删除指定的用户笔记访问记录
     *
     * @param session session 对象
     * @param id 笔记 id
     * @return 返回删除过程中的提示
     */
    Result<?> deleteSpecificNoteHistory(HttpSession session, Integer id);
}
