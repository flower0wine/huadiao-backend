package com.huadiao.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring 配置类
 *
 * @author flowerwine
 * @version 1.1
 */
@Configuration
@ImportResource("classpath:spring-ioc.xml")
@PropertySource({
        "classpath:properties/oauth/github.properties",
        "classpath:properties/oauth/google.properties",
        "classpath:properties/huadiao.properties",
})
public class SpringConfig {

}
