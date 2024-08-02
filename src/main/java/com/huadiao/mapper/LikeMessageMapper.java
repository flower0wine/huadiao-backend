package com.huadiao.mapper;

import com.huadiao.dto.note.NoteCommentDTO;
import com.huadiao.entity.message.like.LikeMessage;
import com.huadiao.entity.message.like.LikeMessageUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
public interface LikeMessageMapper {

    /**
     * 新增或更新点赞最新消息
     * @param noteCommentDTO 笔记评论点赞消息
     * @return 是否成功
     */
    Integer insertLatestMessage(NoteCommentDTO noteCommentDTO);

    /**
     * 获取笔记点赞消息 (包括笔记点赞和笔记评论点赞消息)
     * @param uid 作者 uid
     * @param offset 偏移量
     * @param row    行数
     * @return 笔记点赞消息
     */
    List<LikeMessage> selectNoteLikeMessageByUid(@Param("uid") Integer uid,
                                             @Param("offset") Integer offset,
                                             @Param("row") Integer row);


    /**
     * 获取点赞笔记的用户信息
     *
     * @param uid    作者 uid
     * @param noteId 笔记 id
     * @param offset 偏移量
     * @param row    行数
     * @return 用户信息
     */
    List<LikeMessageUser> selectLikeNoteUser(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("offset") Integer offset,
                                             @Param("row") Integer row);

    /**
     * 获取点赞笔记评论的用户信息
     *
     * @param uid           作者 uid
     * @param noteId        笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId  子评论 id
     * @param offset        偏移量
     * @param row           行数
     * @return 用户信息
     */
    List<LikeMessageUser> selectLikeCommentUser(@Param("uid") Integer uid, @Param("noteId") Integer noteId,
                                             @Param("rootCommentId") Integer rootCommentId, @Param("subCommentId") Integer subCommentId,
                                             @Param("offset") Integer offset, @Param("row") Integer row);

    /**
     * 删除点赞笔记的消息
     *
     * @param noteCommentDTO 笔记评论 DTO
     * @return 返回删除条数
     */
    Integer deleteLikeNoteMessage(NoteCommentDTO noteCommentDTO);

    /**
     * 删除点赞笔记评论的消息
     *
     * @param uid           作者 uid
     * @param noteId        笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId  子评论 id
     * @return 返回删除条数
     */
    Integer deleteLikeCommentMessage(@Param("uid") Integer uid, @Param("noteId") Integer noteId,
                                     @Param("rootCommentId") Integer rootCommentId, @Param("subCommentId") Integer subCommentId);

    /**
     * 删除最近的已读消息
     * @param uid 用户 uid
     * @return 返回删除条数
     */
    Integer deleteLatestReadMessage(@Param("uid") Integer uid);

    /**
     * 获取未读消息数
     * @param uid 作者 uid
     * @return 未读消息数
     */
    Integer countUnreadMessage(@Param("uid") Integer uid);

}
