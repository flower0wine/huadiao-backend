package com.huadiao.controller;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author flowerwine
 * @date 2024 年 07 月 20 日
 */
public abstract class AbstractEmoteController extends AbstractUploadController implements EmoteController {

    @Value("${fileUpload.emote.maxSize}")
    protected Long maxSize;

    @Value("${fileUpload.emote.allowExtension}")
    protected String[] allowExtension;
}
