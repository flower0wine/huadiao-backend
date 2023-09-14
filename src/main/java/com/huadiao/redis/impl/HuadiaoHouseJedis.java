package com.huadiao.redis.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.huadiao.entity.CardBorderImage;
import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.HuadiaoHouseJedisUtil;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 09 月 12 日
 */
@Component
public class HuadiaoHouseJedis extends AbstractJedis implements HuadiaoHouseJedisUtil {

    private String jedisKeyAnimeId = "animeId";

    private String jedisKeyCardBorderImage = "cardBorderImage";

    public HuadiaoHouseJedis(JedisPool jedisPool) {
        this.initialCommentIdGenerator(jedisPool, jedisKeyAnimeId);
    }

    @Override
    public long generateAnimeId() {
        return generateGeneratorId(jedisKeyAnimeId);
    }

    @Override
    public long getAnimeId() {
        return getGeneratorId(jedisKeyAnimeId);
    }

    @Override
    public List<CardBorderImage> getHuadiaoCardBorderImage() {
        Jedis jedis = jedisPool.getResource();
        List<String> cardBorderImageStr = jedis.lrange(jedisKeyCardBorderImage, 0, -1);
        if(cardBorderImageStr.isEmpty()) {
            List<CardBorderImage> cardBorderImageList = huadiaoHouseMapper.selectCardBorderImage();
            for (CardBorderImage cardBorderImage : cardBorderImageList) {
                jedis.lpush(jedisKeyCardBorderImage, JSONUtil.toJsonStr(cardBorderImage));
            }
        }
        List<CardBorderImage> cardBorderImageList = new ArrayList<>();
        for(String cardBorderImage : cardBorderImageStr) {
            cardBorderImageList.add(JSONUtil.toBean(cardBorderImage, CardBorderImage.class));
        }
        jedis.close();
        return cardBorderImageList;
    }
}
