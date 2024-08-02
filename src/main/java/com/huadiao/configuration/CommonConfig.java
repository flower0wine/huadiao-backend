package com.huadiao.configuration;

import com.huadiao.service.upload.emote.EmoteUpload;
import com.huadiao.service.upload.video.VideoFragmentUpload;
import com.huadiao.service.upload.fragment.FragmentUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring 配置类
 *
 * @author flowerwine
 * @version 1.1
 */
@Configuration
public class CommonConfig {

    @Value("${fileUpload.video.tempDir}")
    private String videoTempDir;

    @Value("${fileUpload.video.storeDir}")
    private String videoStoreDir;

    @Value("${fileUpload.video.chunkSize}")
    private Integer videoChunkSize;

    @Bean("videoFragmentUploadBean")
    public FragmentUpload createVideoFragmentUpload() {
        return new VideoFragmentUpload(videoTempDir, videoStoreDir, videoChunkSize);
    }


    @Value("${fileUpload.emote.storeDir}")
    private String emoteStoreDir;

    @Bean("emoteUploadBean")
    public EmoteUpload createEmoteUpload() {
        return new EmoteUpload(emoteStoreDir);
    }
}
