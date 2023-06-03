package com.huadiao.redis.impl;

import cn.hutool.core.util.StrUtil;
import com.huadiao.mapper.NoteStarMapper;
import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.StarJedisUtil;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Component
public class StarJedis extends AbstractJedis implements StarJedisUtil {

    /**
     * redis 存储的键名, 格式为 huadiao.noteStarAmount.uid, uid 为数字,
     * 这里使用占位符 {} 取代
     */
    protected String redisKeyNoteStarAmount = "huadiao.noteStarAmount.{}";

    @Override
    public Integer getUserStarAmount(Integer uid) {
        Jedis jedis = jedisPool.getResource();
        String jedisKey = StrUtil.format(redisKeyNoteStarAmount, uid);
        String noteStarAmount = jedis.get(jedisKey);
        if(noteStarAmount != null) {
            jedisPool.returnResource(jedis);
            return Integer.parseInt(noteStarAmount);
        } else {
            Integer countNoteStar = noteStarMapper.countNoteStarByUid(uid);
            jedis.set(jedisKey, String.valueOf(countNoteStar));
            jedisPool.returnResource(jedis);
            return countNoteStar;
        }
    }
}
