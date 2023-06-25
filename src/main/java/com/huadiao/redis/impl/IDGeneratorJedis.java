package com.huadiao.redis.impl;

import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.IDGeneratorJedisUtil;
import org.springframework.beans.factory.annotation.Value;
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
public class IDGeneratorJedis extends AbstractJedis implements IDGeneratorJedisUtil {

    /**
     * 评论 id, redis key
     */
    private String jedisKeyCommentId = "commentId";

    public IDGeneratorJedis(JedisPool jedisPool) {
        Jedis jedis = jedisPool.getResource();
        String commentId = jedis.get(jedisKeyCommentId);
        if (commentId == null) {
            jedis.set(jedisKeyCommentId, String.valueOf(0L));
        }
        jedis.close();
    }

    @Override
    public long generateCommentId() {
        Jedis jedis = jedisPool.getResource();
        long commentId;
        synchronized (IDGeneratorJedis.class) {
            commentId = Long.parseLong(jedis.get(jedisKeyCommentId));
            commentId++;
            jedis.set(jedisKeyCommentId, String.valueOf(commentId));
        }
        jedis.close();
        return commentId;
    }

    @Override
    public long getCommentId() {
        Jedis jedis = jedisPool.getResource();
        long commentId = Long.parseLong(jedis.get(jedisKeyCommentId));
        jedis.close();
        return commentId;
    }
}
