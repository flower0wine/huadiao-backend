package com.huadiao.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description Spring 配置类
 */
@Configuration
@ImportResource("classpath:spring-ioc.xml")
@PropertySource("classpath:properties/huadiao.properties")
public class SpringConfig {

}
