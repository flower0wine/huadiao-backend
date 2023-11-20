package com.huadiao.controller.elasticsearch;

import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
public interface HistoryController {

    /**
     * 根据 笔记标题 搜索笔记历史
     *
     * @param session         session 对象
     * @param searchNoteTitle 要搜索的笔记标题
     * @param offset          偏移量
     * @param row             行数
     * @return 返回匹配的笔记历史
     */
    Result<?> findNoteHistoryByNoteTitle(HttpSession session, String searchNoteTitle, Integer offset, Integer row);
}
