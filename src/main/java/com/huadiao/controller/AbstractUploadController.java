package com.huadiao.controller;

/**
 * @author flowerwine
 * @date 2024 年 07 月 20 日
 */
public abstract class AbstractUploadController extends AbstractController {

    /**
     * 文件大小校验
     * @param maxSize 最大文件大小
     * @param size 文件大小
     * @return 如果文件大小符合要求，则返回 true，否则返回 false
     */
    protected boolean limitFileSize(Long maxSize, Long size) {
        return size != null && size > 0 && size <= maxSize;
    }

    /**
     * 文件类型校验
     * @param filename 文件名, 包含扩展名
     * @return 如果扩展名符合要求，则返回 true，否则返回 false
     */
    protected boolean extensionMatch(String filename, String[] allowExtensions) {
        String extension = filename.substring(filename.lastIndexOf("."));
        for(String allowExtension : allowExtensions) {
            if(allowExtension.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
