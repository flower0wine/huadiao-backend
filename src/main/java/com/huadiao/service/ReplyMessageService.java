package com.huadiao.service;

import com.huadiao.entity.Result;

/**
 * @author flowerwine
 * @date 2023 年 10 月 09 日
 */
public interface ReplyMessageService extends Service {

    /**
     * 获取回复我的消息
     *
     * @param uid    用户 uid
     * @param userId 用户 id
     * @param offset 偏移量
     * @param row    行数
     * @return 回复我的评论
     */
    Result<?> getReplyMessage(Integer uid, String userId, Integer offset, Integer row);

    /**
     * 删除回复我的消息
     *
     * @param uid           用户 uid, 被回复
     * @param userId        用户 id
     * @param noteId        笔记 id
     * @param replyUid      回复者 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId  子评论 id
     * @return 返回删除提示
     */
    Result<?> deleteReplyMessage(Integer uid, String userId, Integer noteId, Integer replyUid, Integer rootCommentId, Integer subCommentId);

    /**
     * 获取未读消息数量
     * @param uid 用户 uid
     * @param userId 用户 id
     * @return 未读消息数量
     */
    Result<Integer> countUnreadMessage(Integer uid, String userId);
}
