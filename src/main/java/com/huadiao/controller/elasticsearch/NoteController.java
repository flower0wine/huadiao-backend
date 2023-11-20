package com.huadiao.controller.elasticsearch;

import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
public interface NoteController {

    /**
     * 根据 笔记标题 获取笔记
     *
     * @param session session 对象
     * @param noteTitle 要搜索的笔记标题
     * @param offset          偏移量
     * @param row             行数
     * @return 返回匹配的笔记
     */
    Result<?> findNoteByNoteTitle(HttpSession session, String noteTitle, Integer offset, Integer row);

    /**
     * 根据 笔记摘要 获取笔记
     *
     * @param session session 对象
     * @param noteSummary 要搜索的笔记摘要
     * @param offset            偏移量
     * @param row               行数
     * @return 返回匹配的笔记
     */
    Result<?> findNoteByNoteSummary(HttpSession session, String noteSummary, Integer offset, Integer row);
}
