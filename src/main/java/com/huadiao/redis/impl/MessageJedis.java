package com.huadiao.redis.impl;

import cn.hutool.json.JSONUtil;
import com.huadiao.entity.message.system.SystemMessage;
import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.MessageJedisUtil;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
@Component
public class MessageJedis extends AbstractJedis implements MessageJedisUtil {

    private String systemMessageIdJedisKey = "systemMessageId";

    private String whisperMessageIdJedisKey = "whisperMessageId";

    /**
     * redis 的有序列表, 键名
     */
    private String listJedisKey = "systemMessageList";

    public MessageJedis(JedisPool jedisPool) {
        initialIdGenerator(jedisPool, systemMessageIdJedisKey);
        initialIdGenerator(jedisPool, whisperMessageIdJedisKey);
    }

    @Override
    public int generateSystemMessageId() {
        return generateGeneratorId(systemMessageIdJedisKey);
    }

    @Override
    public int generateWhisperMessageId() {
        return generateGeneratorId(whisperMessageIdJedisKey);
    }

    @Override
    public List<SystemMessage> getSystemMessage(Integer offset, Integer row) {
        Jedis jedis = jedisPool.getResource();
        List<String> systemMessageStr = jedis.lrange(listJedisKey, offset, offset + row - 1);
        List<SystemMessage> systemMessageList;
        if (systemMessageStr != null && systemMessageStr.size() > 0) {
            systemMessageList = systemMessageStr.stream()
                    .map(item -> JSONUtil.toBean(item, SystemMessage.class))
                    .collect(Collectors.toList());
        } else {
            systemMessageList = systemMessageMapper.selectSystemMessage(offset, row);
            if (systemMessageList.size() > 0) {
                systemMessageList.forEach(item -> jedis.lpush(listJedisKey, JSONUtil.toJsonStr(item)));
            }
        }
        jedis.close();
        return systemMessageList;
    }

    @Override
    public void addSystemMessage(SystemMessage systemMessage, Integer adminId) {
        Jedis jedis = jedisPool.getResource();
        systemMessage.setSendTime(new Date());
        systemMessageMapper.insertSystemMessage(systemMessage.getMessageId(), adminId, systemMessage.getMessageTitle(), systemMessage.getMessageContent(), systemMessage.getForm());
        jedis.lpush(listJedisKey, JSONUtil.toJsonStr(systemMessage));
        jedis.close();
    }
}
