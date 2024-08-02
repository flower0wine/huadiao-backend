package com.huadiao.controller;

import com.huadiao.dto.note.NoteCommentDTO;
import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;

/**
 * 笔记收藏控制器接口
 * @author flowerwine
 */
public interface NoteOperateController extends Controller {
    /**
     * 删除笔记评论, 如果是父评论将会删除其下的所有子评论
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     * @return 返回删除过程中的相应提示
     */
    Result<?> deleteNoteComment(HttpSession session, Integer uid, Integer noteId, Integer rootCommentId, Integer subCommentId);

    /**
     * 新增笔记收藏
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @param groupId 分组 id
     * @return 返回新增笔记收藏过程的错误或者成功提示
     */
    String addNewNoteStar(HttpSession session, Integer uid, Integer noteId, Integer groupId);

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
     * @param noteCommentDTO 笔记评论 dto
     * @return 返回删除点赞成功提示
     */
    Result<?> deleteNoteLike(HttpSession session, NoteCommentDTO noteCommentDTO);

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
     * @param noteCommentDTO 笔记评论 DTO 对象
     * @return 返回新增过程中的提示
     */
    Result<?> addNoteCommentLike(HttpSession session, NoteCommentDTO noteCommentDTO);

    /**
     * 删除笔记评论喜欢
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     * @return 返回删除过程中的提示
     */
    Result<?> deleteNoteCommentLike(HttpSession session, Integer uid, Integer noteId, Integer rootCommentId, Integer subCommentId);

    /**
     * 新增笔记评论不喜欢
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     * @return 返回新增过程中的提示
     */
    Result<?> addNoteCommentUnlike(HttpSession session, Integer uid, Integer noteId, Integer rootCommentId, Integer subCommentId);

    /**
     * 删除笔记评论不喜欢
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId 子评论 id
     * @return 返回删除过程中的提示
     */
    Result<?> deleteNoteCommentUnlike(HttpSession session, Integer uid, Integer noteId, Integer rootCommentId, Integer subCommentId);

    /**
     * 举报笔记评论
     * @param session session 对象
     * @param uid 被举报者
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @param rootCommentId 根评论 id
     * @param subCommentId 子评论 id
     * @return 返回举报过程中的提示
     */
    Result<?> reportNoteComment(HttpSession session, Integer uid, Integer noteId, Integer authorUid, Integer rootCommentId, Integer subCommentId);
}
