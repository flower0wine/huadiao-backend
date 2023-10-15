package com.huadiao.controller;

import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
public interface LikeMessageController extends Controller {

    /**
     * 获取点赞的消息
     *
     * @param session session 对象
     * @param offset  偏移量
     * @param row     行数
     * @return 返回点赞消息
     */
    Result<?> getLikeMessage(HttpSession session, Integer offset, Integer row);

    /**
     * 删除点赞笔记消息
     *
     * @param session session 对象
     * @param noteId  笔记 id
     * @return 返回删除提示
     */
    Result<?> deleteLikeNoteMessage(HttpSession session, Integer noteId);

    /**
     * 删除点赞评论笔记消息
     *
     * @param session       session 对象
     * @param noteId        笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId  子评论 id
     * @return 返回删除提示
     */
    Result<?> deleteLikeNoteMessage(HttpSession session, Integer noteId, Integer rootCommentId, Integer subCommentId);

    /**
     * 获取点赞笔记的用户
     *
     * @param session session 对象
     * @param noteId 笔记 id
     * @param offset 偏移量
     * @param row    行数
     * @return 返回点赞笔记的用户
     */
    Result<?> getLikeNoteUser(HttpSession session, Integer noteId, Integer offset, Integer row);

    /**
     * 获取点赞评论笔记的用户
     *
     * @param session session 对象
     * @param noteId        笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId  子评论 id
     * @param offset        偏移量
     * @param row           行数
     * @return 返回点赞笔记评论的用户
     */
    Result<?> getLikeCommentUser(HttpSession session, Integer noteId, Integer rootCommentId, Integer subCommentId, Integer offset, Integer row);
}
