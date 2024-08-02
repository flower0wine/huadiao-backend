package com.huadiao.mapper;

import com.huadiao.entity.message.reply.ReplyComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 10 月 09 日
 */
public interface ReplyMessageMapper {

    /**
     * 新增已读的最新回复我的消息
     * @param replyComment 回复
     * @return 新增条数
     */
    Integer insertLatestReplyMessage(ReplyComment replyComment);

    /**
     * 获取回复我的评论消息
     * @param uid 用户 uid
     * @param offset 偏移量
     * @param row 行数
     * @return 返回回复评论消息
     */
    List<ReplyComment> selectReplyCommentMessage(@Param("uid") Integer uid, @Param("offset") Integer offset, @Param("row") Integer row);

    /**
     * 删除回复我的笔记评论消息
     * @param uid 用户 uid
     * @param noteId 用户 id
     * @param repliedUid 被回复的 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId  子评论 id
     * @return 删除条数
     */
    Integer deleteReplyNoteMessage(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("repliedUid") Integer repliedUid,
                                   @Param("rootCommentId") Integer rootCommentId, @Param("subCommentId") Integer subCommentId);

    /**
     * 删除已读的最近的回复我的消息
     * @param uid 用户 uid
     * @return 删除条数
     */
    Integer deleteLatestReplyMessage(@Param("uid") Integer uid);

    /**
     * 获取未读消息数量
     * @param uid 用户 uid
     * @return 未读消息数量
     */
    Integer countUnreadMessage(@Param("uid") Integer uid);
}
