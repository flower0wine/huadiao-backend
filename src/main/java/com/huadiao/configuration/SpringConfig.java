package com.huadiao.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import redis.clients.jedis.JedisPool;

import java.util.Objects;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description Spring 配置类
 */
@Configuration
@ImportResource("classpath:spring-ioc.xml")
@PropertySource("classpath:properties/huadiaoConfig.properties")
public class SpringConfig {


}
