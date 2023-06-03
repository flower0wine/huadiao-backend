package com.huadiao.service;

/**
 * 收藏接口
 * @author flowerwine
 */
public interface NoteStarService {
    /**
     * 新增笔记收藏
     * @param uid 收藏者 uid
     * @param userId 用户 id
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     */
    String addNewNoteStar(Integer uid, String userId, Integer noteId, Integer authorUid);

    /**
     * 删除笔记收藏
     * @param uid 收藏者 uid
     * @param userId 用户 id
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     */
    String deleteNoteStar(Integer uid, String userId, Integer noteId, Integer authorUid);
}
