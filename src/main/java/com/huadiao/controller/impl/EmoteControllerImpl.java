package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractEmoteController;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.EmoteDto;
import com.huadiao.service.EmoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * @author flowerwine
 * @date 2024 年 07 月 20 日
 */
@Slf4j
@RestController
@RequestMapping("/emote")
public class EmoteControllerImpl extends AbstractEmoteController {

    @Resource
    private EmoteService emoteUploadService;

    @Override
    @PostMapping("/upload/multiple")
    public Result<?> uploadEmote(@RequestParam MultipartFile file, EmoteDto emoteDto) {

        if (file == null) {
            log.debug("未提供表情图片");
            return Result.errorParam("请选择要上传的表情图片");
        }

        if (emoteDto == null || emoteDto.getGid() == null || emoteDto.getName() == null || emoteDto.getType() == null) {
            log.debug("上传的表情图片信息不完整");
            return Result.errorParam("请提供完整的表情图片信息");
        }

        if (!limitFileSize(maxSize, file.getSize())) {
            log.debug("上传的表情图片大小超过限制");
            return Result.errorParam("表情图片大小不能超过 " + maxSize / 1024 + "KB");
        }

        if (file.getOriginalFilename() == null) {
            log.debug("获取不到表情文件的名称");
            return Result.errorParam("获取不到表情文件的名称");
        }

        if (!extensionMatch(file.getOriginalFilename(), allowExtension)) {
            log.debug("上传的表情图片格式不支持");
            return Result.errorParam("表情图片格式仅支持 " + Arrays.toString(allowExtension));
        }

        return emoteUploadService.uploadEmote(file, emoteDto);
    }

    @Override
    @GetMapping
    public Result<?> selectEmote(HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        log.debug("uid 为 {} 的用户获取了表情", uid);
        return emoteUploadService.selectEmote();
    }
}
