package com.huadiao.configuration;

import com.huadiao.filter.ConsumeTimeFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        // spring 配置类
        return new Class[]{
                ProdConfig.class,
                CommonConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // springmvc 配置类
        return new Class[]{SpringMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        // 拦截除 jsp 的所有请求
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        // 处理字符编码
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        // 计算每次请求耗时
        ConsumeTimeFilter consumeTimeFilter = new ConsumeTimeFilter();
        return new Filter[]{characterEncodingFilter, consumeTimeFilter};
    }

}
