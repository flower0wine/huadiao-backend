package com.huadiao.service;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public abstract class AbstractNoteOperateService extends AbstractService implements NoteOperateService {
    /**
     * 检查评论 id 是否符合要求, 要求评论 id 大于 0 并且评论 id 小于等于 redis 中保存的评论 id
     * @param commentId 评论 id
     * @return 返回检查结果
     */
    protected boolean checkCommentId(Long commentId) {
        long jedisCommentId = noteJedisUtil.getCommentId();
        return commentId > 0 && commentId <= jedisCommentId;
    }

    @Value("${star.defaultNoteStarGroupId}")
    protected int defaultNoteStarGroupId;

    /**
     * 最大收藏数量
     */
    public int MAX_NOTE_STAR_VALUE = 600;

    /**
     * 达到笔记收藏最大数量
     */
    public String ATTACH_MAX_NOTE_STAR = "attachMaxNoteStar";

    /**
     * 笔记不存在, 可能是用户不存在, 也可能是用户笔记不存在
     */
    public String NOTE_NOT_EXIST = "noteNotExist";

    /**
     * 未分配的评论 id, 当一个评论为父评论时, 则 sub_comment_id 为 0, 仅限在笔记评论喜欢表和不喜欢表中是 null, 在笔记评论表中则是 null
     */
    public Long UNDISTRIBUTED_COMMENT_ID = 0L;

    /**
     * 新增笔记收藏成功
     */
    public String ADD_NOTE_STAR_SUCCEED = "addNoteStarSucceed";

    /**
     * 删除笔记收藏成功
     */
    public String DELETE_NOTE_STAR_SUCCEED = "deleteNoteStarSucceed";

    /**
     * 新增笔记不喜欢成功
     */
    public String ADD_NOTE_UNLIKE_SUCCEED = "addNoteUnlikeSucceed";

    /**
     * 删除笔记不喜欢成功
     */
    public String DELETE_NOTE_UNLIKE_SUCCEED = "deleteNoteUnlikeSucceed";

    /**
     * 新增笔记点赞成功
     */
    public String ADD_NOTE_LIKE_SUCCEED = "addNoteLikeSucceed";

    /**
     * 删除笔记点赞成功
     */
    public String DELETE_NOTE_LIKE_SUCCEED = "deleteNoteLikeSucceed";

    /**
     * 添加笔记评论喜欢成功
     */
    public String ADD_NOTE_COMMENT_LIKE_SUCCEED = "addNoteCommentLikeSucceed";

    /**
     * 删除笔记评论喜欢成功
     */
    public String DELETE_NOTE_COMMENT_LIKE_SUCCEED = "deleteNoteCommentLikeSucceed";

    /**
     * 添加笔记评论不喜欢成功
     */
    public String ADD_NOTE_COMMENT_UNLIKE_SUCCEED = "addNoteCommentUnlikeSucceed";

    /**
     * 删除笔记评论不喜欢成功
     */
    public String DELETE_NOTE_COMMENT_UNLIKE_SUCCEED = "deleteNoteCommentUnlikeSucceed";

}
