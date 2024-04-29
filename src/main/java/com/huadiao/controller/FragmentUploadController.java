package com.huadiao.controller;

import com.huadiao.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2024 年 04 月 20 日
 */
public interface FragmentUploadController extends Controller {

    /**
     * 预上传文件
     * @param session session 对象
     * @param animeId 番剧 id
     * @param filename 文件在客户端的名称
     * @param size 文件大小
     * @return 返回处理结果
     */
    Result<?> preload(HttpSession session, Integer animeId, String filename, Long size);

    /**
     * 上传分片
     * @param session session 对象
     * @param file 分片文件
     * @param index 索引
     * @return 返回处理结果
     */
    Result<?> uploadChunk(HttpSession session, MultipartFile file, Integer index);

    /**
     * 上传完成
     * @param session session 对象
     * @return 返回处理结果
     */
    Result<?> uploaded(HttpSession session);

    /**
     * 取消上传
     * @param session session 对象
     * @return 返回处理结果
     */
    Result<?> cancel(HttpSession session);
}
