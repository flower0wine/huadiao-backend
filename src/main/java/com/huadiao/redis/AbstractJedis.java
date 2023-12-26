package com.huadiao.redis;

import com.huadiao.mapper.*;
import com.huadiao.redis.impl.FollowFanJedis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public abstract class AbstractJedis {
    @Autowired
    protected JedisPool jedisPool;
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected FollowFanMapper followFanMapper;
    @Autowired
    protected UserSettingsMapper userSettingsMapper;
    @Autowired
    protected UserInfoMapper userInfoMapper;
    @Autowired
    protected NoteMapper noteMapper;
    @Autowired
    protected HomepageMapper homepageMapper;
    @Autowired
    protected NoteOperateMapper noteOperateMapper;
    @Autowired
    protected HuadiaoHouseMapper huadiaoHouseMapper;
    @Autowired
    protected ForumNoteMapper forumNoteMapper;
    @Autowired
    protected SystemMessageMapper systemMessageMapper;

    @Value("${jedis.defaultRedisInitialId}")
    protected int defaultRedisInitialId;

    protected void initialIdGenerator(JedisPool jedisPool, String jedisKeyGeneratorId, int initValue) {
        Jedis jedis = jedisPool.getResource();
        String generatorId = jedis.get(jedisKeyGeneratorId);
        if (generatorId == null) {
            jedis.set(jedisKeyGeneratorId, String.valueOf(initValue));
        }
        jedis.close();
    }

    protected void initialIdGenerator(JedisPool jedisPool, String jedisKeyGeneratorId) {
        initialIdGenerator(jedisPool, jedisKeyGeneratorId, defaultRedisInitialId);
    }

    protected int generateGeneratorId(String jedisKeyGeneratorId) {
        Jedis jedis = jedisPool.getResource();
        int generatorId;
        generatorId = Integer.parseInt(jedis.get(jedisKeyGeneratorId));
        generatorId++;
        jedis.set(jedisKeyGeneratorId, String.valueOf(generatorId));
        jedis.close();
        return generatorId;
    }

    protected int getGeneratorId(String jedisKeyGeneratorId) {
        Jedis jedis = jedisPool.getResource();
        int generatorId = Integer.parseInt(jedis.get(jedisKeyGeneratorId));
        jedis.close();
        return generatorId;
    }

    /**
     * 检查更新条数
     * @param updateCount 执行更新语句后返回结果
     * @return 大于 0 返回 true
     */
    protected boolean checkUpdateCount(Integer updateCount) {
        return updateCount != null && updateCount > 0;
    }

}
