package com.huadiao.service;

import com.huadiao.constant.CodeProperties;
import com.huadiao.constant.CookieProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author flowerwine
 * @date 2024 年 02 月 09 日
 */
@Slf4j
public abstract class AbstractCommonService extends AbstractService implements CommonService {

    @Autowired
    protected CodeProperties codeProperties;

    @Autowired
    protected CookieProperties cookieProperties;

}
