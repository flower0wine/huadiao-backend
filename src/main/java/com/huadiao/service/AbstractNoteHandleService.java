package com.huadiao.service;

/**
 * @author flowerwine
 * @projectName huadiao-user-back
 */
public abstract class AbstractNoteHandleService extends AbstractService implements NoteOperateService {

    /**
     * 检查评论 id 是否符合要求, 要求评论 id 大于 0 并且评论 id 小于等于 redis 中保存的评论 id
     * @param commentId 评论 id
     * @return 返回检查结果
     */
    protected boolean checkCommentId(Long commentId) {
        long jedisCommentId = idGeneratorJedisUtil.getCommentId();
        return commentId > 0 && commentId <= jedisCommentId;
    }
}
