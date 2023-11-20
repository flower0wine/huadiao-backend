package com.huadiao.redis.impl;

import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.UserBaseJedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.tz.CachedDateTimeZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author flowerwine
 * @date 2023 年 10 月 01 日
 */
@Component
@Slf4j
public class UserBaseJedis extends AbstractJedis implements UserBaseJedisUtil {

    private String userUidJedisKey = "uid";

    @Value("${jedis.defaultCheckCodeSaveTime}")
    protected int defaultCheckCodeSaveTime;

    @Value("${huadiao.minUid}")
    private int minUid;

    public UserBaseJedis(JedisPool jedisPool) {
        this.initialIdGenerator(jedisPool, userUidJedisKey, minUid);
    }

    @Override
    public int generateUid() {
        return generateGeneratorId(userUidJedisKey);
    }

    @Override
    public void setCheckCode(String jsessionid, String checkCode) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(jsessionid, defaultCheckCodeSaveTime, checkCode);
            log.debug("保存验证码到 redis 成功, 验证码为: {}, 保存时间: {}s", checkCode, defaultCheckCodeSaveTime);
        } catch (Exception e) {
            log.warn("保存验证码时出现错误, 错误信息: {}", e.getMessage());
        }
    }

    @Override
    public String getCheckCode(String jsessionid) {
        String checkCode = null;
        try (Jedis jedis = jedisPool.getResource()) {
            checkCode = jedis.get(jsessionid);
            log.debug("从 redis 获取验证码成功, 验证码为: {}", checkCode);
        } catch (Exception e) {
            log.debug("从 redis 获取验证码时出现错误, 错误信息: {}", e.getMessage());
        }
        return checkCode;
    }

    @Override
    public void deleteCheckCode(String jsessionid) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(jsessionid);
            log.debug("从 redis 删除验证码成功");
        } catch (Exception e) {
            log.debug("从 redis 删除验证码失败");
        }
    }
}
