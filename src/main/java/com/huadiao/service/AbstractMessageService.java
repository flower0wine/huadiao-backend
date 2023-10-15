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
}