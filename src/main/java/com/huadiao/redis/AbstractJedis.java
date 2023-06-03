package com.huadiao.redis;

import com.huadiao.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    protected NoteStarMapper noteStarMapper;

}
