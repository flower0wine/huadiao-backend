package com.huadiao.redis.impl;

import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.NoteJedisUtil;
import org.springframework.stereotype.Component;

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

    @Override
    public int generateCommentId() {
        return generateGeneratorId(jedisKeyCommentId);
    }

    @Override
    public int getCommentId() {
        return getGeneratorId(jedisKeyCommentId);
    }
}
