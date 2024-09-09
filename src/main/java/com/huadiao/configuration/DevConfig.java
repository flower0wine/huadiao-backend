package com.huadiao.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * @author flowerwine
 * @date 2024 年 09 月 09 日
 */
@Configuration
@Profile("dev")
@PropertySource("classpath:dev/huadiao.properties")
public class DevConfig {
}
