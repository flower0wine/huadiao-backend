package com.huadiao.redis.impl;

import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.NoteJedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Component
public class NoteJedis extends AbstractJedis implements NoteJedisUtil {

    /**
     * 评论 id, redis key
     */
    private String jedisKeyCommentId = "commentId";

    private String jedisKeyNoteId = "noteId";

    public NoteJedis(JedisPool jedisPool) {
        initialIdGenerator(jedisPool, jedisKeyCommentId);
        initialIdGenerator(jedisPool, jedisKeyNoteId);
    }

    @Override
    public int generateCommentId() {
        return generateGeneratorId(jedisKeyCommentId);
    }

    @Override
    public int getCommentId() {
        return getGeneratorId(jedisKeyCommentId);
    }

    @Override
    public int generateNoteId() {
        return generateGeneratorId(jedisKeyNoteId);
    }
}
