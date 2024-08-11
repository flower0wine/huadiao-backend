package com.huadiao.configuration;

import com.huadiao.constant.ProgramEnvironment;
import com.huadiao.service.upload.emote.EmoteUpload;
import com.huadiao.service.upload.fragment.FragmentUpload;
import com.huadiao.service.upload.video.VideoFragmentUpload;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;

/**
 * Spring 配置类
 *
 * @author flowerwine
 * @version 1.1
 */
@Configuration
@ImportResource({
        "classpath:spring-ioc.xml",
})
@Slf4j
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

    @Autowired
    public CommonConfig(Environment environment) {
        String env = environment.getActiveProfiles()[0];

        if (ProgramEnvironment.DEVELOPMENT.equals(env)) {
            Configurator.initialize("devLogger", "classpath:log4j2-dev.xml");
        } else if (ProgramEnvironment.PRODUCTION.equals(env)) {
            Configurator.initialize("prodLogger", "classpath:log4j2-prod.xml");
        } else {
            log.warn("The current program running environment is not recognized");
            return;
        }
        log.debug("The current program running environment is: {}", env);
    }
}
