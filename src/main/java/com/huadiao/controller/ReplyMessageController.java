package com.huadiao.controller;

import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 10 月 09 日
 */
public interface ReplyMessageController extends Controller {

    /**
     * 获取回复我的消息
     * @param session session 对象
     * @param offset 偏移量
     * @param row 行数
     * @return 回复我的评论
     */
    Result<?> getReplyMessage(HttpSession session, Integer offset, Integer row);

    /**
     * 删除回复我的消息
     *
     * @param session session 对象
     * @param noteId        笔记 id
     * @param uid      回复者 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId  子评论 id
     * @return 返回删除提示
     */
    Result<?> deleteReplyMessage(HttpSession session, Integer noteId, Integer uid, Integer rootCommentId, Integer subCommentId);

}
