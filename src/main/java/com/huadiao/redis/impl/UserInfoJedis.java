package com.huadiao.redis.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.entity.dto.userdto.UserShareDto;
import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.UserInfoJedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Slf4j
@Component
public class UserInfoJedis extends AbstractJedis implements UserInfoJedisUtil {

    /**
     * redis 存储的键名, 格式为 huadiao.userInfo.uid, uid 为数字,
     * 这里使用占位符 {} 取代
     */
    protected String redisKeyUserInfo = "huadiao.userInfo.{}";

    @Override
    public void modifyUserInfoByUid(Integer uid, String nickname, String canvases, String sex, Date bornDate, String school) {
        Jedis jedis = jedisPool.getResource();
        String jedisKey = StrUtil.format(redisKeyUserInfo, uid);
        String userInfo = jedis.get(jedisKey);
        UserAbstractDto userAbstractDto;
        if (userInfo != null) {
            userAbstractDto = JSONUtil.toBean(userInfo, UserAbstractDto.class);
            userAbstractDto.setNickname(nickname);
            // redis 有数据, 先删 redis 再更新数据库, 再将数据写入 redis
            jedis.del(jedisKey);
            userInfoMapper.insertOrUpdateUserInfoByUid(uid, nickname, canvases, sex, bornDate, school);
        } else {
            // redis 无数据, 先更新数据库再获取保存到 redis
            userInfoMapper.insertOrUpdateUserInfoByUid(uid, nickname, canvases, sex, bornDate, school);
            userAbstractDto = getUserAbstractDto(uid);
        }
        jedis.set(jedisKey, JSONUtil.toJsonStr(userAbstractDto));
        jedis.close();
    }

    @Override
    public void modifyUserAvatarByUid(Integer uid, String userAvatar) {
        Jedis jedis = jedisPool.getResource();
        String jedisKey = StrUtil.format(redisKeyUserInfo, uid);
        String userInfo = jedis.get(jedisKey);
        UserAbstractDto userAbstractDto;
        if (userInfo != null) {
            userAbstractDto = JSONUtil.toBean(userInfo, UserAbstractDto.class);
            userAbstractDto.setUserAvatar(userAvatar);
            // redis 有数据, 先删 redis 再更新数据库, 再将数据写入 redis
            jedis.del(jedisKey);
            Integer updateCount = homepageMapper.updateUserAvatarByUid(uid, userAvatar);
            if(!checkUpdateCount(updateCount)) {
                jedis.close();
                return;
            }
        } else {
            // redis 无数据, 先更新数据库再获取保存到 redis
            homepageMapper.updateUserAvatarByUid(uid, userAvatar);
            userAbstractDto = getUserAbstractDto(uid);
        }
        jedis.set(jedisKey, JSONUtil.toJsonStr(userAbstractDto));
        jedis.close();
    }

    private UserAbstractDto getUserAbstractDto(Integer uid) {
        UserShareDto userShareDto = userMapper.selectUserShareDtoByUid(uid);
        List<Integer> followFans = followFanMapper.countFollowAndFansByUid(uid);
        return UserAbstractDto.loadUserAbstractInfo(userShareDto, followFans);
    }

    @Override
    public UserAbstractDto getUserInfoByUid(Integer uid) {
        Jedis jedis = jedisPool.getResource();
        String jedisKey = StrUtil.format(redisKeyUserInfo, uid);
        String userInfo = jedis.get(jedisKey);
        if (userInfo != null) {
            jedis.close();
            return JSONUtil.toBean(userInfo, UserAbstractDto.class);
        } else {
            UserAbstractDto userAbstractDto = getUserAbstractDto(uid);
            jedis.set(jedisKey, JSONUtil.toJsonStr(userAbstractDto));
            jedis.close();
            return userAbstractDto;
        }
    }
}
