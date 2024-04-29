package com.huadiao.controller;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author flowerwine
 * @date 2024 年 04 月 27 日
 */
public abstract class AbstractVideoUploadController extends AbstractController implements VideoUploadController {

    @Value("${fileUpload.video.maxSize}")
    protected Integer maxSize;

    @Value("${fileUpload.video.chunkSize}")
    protected Integer chunkSize;

    @Value("${fileUpload.video.allowExtension}")
    private String[] allowExtensions;

    /**
     * 文件类型校验
     * @param filename 文件名, 包含扩展名, 如 123.mp4
     * @return 如果扩展名符合要求，则返回 true，否则返回 false
     */
    protected boolean extensionMatch(String filename) {
        String extension = filename.substring(filename.lastIndexOf("."));
        for(String allowExtension : allowExtensions) {
            if(allowExtension.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
