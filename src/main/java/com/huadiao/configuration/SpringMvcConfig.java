package com.huadiao.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description SpringMvc 配置类
 */
@Configuration
@ImportResource("classpath:spring-mvc.xml")
public class SpringMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        // 异常解析
        resolvers.add((request, response, handler, ex) -> {
            ex.printStackTrace();
            return null;
        });
    }

}
