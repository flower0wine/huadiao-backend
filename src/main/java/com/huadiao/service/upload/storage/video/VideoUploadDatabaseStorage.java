package com.huadiao.service.upload.storage.video;

import com.huadiao.entity.upload.video.UploadInfo;
import com.huadiao.mapper.VideoUploadMapper;
import com.huadiao.service.upload.storage.StorageEngineEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author flowerwine
 * @date 2024 年 04 月 22 日
 */
@Component
public class VideoUploadDatabaseStorage implements VideoUploadStorage {
    private final VideoUploadMapper videoUploadMapper;

    @Autowired
    public VideoUploadDatabaseStorage(VideoUploadMapper videoUploadMapper) {
        this.videoUploadMapper = videoUploadMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUploadInfo(Integer animeId, Integer uid, String filename, Long size, Boolean uploadSucceed) {
        return videoUploadMapper.insertOrUpdateUploadInfo(animeId, uid, filename, size, uploadSucceed) != 0;
    }

    @Override
    public List<UploadInfo> getUploadInfo(Integer uid, Integer offset, Integer row) {
        return videoUploadMapper.selectUploadInfo(uid, offset, row);
    }

    @Override
    public boolean deleteUploadInfo(Integer uid, String filename) {
        return videoUploadMapper.deleteUploadInfo(uid, filename) != 0;
    }

    @Override
    public boolean match(StorageEngineEnum storageEngineEnum) {
        return StorageEngineEnum.DATABASE.equals(storageEngineEnum);
    }
}
