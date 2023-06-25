package com.huadiao.service;

import com.huadiao.entity.Result;

/**
 * 收藏接口
 * @author flowerwine
 */
public interface NoteOperateService {
    /**
     * 新增笔记收藏
     * @param uid 收藏者 uid
     * @param userId 用户 id
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @return 返回新增过程中错误或者成功提示
     */
    String addNewNoteStar(Integer uid, String userId, Integer noteId, Integer authorUid);

    /**
     * 删除笔记收藏
     * @param uid 收藏者 uid
     * @param userId 用户 id
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @return 删除删除成功提示
     */
    String deleteNoteStar(Integer uid, String userId, Integer noteId, Integer authorUid);

    /**
     * 新增笔记不喜欢
     * @param uid 不喜欢的用户 uid
     * @param userId 用户 id
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @return 返回新增不喜欢过程错误或者成功提示
     */
    String addNoteUnlike(Integer uid, String userId, Integer noteId, Integer authorUid);

    /**
     * 删除笔记不喜欢
     * @param uid 不喜欢的用户的 uid
     * @param userId 用户 id
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @return 返回删除不喜欢成功提示
     */
    String deleteNoteUnlike(Integer uid, String userId, Integer noteId, Integer authorUid);

    /**
     * 新增笔记点赞
     * @param uid 点赞用户 uid
     * @param userId 用户 id
     * @param authorUid 作者 uid
     * @param noteId 笔记 id
     * @return 返回新增笔记过程中的错误或成功提示
     */
    String addNoteLike(Integer uid, String userId, Integer authorUid, Integer noteId);

    /**
     * 阐述笔记点赞
     * @param uid 点赞用户 uid
     * @param userId 用户 id
     * @param authorUid 作者 uid
     * @param noteId 笔记 id
     * @return 返回删除笔记成功提示
     */
    String deleteNoteLike(Integer uid, String userId, Integer authorUid, Integer noteId);

    /**
     * 添加笔记评论喜欢
     * @param uid 点击喜欢的用户 uid
     * @param userId 用户 id
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     * @return 返回添加过程中的提示
     */
    Result<String> addNoteCommentLike(Integer uid, String userId, Integer noteId, Integer authorUid, Long rootCommentId, Long subCommentId);

    /**
     * 删除笔记评论喜欢
     * @param uid 点击喜欢的用户 uid
     * @param userId 用户 id
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     * @return 返回删除过程中的提示
     */
    Result<String> deleteNoteCommentLike(Integer uid, String userId, Integer noteId, Integer authorUid, Long rootCommentId, Long subCommentId);

    /**
     * 添加笔记评论不喜欢
     * @param uid 点击不喜欢的用户 uid
     * @param userId 用户 id
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     * @return 返回添加过程中的提示
     */
    Result<String> addNoteCommentUnlike(Integer uid, String userId, Integer noteId, Integer authorUid, Long rootCommentId, Long subCommentId);

    /**
     * 删除笔记评论不喜欢
     * @param uid 点击不喜欢的用户 uid
     * @param userId 用户 id
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     * @return 返回删除过程中的提示
     */
    Result<String> deleteNoteCommentUnlike(Integer uid, String userId, Integer noteId, Integer authorUid, Long rootCommentId, Long subCommentId);
}
