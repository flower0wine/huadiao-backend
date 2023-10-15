package com.huadiao.controller;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author flowerwine
 * @date 2023 年 10 月 13 日
 */
public abstract class AbstractController implements Controller {

    @Value("${huadiao.uidKey}")
    protected String uidKey;

    @Value("${huadiao.userIdKey}")
    protected String userIdKey;

    @Value("${huadiao.nicknameKey}")
    protected String nicknameKey;
}
