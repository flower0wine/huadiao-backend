package com.huadiao.mapper;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

/**
 * @author flowerwine
 * @date 2024 年 04 月 22 日
 */
public class VideoUploadTest {

    @Test
    public void test() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-ioc.xml");
        VideoUploadMapper videoUploadMapper = applicationContext.getBean(VideoUploadMapper.class);

        Integer animeId = 1;
        Integer uid = 29;
        String suffix = ".mp4";
        Long size = 123L;

        String uuid = UUID.randomUUID() + suffix;
        // 测试新增
        videoUploadMapper.insertOrUpdateUploadInfo(animeId, uid, uuid, size, false);
        // 测试修改
        videoUploadMapper.insertOrUpdateUploadInfo(animeId, uid, uuid, size, true);
        videoUploadMapper.insertOrUpdateUploadInfo(animeId, uid, uuid, size, true);
        videoUploadMapper.insertOrUpdateUploadInfo(animeId, uid, uuid, size, true);

        uuid = UUID.randomUUID() + suffix;
        // 测试再新增
        videoUploadMapper.insertOrUpdateUploadInfo(animeId, uid, uuid, size, false);
        // 测试修改
        videoUploadMapper.insertOrUpdateUploadInfo(animeId, uid, uuid, size, true);

        videoUploadMapper.deleteUploadInfo(uid, uuid);
    }
}
