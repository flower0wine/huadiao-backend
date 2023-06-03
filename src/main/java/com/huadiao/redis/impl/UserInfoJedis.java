package com.huadiao.redis.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.entity.dto.userdto.UserShareDto;
import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.UserInfoJedisUtil;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Component
public class UserInfoJedis extends AbstractJedis implements UserInfoJedisUtil {

    /**
     * redis 存储的键名, 格式为 huadiao.userInfo.uid, uid 为数字,
     * 这里使用占位符 {} 取代
     */
    protected String redisKeyUserInfo = "huadiao.userInfo.{}";

    @Override
    public void modifyUserInfoByUid(Integer uid) {

    }

    @Override
    public UserAbstractDto getUserInfoByUid(Integer uid) {
        Jedis jedis = jedisPool.getResource();
        String jedisKey = StrUtil.format(redisKeyUserInfo, uid);
        String userInfo = jedis.get(jedisKey);
        if (userInfo != null) {
            jedisPool.returnResource(jedis);
            return JSONUtil.toBean(userInfo, UserAbstractDto.class);
        } else {
            // 获取并填充用户信息
            UserShareDto userShareDto = userMapper.selectUserShareDtoByUid(uid);
            // 用户关注与粉丝
            List<Integer> followFans = followFanMapper.countFollowAndFansByUid(uid);
            UserAbstractDto userAbstractDto = UserAbstractDto.loadUserAbstractInfo(userShareDto, followFans);
            jedis.set(jedisKey, JSONUtil.toJsonStr(userAbstractDto));
            jedisPool.returnResource(jedis);
            return userAbstractDto;
        }
    }
}
