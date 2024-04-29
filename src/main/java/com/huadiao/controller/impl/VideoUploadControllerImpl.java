package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractVideoUploadController;
import com.huadiao.entity.Result;
import com.huadiao.service.VideoUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author flowerwine
 * @date 2024 年 04 月 20 日
 */
@Slf4j
@RestController
@RequestMapping("/upload/video")
public class VideoUploadControllerImpl extends AbstractVideoUploadController {
    private final VideoUploadService videoUploadService;

    @Autowired
    public VideoUploadControllerImpl(VideoUploadService videoUploadService) {
        this.videoUploadService = videoUploadService;
    }

    @Override
    @GetMapping("/preload")
    public Result<?> preload(HttpSession session, Integer animeId, String filename, Long size) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);

        if(animeId == null || filename == null || size == null || animeId <= 0 || size <= 0) {
            return Result.errorParam();
        }

        // 检查 size 大小是否超出限制
        if (size > maxSize) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的文件大小不符合要求, 文件大小为 {}", uid, userId, size);
            return Result.exceedLimit();
        }

        if (!extensionMatch(filename)) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的文件类型不符合要求, 后缀名为 {}", uid, userId, filename);
            return Result.errorParam();
        }

        try {
            return videoUploadService.preload(uid, userId, animeId, filename, size);
        } catch (Exception e) {
            log.error("uid, userId 分别为 {}, {} 的用户上传文件时发生异常, 错误信息: {}", uid, userId, e.getMessage());
            return Result.serverError();
        }
    }

    @Override
    @PostMapping("/uploading")
    public Result<?> uploadChunk(HttpSession session, @RequestParam("file") MultipartFile file, Integer index) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);

        // 检查分片文件是否符合要求
        if (file == null || file.isEmpty()) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的分片文件未提供或没有数据", uid, userId);
            return Result.errorParam();
        }

        long fileSize = file.getSize();
        if (fileSize > chunkSize) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的分片文件大小不符合要求, fileSize: {} > chunkSize: {}", uid, userId, fileSize, chunkSize);
            return Result.errorParam();
        }

        try {
            return videoUploadService.uploadChunk(uid, userId, file, index);
        } catch (IOException e) {
            log.warn("上传分片失败, 错误信息: {}", e.getMessage());
            return Result.serverError();
        }
    }

    @Override
    @GetMapping("/merge")
    public Result<?> uploaded(HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);

        try {
            return videoUploadService.uploaded(uid, userId);
        } catch (IOException e) {
            log.warn("合并分片失败, 错误信息: {}", e.getMessage());
            return Result.serverError();
        }
    }

    @Override
    @GetMapping("/cancel")
    public Result<?> cancel(HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return videoUploadService.cancel(uid, userId);
    }
}
