package com.huadiao.service;

import com.huadiao.entity.Poem;

/**
 * 业务层: 处理诗句的接口
 * @author flowerwine
 */
public interface PoemService {

    /**
     * 获取诗句
     * @return 返回诗句
     */
    Poem getPoem();
}
