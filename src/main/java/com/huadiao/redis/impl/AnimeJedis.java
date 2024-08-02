package com.huadiao.redis.impl;

import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.AnimeJedisUtil;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

/**
 * @author flowerwine
 * @date 2024 年 04 月 23 日
 */
@Component
public class AnimeJedis extends AbstractJedis implements AnimeJedisUtil {

    private final String ANIME_ID_JEDIS_KEY = "animeId";

    public AnimeJedis(JedisPool jedisPool) {
        this.initialIdGenerator(jedisPool, ANIME_ID_JEDIS_KEY);
    }

    @Override
    protected int getGeneratorId(String jedisKeyGeneratorId) {
        return super.getGeneratorId(jedisKeyGeneratorId);
    }

    @Override
    public int generateAnimeId() {
        return super.getGeneratorId(ANIME_ID_JEDIS_KEY);
    }
}
