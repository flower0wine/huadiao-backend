package com.huadiao.service;

import com.huadiao.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author flowerwine
 * @date 2024 年 04 月 20 日
 */
public interface VideoUploadService extends Service {
    /**
     * 预上传文件
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param animeId 番剧 id
     * @param filename 文件在客户端的名称
     * @param size 文件大小
     * @return 返回处理结果
     */
    Result<?> preload(Integer uid, String userId, Integer animeId, String filename, Long size);

    /**
     * 上传分片
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param file 分片文件
     * @param index 索引
     * @return 返回处理结果
     * @throws IOException IO异常
     */
    Result<?> uploadChunk(Integer uid, String userId, MultipartFile file, Integer index) throws IOException;

    /**
     * 上传完成
     * @param uid 用户 uid
     * @param userId 用户 id
     * @return 返回处理结果
     * @throws IOException IO异常
     */
    Result<?> uploaded(Integer uid, String userId) throws IOException;

    /**
     * 取消上传
     * @param uid 用户 uid
     * @param userId 用户 id
     * @return 返回处理结果
     */
    Result<?> cancel(Integer uid, String userId);
}
