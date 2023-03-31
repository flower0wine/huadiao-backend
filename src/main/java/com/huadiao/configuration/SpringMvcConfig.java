package com.huadiao.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description SpringMvc 配置类
 */
@Configuration
@ImportResource("classpath:spring-mvc.xml")
public class SpringMvcConfig implements WebMvcConfigurer {

    /**
     * 跨域请求设置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }


}
