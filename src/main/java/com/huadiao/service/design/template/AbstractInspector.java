package com.huadiao.service.design.template;

import com.huadiao.elasticsearch.repository.UserRepository;
import com.huadiao.mapper.*;
import com.huadiao.redis.UserBaseJedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author flowerwine
 * @date 2024 年 02 月 10 日
 */
public abstract class AbstractInspector {

    @Value("${oauth.github.clientId}")
    protected String githubClientId;

    @Value("${oauth.github.clientSecret}")
    protected String githubClientSecret;

    @Value("${oauth.github.accessTokenUri}")
    protected String githubAccessTokenUri;

    @Value("${oauth.github.api.user}")
    protected String githubApiUser;

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected UserBaseJedisUtil userBaseJedisUtil;

    @Autowired
    protected UserInfoMapper userInfoMapper;

    @Autowired
    protected UserSettingsMapper userSettingsMapper;

    @Autowired
    protected HuadiaoHouseMapper huadiaoHouseMapper;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected HomepageMapper homepageMapper;


}
