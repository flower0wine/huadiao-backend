package com.huadiao.configuration;

import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.context.annotation.*;

/**
 * @author flowerwine
 * @date 2024 年 04 月 20 日
 */
@Configuration
@ImportResource({
        "classpath:spring-ioc.xml",
})
@PropertySource("classpath:prod.properties")
@Profile("prod")
public class ProdConfig {
    public ProdConfig() {
        Configurator.initialize(null, "classpath:log4j2-prod.xml");
    }
}
