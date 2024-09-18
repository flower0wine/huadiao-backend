package com.huadiao.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author flowerwine
 * @date 2024 年 09 月 18 日
 */
@Component
public class HuadiaoProperties {

    @Value("${huadiao.frontendHost}")
    public String frontendHost;

    @Value("${huadiao.uidKey}")
    public String uidKey;

    @Value("${huadiao.userIdKey}")
    public String userIdKey;

    @Value("${huadiao.nicknameKey}")
    public String nicknameKey;
}
