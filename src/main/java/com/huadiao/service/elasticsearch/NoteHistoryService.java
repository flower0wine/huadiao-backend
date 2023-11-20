package com.huadiao.service.elasticsearch;

import com.huadiao.entity.Result;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
public interface NoteHistoryService {

    /**
     * 根据 笔记标题 搜索笔记历史
     *
     * @param uid             用户 uid
     * @param userId          用户 id
     * @param searchNoteTitle 要搜索的笔记标题
     * @param offset          偏移量
     * @param row             行数
     * @return 返回匹配的笔记历史
     */
    Result<?> findNoteHistoryByNoteTitle(Integer uid, String userId, String searchNoteTitle, Integer offset, Integer row);

    /**
     * 删除用户的所有笔记访问记录
     *
     * @param uid    用户 uid
     * @param userId 用户 id
     * @return 返回删除过程中的提示
     */
    Result<?> deleteAllByUid(Integer uid, String userId);

    /**
     * 删除指定的用户笔记访问记录
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param noteId 笔记 id
     * @return 返回删除过程中的提示
     */
    Result<?> deleteSpecificNoteHistory(Integer uid, String userId, Integer noteId);
}
