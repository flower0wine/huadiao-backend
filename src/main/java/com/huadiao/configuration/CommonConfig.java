package com.huadiao.configuration;

import com.huadiao.service.upload.VideoFragmentUpload;
import com.huadiao.util.FragmentUpload;
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
    private String tempDir;

    @Value("${fileUpload.video.storeDir}")
    private String storeDir;

    @Value("${fileUpload.video.chunkSize}")
    private Integer chunkSize;

    @Bean("videoFragmentUploadBean")
    public FragmentUpload createVideoFragmentUpload() {
        return new VideoFragmentUpload(tempDir, storeDir, chunkSize);
    }
}
