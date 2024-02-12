package com.huadiao.redis.impl;

import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.FollowFanJedisUtil;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 该类用于生成唯一递增 id
 *
 * @author flowerwine
 * @projectName huadiao-user-back
 */
@Component
public class FollowFanJedis extends AbstractJedis implements FollowFanJedisUtil {

    private String jedisKeyFollowGroupId = "followGroupId";

    public FollowFanJedis(JedisPool jedisPool) {
        this.initialIdGenerator(jedisPool, jedisKeyFollowGroupId);
    }

    @Override
    public int generateFollowGroupId() {
        return super.generateGeneratorId(jedisKeyFollowGroupId);
    }
}
