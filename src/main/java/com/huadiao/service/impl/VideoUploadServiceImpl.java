package com.huadiao.service.impl;

import com.huadiao.entity.Result;
import com.huadiao.service.AbstractVideoUploadService;
import com.huadiao.service.upload.fragment.PreloadReturnValue;
import com.huadiao.service.upload.storage.StorageUtils;
import com.huadiao.service.upload.video.VideoInfo;
import com.huadiao.service.upload.video.VideoUploadStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author flowerwine
 * @date 2024 年 04 月 20 日
 */
@Service
@Slf4j
public class VideoUploadServiceImpl extends AbstractVideoUploadService {

    @Resource
    private List<VideoUploadStorage> storageList;

    private final Map<Integer, VideoInfo> map = new ConcurrentHashMap<>();

    @Override
    protected String getUserUploadingServerFilename(Integer uid) {
        return map.get(uid).getServerFilename();
    }

    @Override
    protected void setUserUploadingServerFilename(Integer uid, String serverFilename) {
        map.get(uid).setServerFilename(serverFilename);
    }

    @Override
    public Result<?> preload(Integer uid, String userId, Integer animeId, String filename, Long size) {
        VideoInfo videoInfo = new VideoInfo();
        map.put(uid, videoInfo);

        PreloadReturnValue preloadReturnValue = commonPreload(uid, filename, size);

        // 保存上传信息
        StorageUtils.save(storageList, videoInfo.getAnimeId(), uid, videoInfo.getServerFilename(), size, false);

        log.debug("uid, userId 分别为 {}, {} 的用户尝试为 animeId 为 {} 的番剧添加视频", uid, userId, animeId);
        return Result.ok(preloadReturnValue);
    }

    @Override
    public Result<?> uploadChunk(Integer uid, String userId, MultipartFile file, Integer index) throws IOException {
        boolean upload = commonUpload(uid, file, index);
        if (upload) {
            log.debug("uid, userId 分别为 {}, {} 的用户成功上传番剧视频分片, 分片索引 index 为 {}", uid, userId, index);
            return Result.ok(null);
        } else {
            uploadInfoNotExistLog(uid, userId);
            return Result.errorParam();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> uploaded(Integer uid, String userId) throws IOException {
        boolean merge = commonUploaded(uid);
        if (merge) {
            removeUploadInfo(uid);

            VideoInfo videoInfo = map.get(uid);
            String serverFilename = videoInfo.getServerFilename();
            // 修改上传成功为 true
            StorageUtils.update(storageList, videoInfo.getAnimeId(), uid, serverFilename, true);

            log.debug("uid, userId 分别为 {}, {} 的用户成功成功上传视频, 视频名称为 {}", uid, userId, serverFilename);
            return Result.ok(null);
        } else {
            uploadInfoNotExistLog(uid, userId);
            return Result.errorParam();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> cancel(Integer uid, String userId) {
        boolean cancel = commonCancel(uid);
        if (cancel) {
            removeUploadInfo(uid);

            String serverFilename = map.get(uid).getServerFilename();
            // 删除上传记录
            StorageUtils.delete(storageList, uid, serverFilename);

            log.debug("uid, userId 分别为 {}, {} 的用户取消上传视频, 视频名称为 {}", uid, userId, serverFilename);
            return Result.ok(null);
        } else {
            uploadInfoNotExistLog(uid, userId);
            return Result.errorParam();
        }
    }

    private void uploadInfoNotExistLog(Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户提供的分片文件上传失败, 因为上传信息在服务端未找到", uid, userId);
    }

    private void removeUploadInfo(Integer uid) {
        map.remove(uid);
    }
}
