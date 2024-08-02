package com.huadiao.service.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.dao.EmoteDao;
import com.huadiao.entity.dto.EmoteDto;
import com.huadiao.entity.vo.EmoteVo;
import com.huadiao.mapper.EmoteMapper;
import com.huadiao.service.AbstractEmoteService;
import com.huadiao.service.upload.single.FileUpload;
import com.huadiao.service.upload.single.FileUploadReturnValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author flowerwine
 * @date 2024 年 07 月 20 日
 */
@Slf4j
@Service
public class EmoteServiceImpl extends AbstractEmoteService {

    @Resource
    private FileUpload fileUpload;

    @Resource
    private EmoteMapper emoteMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> uploadEmote(MultipartFile file, EmoteDto emoteDto) {

        EmoteDao emoteDao = new EmoteDao();
        try {
            // 保存表情文件
            FileUploadReturnValue returnValue = fileUpload.upload(file);

            // 插入数据库
            emoteDao.setName(emoteDto.getName());
            emoteDao.setGid(emoteDto.getGid());
            emoteDao.setType(emoteDto.getType());
            emoteDao.setFilename(returnValue.getFilename());
        } catch (IOException e) {
            log.warn("上传的表情图片上传失败");

            throw new RuntimeException("上传的表情图片上传失败");
        }

        emoteMapper.insertEmotes(emoteDao);

        return Result.ok();
    }

    @Override
    public Result<?> selectEmote() {
        List<EmoteDao> emoteDaoList = emoteMapper.selectEmote();

        Map<Integer, List<EmoteDao>> collect = emoteDaoList.stream().collect(Collectors.groupingBy(EmoteDao::getGid));

        List<EmoteVo> emoteVoList = collect.entrySet().stream().map(entry -> {
            EmoteDao emoteDao = entry.getValue().get(0);
            EmoteVo emoteVo = new EmoteVo();
            emoteVo.setGid(entry.getKey());
            emoteVo.setEmotes(entry.getValue());
            emoteVo.setFilename(emoteDao.getFilename());
            emoteVo.setType(emoteDao.getType());
            emoteVo.setName(emoteDao.getName());
            return emoteVo;
        }).collect(Collectors.toList());
        return Result.ok(emoteVoList);
    }
}
