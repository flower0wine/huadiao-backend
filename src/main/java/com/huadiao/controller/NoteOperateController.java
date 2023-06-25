package com.huadiao.controller;

import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;

/**
 * 笔记收藏控制器接口
 * @author flowerwine
 */
public interface NoteOperateController extends Controller {

    /**
     * 新增笔记收藏
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @return 返回新增笔记收藏过程的错误或者成功提示
     */
    String addNewNoteStar(HttpSession session, Integer uid, Integer noteId);

    /**
     * 删除笔记收藏
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @return 返回删除成功提示
     */
    String deleteNoteStar(HttpSession session, Integer uid, Integer noteId);

    /**
     * 新增笔记点赞
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @return 返回新增笔记点赞过程的错误或者成功提示
     */
    String addNoteLike(HttpSession session, Integer uid, Integer noteId);

    /**
     * 删除笔记点赞
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @return 返回删除点赞成功提示
     */
    String deleteNoteLike(HttpSession session, Integer uid, Integer noteId);

    /**
     * 新增笔记不喜欢
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @return 返回新增不喜欢过程的错误或者成功提示
     */
    String addNoteUnlike(HttpSession session, Integer uid, Integer noteId);

    /**
     * 删除笔记不喜欢
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @return 返回删除不喜欢成功提示
     */
    String deleteNoteUnlike(HttpSession session, Integer uid, Integer noteId);

    /**
     * 新增笔记评论喜欢
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     * @return 返回新增过程中的提示
     */
    Result<String> addNoteCommentLike(HttpSession session, Integer uid, Integer noteId, Long rootCommentId, Long subCommentId);

    /**
     * 删除笔记评论喜欢
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     * @return 返回删除过程中的提示
     */
    Result<String> deleteNoteCommentLike(HttpSession session, Integer uid, Integer noteId, Long rootCommentId, Long subCommentId);

    /**
     * 新增笔记评论不喜欢
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     * @return 返回新增过程中的提示
     */
    Result<String> addNoteCommentUnlike(HttpSession session, Integer uid, Integer noteId, Long rootCommentId, Long subCommentId);

    /**
     * 删除笔记评论不喜欢
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     * @return 返回删除过程中的提示
     */
    Result<String> deleteNoteCommentUnlike(HttpSession session, Integer uid, Integer noteId, Long rootCommentId, Long subCommentId);
}
