package com.huadiao.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description Spring 配置类
 */
@Configuration
@ImportResource("classpath:spring-ioc.xml")
public class SpringConfig {



}
