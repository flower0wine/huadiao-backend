package com.huadiao.controller;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author flowerwine
 * @date 2024 年 04 月 27 日
 */
public abstract class AbstractVideoUploadController extends AbstractUploadController implements VideoUploadController {

    @Value("${fileUpload.video.maxSize}")
    protected Long maxSize;

    @Value("${fileUpload.video.chunkSize}")
    protected Integer chunkSize;

    @Value("${fileUpload.video.allowExtension}")
    protected String[] allowExtensions;
}
