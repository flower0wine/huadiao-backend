package com.huadiao.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 计算请求耗时的过滤器
 */
@Slf4j
@Component
public class ConsumeTimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        long end = System.currentTimeMillis();
        long consumeTime = end - start;
        if(consumeTime < 300)
            log.trace("此次请求耗时 {} ms", consumeTime);
        else if(consumeTime < 600)
            log.debug("此次请求耗时 {} ms", consumeTime);
        else
            log.warn("此次请求耗时 {} ms", consumeTime);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
