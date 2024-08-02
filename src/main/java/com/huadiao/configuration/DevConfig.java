package com.huadiao.configuration;

import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * @author flowerwine
 * @date 2024 年 04 月 20 日
 */
@Configuration
@ImportResource({
        "classpath:spring-ioc.xml",
        "classpath:elasticsearch-spring-config.xml",
})
@PropertySource("classpath:dev.properties")
@Profile("dev")
public class DevConfig {

    public DevConfig() {
        Configurator.initialize(null, "classpath:log4j2-dev.xml");
    }
}
