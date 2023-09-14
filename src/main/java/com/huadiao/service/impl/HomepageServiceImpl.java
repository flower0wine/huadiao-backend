package com.huadiao.service.impl;

import com.huadiao.entity.AccountSettings;
import com.huadiao.entity.HomepageInfo;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.userdto.UserShareDto;
import com.huadiao.entity.dto.userinfodto.UserInfoDto;
import com.huadiao.mapper.*;
import com.huadiao.redis.UserSettingJedisUtil;
import com.huadiao.service.AbstractFollowFanService;
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

    @Autowired
    public HomepageServiceImpl(UserInfoMapper userInfoMapper, UserMapper userMapper, FollowFanMapper followFanMapper, UserSettingsMapper userSettingsMapper, HomepageMapper homepageMapper) {
        this.userInfoMapper = userInfoMapper;
        this.userMapper = userMapper;
        this.followFanMapper = followFanMapper;
        this.userSettingsMapper = userSettingsMapper;
        this.homepageMapper = homepageMapper;
    }

    @Override
    public Result<?> getHomepageInfo(Integer uid, String userId, Integer viewedUid) {
        // 判断被访问者是否存在
        String viewerUserId = userMapper.selectUserIdByUid(viewedUid);
        if (viewerUserId == null) {
            log.debug("uid, userId 分别为 {}, {} 提供的 uid 为 {} 的个人主页不存在", uid, userId, viewedUid);
            return Result.notExist();
        }

        boolean me = uid.equals(viewedUid);
        // 如果不是本人
        if (!me) {
            AccountSettings accountSettings = userSettingJedisUtil.getAccountSettings(viewedUid);
            // 如果用户选择不公开个人主页信息
            if (!accountSettings.getPublicHomepageStatus()) {
                log.debug("uid 为 {} 的用户不公开个人主页信息", viewedUid);
                return Result.notAllowed();
            }
        }

        log.debug("uid 为 {} 的用户公开个人主页信息", viewedUid);
        // 获取个人主页信息
        List<Integer> followFan = followFanMapper.countFollowAndFansByUid(viewedUid);
        List<Integer> relation = followFanMapper.selectRelationByBothUid(uid, viewedUid);
        UserInfoDto userInfoDto = userInfoMapper.selectUserInfoByUid(viewedUid);
        HomepageInfo homepageInfo = homepageMapper.selectHomepageInfoByUid(viewedUid);

        // 装填参数
        Map<String, Object> map = new HashMap<>(10);
        map.put("followCount", followFan.get(FollowFanService.FOLLOW_INDEX));
        map.put("fanCount", followFan.get(FollowFanService.FAN_INDEX));
        map.put("userInfo", userInfoDto);
        map.put("homepageInfo", homepageInfo);
        map.put("relation", AbstractFollowFanService.judgeRelationBetweenBoth(relation));
        map.put("uid", uid);
        map.put("me", me);

        // 不是本人添加访问记录
        if (!me) {
            insertVisitRecord(uid, userId, viewedUid);
        }
        return Result.ok(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertVisitRecord(Integer uid, String userId, Integer viewedUid) {
        log.debug("尝试为 uid, userId 分别为 {}, {} 的用户的个人主页新增访问记录, 访问者 uid: {}", uid, userId, viewedUid);
        homepageMapper.insertVisitRecordByUid(uid, viewedUid);
        log.debug("为 uid, userId 分别为 {}, {} 的用户的个人主页新增访问记录成功, 访问者 uid: {}", uid, userId, viewedUid);
    }
}
