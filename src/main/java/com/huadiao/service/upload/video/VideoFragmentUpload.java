package com.huadiao.service.upload.video;

import com.huadiao.service.upload.fragment.FileInfo;
import com.huadiao.service.upload.fragment.FragmentUpload;
import com.huadiao.service.upload.fragment.PreloadReturnValue;
import lombok.Getter;

import java.util.List;

/**
 * @author flowerwine
 * @date 2024 年 04 月 20 日
 */
@Getter
public class VideoFragmentUpload extends FragmentUpload {

    public VideoFragmentUpload(String tempDir, String fileStoreDir, int chunkSize) {
        super(tempDir, fileStoreDir, chunkSize);
    }

    @Override
    protected PreloadReturnValue handlePreloadReturnValue(FileInfo fileInfo, List<Integer> uploadedChunk, int chunkSize) {
        PreloadReturnValue preloadReturnValue = new PreloadReturnValue();
        preloadReturnValue.setFileInfo(fileInfo);
        preloadReturnValue.setUploadedChunk(uploadedChunk);
        preloadReturnValue.setChunkSize(chunkSize);
        return preloadReturnValue;
    }

    @Override
    protected String customStoreFilename(FileInfo fileInfo) {
        return fileInfo.getFilenameFromServer() + fileInfo.getSuffix();
    }

}
