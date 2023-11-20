package com.huadiao.service.elasticsearch;

import com.huadiao.entity.Result;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
public interface NoteService {

    /**
     * 根据 笔记标题 获取笔记
     *
     * @param uid             用户 uid
     * @param userId          用户 id
     * @param searchNoteTitle 要搜索的笔记标题
     * @param offset          偏移量
     * @param row             行数
     * @return 返回匹配的笔记
     */
    Result<?> findNoteByNoteTitle(Integer uid, String userId, String searchNoteTitle, Integer offset, Integer row);

    /**
     * 根据 笔记摘要 获取笔记
     *
     * @param uid               用户 uid
     * @param userId            用户 id
     * @param searchNoteSummary 要搜索的笔记摘要
     * @param offset            偏移量
     * @param row               行数
     * @return 返回匹配的笔记
     */
    Result<?> findNoteByNoteSummary(Integer uid, String userId, String searchNoteSummary, Integer offset, Integer row);
}
