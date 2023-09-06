package com.huadiao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 映射文件, 与用户笔记收藏相关
 * @author flowewine
 */
public interface NoteOperateMapper {
    /**
     * 删除笔记评论, 如果是父评论将会删除其下的所有子评论
     * @param deleteUid 删除者 uid
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id, 如果为父评论时传入 0L 即可
     */
    void deleteNoteCommentByUid(@Param("deleteUid") Integer deleteUid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid,
                                @Param("rootCommentId") Long rootCommentId, @Param("subCommentId") Long subCommentId);

    /**
     * 新增笔记收藏或者再次收藏笔记
     * @param uid 用户 uid
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     */
    void insertNoteStarByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 删除笔记收藏
     * @param uid 用户 uid
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     */
    void deleteNoteStarByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 获取用户笔记收藏数量
     * @param uid 用户 uid
     * @return 返回笔记收藏数量
     */
    Integer countNoteStarByUid(@Param("uid") Integer uid);

    /**
     * 新增笔记不喜欢
     * @param uid 不喜欢的用户的 uid
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     */
    void insertNoteUnlikeByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 删除笔记不喜欢
     * @param uid 不喜欢的用户的 uid
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     */
    void deleteNoteUnlikeByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 新增笔记访问记录
     * @param uid 访问者
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     */
    void insertNoteViewByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 删除笔记访问记录
     * @param uid 访问者 uid
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     */
    void deleteNoteViewByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 新增笔记点赞
     * @param uid 用户 uid
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     */
    void insertNoteLikeByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 删除笔记点赞
     * @param uid 点赞者 uid
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     */
    void deleteNoteLikeByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 获取笔记浏览量
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @return 返回笔记浏览量
     */
    Integer countNoteViewByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId);

    /**
     * 查询是否有某个笔记评论
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     * @return 存在返回 数字, 不存在返回 null
     */
    Integer selectNoteCommentExist(@Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid,
                                   @Param("rootCommentId") Long rootCommentId, @Param("subCommentId") Long subCommentId);

    /**
     * 新增笔记评论喜欢
     * @param uid 点击喜欢的用户
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     */
    void insertNoteCommentLike(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid,
                           @Param("rootCommentId") Long rootCommentId, @Param("subCommentId") Long subCommentId);

    /**
     * 删除笔记评论喜欢
     * @param uid 点击喜欢的用户
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     */
    void deleteNoteCommentLike(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid,
                               @Param("rootCommentId") Long rootCommentId, @Param("subCommentId") Long subCommentId);

    /**
     * 新增笔记评论不喜欢
     * @param uid 点击不喜欢的用户
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     */
    void insertNoteCommentUnlike(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid,
                               @Param("rootCommentId") Long rootCommentId, @Param("subCommentId") Long subCommentId);

    /**
     * 删除笔记评论不喜欢
     * @param uid 点击喜不欢的用户
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     */
    void deleteNoteCommentUnlike(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid,
                               @Param("rootCommentId") Long rootCommentId, @Param("subCommentId") Long subCommentId);

    /**
     * 举报评论, 自己的评论不能举报
     * @param uid 举报者 uid
     * @param reportedUid 被举报者 uid
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @param rootCommentId 根评论 id
     * @param subCommentId 子评论 id
     */
    void insertNoteCommentReport(@Param("uid") Integer uid, @Param("reportedUid") Integer reportedUid, @Param("noteId") Integer noteId,
                                 @Param("authorUid") Integer authorUid, @Param("rootCommentId") Long rootCommentId,
                                 @Param("subCommentId") Long subCommentId);
}
