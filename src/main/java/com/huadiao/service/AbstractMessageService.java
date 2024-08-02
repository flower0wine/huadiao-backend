package com.huadiao.service;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
public abstract class AbstractMessageService extends AbstractService {

    @Value("${message.likeUserLimit}")
    protected int likeUserLimit;

    @Value("${message.type.note}")
    protected int likeNoteMessageType;

    @Value("${message.type.comment}")
    protected int likeCommentMessageType;

    @Value("${message.whisperMessageMinLength}")
    protected int whisperMessageMinLength;

    @Value("${message.whisperMessageMaxLength}")
    protected int whisperMessageMaxLength;

    /**
     * 未分配的评论 id, 当一个评论为父评论时, 则 sub_comment_id 为 0, 仅限在笔记评论喜欢表和不喜欢表中是 null, 在笔记评论表中则是 null
     */
    public Integer UNDISTRIBUTED_COMMENT_ID = 0;
}