package com.huadiao.service.design.template;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.huadiao.elasticsearch.repository.UserRepository;
import com.huadiao.mapper.*;
import com.huadiao.redis.UserBaseJedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2024 年 02 月 10 日
 */
@Slf4j
public abstract class AbstractInspector {

    @Value("${oauth.github.clientId}")
    protected String githubClientId;

    @Value("${oauth.github.clientSecret}")
    protected String githubClientSecret;

    @Value("${oauth.github.accessTokenUri}")
    protected String githubAccessTokenUri;

    @Value("${oauth.github.api.user}")
    protected String githubApiUser;

    @Value("${oauth.google.accessTokenUri}")
    protected String googleAccessTokenUri;

    @Value("${oauth.google.api.user}")
    protected String googleApiUser;

    @Value("${oauth.google.clientId}")
    protected String googleClientId;

    @Value("${oauth.google.clientSecret}")
    protected String googleClientSecret;

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

    @Autowired(required = false)
    protected UserRepository userRepository;

    @Autowired
    protected HomepageMapper homepageMapper;


    /**
     * 获取用户信息
     *
     * @param accessToken 访问令牌
     * @param apiUser     用户信息 api
     * @return 返回用户信息
     */
    protected JSONObject getUserInfo(String accessToken, String apiUser) {
        long start = System.currentTimeMillis();
        log.debug("向第三方请求数据中...");
        // 通过访问令牌获取用户信息
        HttpRequest httpRequest = HttpRequest.get(apiUser)
                .header(Header.ACCEPT, "application/json")
                .header(Header.AUTHORIZATION, accessToken);

        String result = httpRequest.execute().body();

        log.debug("向第三方请求数据完成, 耗时: {} ms", System.currentTimeMillis() - start);

        return JSONUtil.parseObj(result);
    }

    /**
     * 返回数据
     *
     * @param code           授权码
     * @param clientId       客户端 id
     * @param clientSecret   密钥
     * @param accessTokenUri 访问令牌获取 URI
     * @return 返回数据
     */
    protected JSONObject getData(String code, String clientId, String clientSecret, String accessTokenUri) {
        return this.getData(this.getParamsMap(clientId, clientSecret, code), accessTokenUri);
    }

    /**
     * 获取数据
     *
     * @param code           授权码
     * @param clientId       客户端 id
     * @param clientSecret   密钥
     * @param accessTokenUri 访问令牌获取 URI
     * @param grantType      授权类型
     * @param redirectUri    重定向 URI
     * @return 返回数据
     */
    protected JSONObject getData(String code, String clientId, String clientSecret, String accessTokenUri,
                                 String grantType, String redirectUri) {
        Map<String, Object> map = this.getParamsMap(clientId, clientSecret, code);
        map.put("grant_type", grantType);
        map.put("redirect_uri", redirectUri);

        return this.getData(map, accessTokenUri);
    }

    private Map<String, Object> getParamsMap(String clientId, String clientSecret, String code) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("client_id", clientId);
        map.put("client_secret", clientSecret);
        map.put("code", code);
        return map;
    }

    private JSONObject getData(Map<String, Object> map, String accessTokenUri) {
        long start = System.currentTimeMillis();
        log.debug("获取访问令牌中...");

        // 获取访问令牌
        HttpRequest httpRequest = HttpRequest.post(accessTokenUri)
                .header(Header.ACCEPT, "application/json")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .form(map);

        String result = httpRequest.execute().body();

        log.debug("访问令牌获取成功, 耗时: {} ms", System.currentTimeMillis() - start);
        return JSONUtil.parseObj(result);
    }
}
