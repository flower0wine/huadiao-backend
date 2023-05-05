package com.huadiao.service.impl;

import com.huadiao.entity.AccountSettings;
import com.huadiao.entity.HomepageInfo;
import com.huadiao.entity.dto.userdto.UserShareDto;
import com.huadiao.entity.dto.userinfodto.UserInfoDto;
import com.huadiao.mapper.*;
import com.huadiao.service.AbstractHomepageService;
import com.huadiao.service.AbstractUserSettingsService;
import com.huadiao.service.FollowFanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Service
@Slf4j
public class HomepageServiceImpl extends AbstractHomepageService {

    private UserInfoMapper userInfoMapper;
    private UserMapper userMapper;
    private FollowFanMapper followFanMapper;
    private UserSettingsMapper userSettingsMapper;
    private HomepageMapper homepageMapper;
    private JedisPool jedisPool;

    @Autowired
    public HomepageServiceImpl(UserInfoMapper userInfoMapper, UserMapper userMapper, FollowFanMapper followFanMapper, UserSettingsMapper userSettingsMapper, HomepageMapper homepageMapper, JedisPool jedisPool) {
        this.userInfoMapper = userInfoMapper;
        this.userMapper = userMapper;
        this.followFanMapper = followFanMapper;
        this.userSettingsMapper = userSettingsMapper;
        this.homepageMapper = homepageMapper;
        this.jedisPool = jedisPool;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> getHomepageInfo(Integer uid, String userId, Integer viewedUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试访问 uid 为 {} 的用户的个人主页", uid, userId, viewedUid);
        // 判断被访问者是否存在
        String viewerUserId = userMapper.selectUserIdByUid(viewedUid);
        if(viewerUserId == null) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("wrongUid", "");
            return map;
        }
        AccountSettings accountSettings = AbstractUserSettingsService.getAccountSettings(viewedUid, jedisPool, userSettingsMapper);
        // 如果用户选择不公开个人主页信息
        if (!accountSettings.getPublicHomepageStatus()) {
            log.debug("uid 为 {} 的用户不公开个人主页信息", viewedUid);
            Map<String, Object> map = new HashMap<>(2);
            map.put(AbstractUserSettingsService.PRIVATE_SETTINGS_KEY, AbstractUserSettingsService.PRIVATE_USER_INFO);
            return map;
        }
        // 如果用户选择公开个人主页
        else {
            log.debug("uid 为 {} 的用户公开个人主页信息", viewedUid);
            // 获取用户信息
            List<Integer> followFan = followFanMapper.countFollowAndFansByUid(viewedUid);
            UserInfoDto userInfoDto = userInfoMapper.selectUserInfoByUid(viewedUid);
            HomepageInfo homepageInfo = homepageMapper.selectHomepageInfoByUid(viewedUid);
            // 添加访问记录
            if (!uid.equals(viewedUid)) {
                insertVisitRecord(uid, userId, viewedUid);
            }

            Map<String, Object> map = new HashMap<>(10);
            map.put("followCount", followFan.get(FollowFanService.FOLLOW_INDEX));
            map.put("fanCount", followFan.get(FollowFanService.FAN_INDEX));
            map.put("userInfo", userInfoDto);
            map.put("homepageInfo", homepageInfo);
            return map;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertVisitRecord(Integer uid, String userId, Integer viewedUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户的个人主页新增访问记录, 访问者 uid: {}", uid, userId, viewedUid);
        homepageMapper.insertVisitRecordByUid(uid, viewedUid);
    }
}
