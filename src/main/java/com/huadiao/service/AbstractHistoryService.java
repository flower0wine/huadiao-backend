package com.huadiao.service;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author flowerwine
 * @date 2023 年 09 月 18 日
 */
public abstract class AbstractHistoryService extends AbstractService implements HistoryService {

    /**
     * 单次请求最多能获取的数据量
     */
    @Value("${history.requestMaxRow}")
    protected int requestMaxRow;
}
