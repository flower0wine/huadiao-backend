package com.huadiao.redis.impl;

import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.UserBaseJedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

/**
 * @author flowerwine
 * @date 2023 年 10 月 01 日
 */
@Component
public class UserBaseJedis extends AbstractJedis implements UserBaseJedisUtil {

    private String userUidJedisKey = "uid";

    @Value("${huadiao.minUid}")
    private int minUid;

    public UserBaseJedis(JedisPool jedisPool) {
        this.initialIdGenerator(jedisPool, userUidJedisKey, minUid);
    }

    @Override
    public int generateUid() {
        return generateGeneratorId(userUidJedisKey);
    }
}
