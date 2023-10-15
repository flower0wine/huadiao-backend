package com.huadiao.mapper;

import com.huadiao.entity.message.like.LikeMessage;
import com.huadiao.entity.message.like.LikeMessageItem;
import com.huadiao.entity.message.like.LikeMessageUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
public interface LikeMessageMapper {

    /**
     * 获取点赞笔记的消息
     *
     * @param uid       用户 uid
     * @param likeLimit 获取时限制获取
     * @param offset    偏移量
     * @param row       行数
     * @return 返回点赞笔记消息
     */
    List<LikeMessage> selectLikeNoteMessageByUid(@Param("uid") Integer uid, @Param("limit") Integer likeLimit, @Param("offset") Integer offset,
                                                 @Param("row") Integer row);

    /**
     * 获取点赞评论的消息
     *
     * @param uid       用户 uid
     * @param likeLimit 获取时限制获取
     * @param offset    偏移量
     * @param row       行数
     * @return 返回点赞评论消息
     */
    List<LikeMessage> selectCommentLikeMessageByUid(@Param("uid") Integer uid, @Param("limit") Integer likeLimit, @Param("offset") Integer offset,
                                                    @Param("row") Integer row);

    /**
     * 获取点赞笔记评论的用户的必要信息
     *
     * @param uid           作者 uid
     * @param noteId        笔记 id
     * @param rootCommentId 根评论 id
     * @param subCommentId  子评论 id
     * @param likeLimit     限制获取
     * @return 必要信息
     */
    List<LikeMessageUser> selectLikeCommentUserByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("rootCommentId") Integer rootCommentId,
                                                     @Param("subCommentId") Integer subCommentId, @Param("limit") Integer likeLimit);

    /**
     * 获取点赞笔记的用户的必要信息, 在获取点赞消息时会调用, 最多获取 likeLimit 条数据
     *
     * @param uid       作者 uid
     * @param noteId    笔记 id
     * @param likeLimit 限制获取
     * @return 必要信息
     */
    List<LikeMessageUser> selectLikeNoteUserByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("limit") Integer likeLimit);

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
     * @param uid    作者 uid
     * @param noteId 笔记 id
     * @return 返回删除条数
     */
    Integer deleteLikeNoteMessage(@Param("uid") Integer uid, @Param("noteId") Integer noteId);

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

}
