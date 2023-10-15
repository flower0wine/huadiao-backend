package com.huadiao.service;

import com.huadiao.entity.Result;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
public interface LikeMessageService {

    /**
     * 获取点赞的消息
     *
     * @param uid    用户 uid
     * @param userId 用户 id
     * @param offset 偏移量
     * @param row    行数
     * @return 返回点赞的消息
     */
    Result<?> getLikeMessage(Integer uid, String userId, Integer offset, Integer row);

    /**
     * 删除点赞笔记消息, 作者删除消息为批量删除
     *
     * @param uid    作者 uid
     * @param userId 用户 id
     * @param noteId 笔记 id
     * @return 返回删除提示
     */
    Result<?> deleteLikeNoteMessage(Integer uid, String userId, Integer noteId);

    /**
     * 删除点赞评论笔记消息, 作者删除消息为批量删除
     *
     * @param uid           作者 uid
     * @param userId        用户 id
     * @param noteId        笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId  子评论 id
     * @return 返回删除提示
     */
    Result<?> deleteLikeNoteMessage(Integer uid, String userId, Integer noteId, Integer rootCommentId, Integer subCommentId);

    /**
     * 获取点赞笔记的用户
     *
     * @param uid    作者 uid
     * @param userId 用户 id
     * @param noteId 笔记 id
     * @param offset 偏移量
     * @param row    行数
     * @return 返回点赞笔记的用户
     */
    Result<?> getLikeNoteUser(Integer uid, String userId, Integer noteId, Integer offset, Integer row);

    /**
     * 获取点赞评论笔记的用户
     *
     * @param uid           作者 uid
     * @param userId        用户 id
     * @param noteId        笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId  子评论 id
     * @param offset        偏移量
     * @param row           行数
     * @return 返回点赞笔记评论的用户
     */
    Result<?> getLikeCommentUser(Integer uid, String userId, Integer noteId, Integer rootCommentId, Integer subCommentId, Integer offset, Integer row);
}
