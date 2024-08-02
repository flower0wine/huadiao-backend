package com.huadiao.service;

import com.huadiao.entity.Result;
import com.huadiao.entity.dto.EmoteDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author flowerwine
 * @date 2024 年 07 月 20 日
 */
public interface EmoteService {

    /**
     * 上传表情包
     * @param file 表情包文件
     * @param emoteDto 表情包信息
     * @return 上传结果
     */
    Result<?> uploadEmote(MultipartFile file, EmoteDto emoteDto);

    /**
     * 查询表情包
     * @return 查询结果
     */
    Result<?> selectEmote();
}
