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

    /**
     * 评论 id, redis key
     */
    private String jedisKeyCommentId = "commentId";

    public FollowFanJedis(JedisPool jedisPool) {
        this.initialCommentIdGenerator(jedisPool, jedisKeyCommentId);
    }

    @Override
    public long generateCommentId() {
        return generateGeneratorId(jedisKeyCommentId);
    }

    @Override
    public long getCommentId() {
        return getGeneratorId(jedisKeyCommentId);
    }
}
