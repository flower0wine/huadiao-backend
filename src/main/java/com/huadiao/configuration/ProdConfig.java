package com.huadiao.configuration;

import org.springframework.context.annotation.*;

/**
 * @author flowerwine
 * @date 2024 年 09 月 09 日
 */
@Configuration
@Profile("prod")
@PropertySource("classpath:prod/huadiao.properties")
public class ProdConfig {
}
