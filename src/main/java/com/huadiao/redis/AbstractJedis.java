package com.huadiao.redis;

import com.huadiao.mapper.*;
import com.huadiao.redis.impl.FollowFanJedis;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void initialCommentIdGenerator(JedisPool jedisPool, String jedisKeyGeneratorId) {
        Jedis jedis = jedisPool.getResource();
        String generatorId = jedis.get(jedisKeyGeneratorId);
        if (generatorId == null) {
            jedis.set(jedisKeyGeneratorId, String.valueOf(0L));
        }
        jedis.close();
    }

    public long generateGeneratorId(String jedisKeyGeneratorId) {
        Jedis jedis = jedisPool.getResource();
        long generatorId;
        generatorId = Long.parseLong(jedis.get(jedisKeyGeneratorId));
        generatorId++;
        jedis.set(jedisKeyGeneratorId, String.valueOf(generatorId));
        jedis.close();
        return generatorId;
    }

    public long getGeneratorId(String jedisKeyGeneratorId) {
        Jedis jedis = jedisPool.getResource();
        long generatorId = Long.parseLong(jedis.get(jedisKeyGeneratorId));
        jedis.close();
        return generatorId;
    }
}
