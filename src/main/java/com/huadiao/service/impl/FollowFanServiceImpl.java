package com.huadiao.service.impl;

import cn.hutool.core.util.StrUtil;
import com.huadiao.entity.AccountSettings;
import com.huadiao.entity.FollowFan;
import com.huadiao.entity.FollowGroup;
import com.huadiao.entity.dto.followfan.FollowFanBaseInfoDto;
import com.huadiao.mapper.FollowFanMapper;
import com.huadiao.mapper.UserMapper;
import com.huadiao.mapper.UserSettingsMapper;
import com.huadiao.service.AbstractFollowFanService;
import com.huadiao.service.AbstractUserInfoService;
import com.huadiao.service.AbstractUserSettingsService;
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
 * @description 业务层: 关注与粉丝处理实现类
 */
@Slf4j
@Service
public class FollowFanServiceImpl extends AbstractFollowFanService {

    private UserMapper userMapper;
    private FollowFanMapper followFanMapper;
    private UserSettingsMapper userSettingsMapper;
    private JedisPool jedisPool;

    @Autowired
    public FollowFanServiceImpl(UserMapper userMapper, FollowFanMapper followFanMapper, UserSettingsMapper userSettingsMapper, JedisPool jedisPool) {
        this.userMapper = userMapper;
        this.followFanMapper = followFanMapper;
        this.userSettingsMapper = userSettingsMapper;
        this.jedisPool = jedisPool;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addNewFollowGroup(String groupName, Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增关注分组", uid, userId);
        // 检查关注分组是否达到最大限制
        Integer followGroup = followFanMapper.countFollowGroupByUid(uid);
        if(followGroup > MAX_FOLLOW_GROUP_AMOUNT) {
            log.debug("uid, userId 分别为 {}, {} 的用户关注分组已达到最大数量", uid, userId);
            return REACH_MAX_FOLLOW_GROUP_AMOUNT;
        }
        // 检查关注分组名称是否符合要求
        if(groupName == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户未提供关注分组名称", uid, userId);
            return NULL_FOLLOW_GROUP_NAME;
        }
        groupName = groupName.trim();
        if("".equals(groupName)) {
            log.debug("uid, userId 分别为 {}, {} 提供的关注分组名为空或为空格", uid, userId);
            return NULL_FOLLOW_GROUP_NAME;
        }
        if(groupName.length() > MAX_FOLLOW_GROUP_NAME_LENGTH) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的关注分组名过长, groupName: {}", uid, userId, groupName);
            return WRONG_FOLLOW_GROUP_NAME_LENGTH;
        }
        // 获取用户自定义的分组总数
        Integer followGroupCount = followFanMapper.countFollowGroup();
        followFanMapper.insertFollowGroupByUid(uid, followGroupCount + FOLLOW_GROUP_ORIGIN_ID, groupName);
        log.debug("uid, userId 分别为 {}, {} 的用户添加关注分组成功, (groupId: {}, groupName: {})", uid, userId, followGroupCount + FOLLOW_GROUP_ORIGIN_ID, groupName);
        return FOLLOW_GROUP_ADD_SUCCEED;
    }

    @Override
    public List<FollowGroup> getFollowGroup(Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取关注分组", uid, userId);
        List<FollowGroup> followGroupList = followFanMapper.selectFollowGroupByUid(uid);
        log.debug("uid, userId 分别为 {}, {} 的用户获取关注分组成功, followGroup: {}", uid, userId, followGroupList);
        return followGroupList;
    }

    @Override
    public Map<String, Object> getUserFollow(Integer uid, String userId, Integer viewedUid, Integer groupId, Integer begin, Integer page) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的用户的关注信息", uid, userId, viewedUid);
        // uid 最小为 1, 新增用户则增大
        if(viewedUid == null || viewedUid < 1) {
            log.debug("uid, userId 分别为 {}, {} 的用户访问 viewedUid 为 {} 的用户的关注信息, 但该用户不存在", uid, userId, viewedUid);
            Map<String, Object> map = new HashMap<>(2);
            map.put(WRONG_MESSAGE_KEY, NO_EXIST_UID);
            return map;
        }
        // 分组 id 校验
        if(groupId == null || groupId < 1) {
            log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的关注分组 groupId 为 {}, 该分组不存在, 视为查询全部关注", uid, userId, viewedUid, groupId);
            groupId = null;
        }
        // 分页参数校验, 有错误使用默认值
        if(begin == null || page == null || begin < 0 || page < 1) {
            begin = 0;
            page = 20;
        }
        // 20 为单次获取的最大条数
        if(page > 20) {
            page = 20;
        }
        Map<String, Object> map = new HashMap<>(4);
        // 是否是本人
        boolean me = uid.equals(viewedUid);
        // 不是本人需要判断本人是否公开关注信息
        if(!me) {
            AccountSettings accountSettings = AbstractUserSettingsService.getAccountSettings(viewedUid, jedisPool, userSettingsMapper);
            if(!accountSettings.getPublicFollowStatus()) {
                log.debug("viewedUid 为 {} 的用户已设置关注信息不公开", viewedUid);
                map.put(PRIVATE_SETTINGS_KEY, PRIVATE_USER_INFO);
                return map;
            }
        }
        // 获取用户关注信息
        List<FollowFan> followFan = followFanMapper.selectUserFollowByUid(viewedUid, groupId, begin, page);
        log.debug("uid, userId 分别为 {}, {} 的用户获取 uid 为 {} 的用户的关注信息成功, (followFan: {}, me: {})", uid, userId, viewedUid, followFan, me);
        map.put("follow", followFan);
        map.put("me", me);
        return map;
    }

    @Override
    public Map<String, Object> getUserFan(Integer uid, String userId, Integer viewedUid, Integer begin, Integer page) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的用户的粉丝信息", uid, userId, viewedUid);
        String viewedUserId = userMapper.selectUserIdByUid(viewedUid);
        // 判断被访问的用户是否存在
        if(viewedUserId == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户访问 viewedUid 为 {} 的用户的粉丝信息, 但该用户不存在", uid, userId, viewedUid);
            Map<String, Object> map = new HashMap<>(2);
            map.put(WRONG_MESSAGE_KEY, NO_EXIST_UID);
            return map;
        }

        // 是否是本人
        boolean me = uid.equals(viewedUid);
        // 不是本人需要判断本人是否公开粉丝信息
        if(!me) {
            AccountSettings accountSettings = AbstractUserSettingsService.getAccountSettings(viewedUid, jedisPool, userSettingsMapper);
            if(!accountSettings.getPublicFanStatus()) {
                log.debug("viewedUid 为 {} 的用户已设置粉丝信息不公开", viewedUid);
                Map<String, Object> map = new HashMap<>(2);
                map.put(PRIVATE_SETTINGS_KEY, PRIVATE_USER_INFO);
                return map;
            }
        }

        // 校验分页
        if(begin == null || page == null || begin < 0 || page < 1) {
            begin = 0;
            page = 20;
        }
        if(page > 20) {
            page = 20;
        }
        Map<String, Object> map = new HashMap<>(4);
        // 获取用户粉丝信息
        List<FollowFan> followFan = followFanMapper.selectUserFanByUid(viewedUid, begin, page);
        log.debug("uid, userId 分别为 {}, {} 的用户获取 uid 为 {} 的用户的粉丝信息成功, (followFan: {}, me: {})", uid, userId, viewedUid, followFan, me);
        map.put("fan", followFan);
        map.put("me", me);
        return map;
    }

    @Override
    public FollowFanBaseInfoDto getUserFollowFanInfo(Integer uid, String userId, Integer viewedUid) {
        FollowFanBaseInfoDto followFanBaseInfoDto = new FollowFanBaseInfoDto();
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的用户的关注和粉丝数量", uid, userId, viewedUid);
        List<Integer> list = followFanMapper.countFollowAndFansByUid(viewedUid);
        if(list.size() != 0) {
            followFanBaseInfoDto.setFollowCount(list.get(FOLLOW_INDEX));
            followFanBaseInfoDto.setFanCount(FAN_INDEX);
            followFanBaseInfoDto.setUid(viewedUid);
        }
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取 uid 为 {} 的用户的关注和粉丝数量, followFanBaseInfoDto: {}", uid, userId, viewedUid, followFanBaseInfoDto);
        return followFanBaseInfoDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String buildRelationBetweenBoth(Integer uid, String fanUserId, Integer fanUid, Integer groupId) {
        // 注意：uid 不是当前用户, fanUid 才是当前用户
        log.debug("uid, userId 分别为 {}, {} 的用户尝试与 uid 为 {} 的用户建立关系, 并将其加入关注分组 groupId: {}", fanUid, fanUserId, uid, groupId);
        // 判断用户是否存在
        String userId = userMapper.selectUserIdByUid(uid);
        if(userId == null) {
            log.debug("uid, userId 为 {}, {} 的用户提供了一个错误的 uid: {} 并试图与其建立关系, 错误原因：该用户不存在", fanUid, fanUserId, uid);
            return NO_EXIST_UID;
        }
        log.debug("uid 为 {} 的用户存在", uid);
        // 如果没有指定关注分组, 则默认为默认分组, groupId = 1
        if(groupId == null) {
            groupId = 1;
            log.debug("用户提供了 groupId 为 null, 默认为默认分组, groupId 为 1");
        } else {
            if(groupId < 1) {
                log.debug("uid, userId 为 {}, {} 的用户提供了一个错误的 groupId: {} , 错误原因：关注分组不存在", fanUid, fanUserId, groupId);
                return NO_EXIST_GROUP_ID;
            }
            // 检查分组是否存在
            FollowGroup followGroup = followFanMapper.selectFollowGroupByUidAndGroupId(fanUid, groupId);
            if(followGroup == null) {
                log.debug("uid, userId 为 {}, {} 的用户提供了一个错误的 groupId: {} , 错误原因：关注分组不存在", fanUid, fanUserId, groupId);
                return NO_EXIST_GROUP_ID;
            }
            log.debug("用户提供的关注分组 groupId: {} 存在", groupId);
        }
        // 获取用户关注数量
        Integer follow = followFanMapper.countFollowByUid(fanUid);
        if(follow > MAX_FOLLOW_AMOUNT) {
            log.debug("uid, userId 分别为 {}, {} 的用户已达到最大关注数量", fanUid, fanUserId);
            return REACH_MAX_FOLLOW_AMOUNT;
        }
        log.debug("未达到最大关注数量, 并且通过参数校验, 分别为 uid: {}, groupId: {}", uid, groupId);
        followFanMapper.insertRelationByBothUid(uid, fanUid, groupId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功与 uid 为 {} 的用户建立关系, 并将其加入关注分组 groupId: {}", fanUid, fanUserId, uid, groupId);
        return BUILD_RELATION_SUCCEED;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelBuildRelationBetweenBoth(Integer uid, Integer fanUid, String fanUserId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试解除与 uid 为 {} 的用户的关系", fanUid, fanUserId, uid);
        followFanMapper.deleteRelationByBothUid(uid, fanUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功解除与 uid 为 {} 的用户的关系", fanUid, fanUserId, uid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFan(Integer uid, String userId, Integer fanUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试移除 uid 为 {} 的粉丝", uid, userId, fanUid);
        followFanMapper.deleteFanByUid(uid, fanUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功移除 uid 为 {} 的粉丝", uid, userId, fanUid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveFollowGroup(Integer uid, Integer fanUid, String fanUserId, Integer groupId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试将 uid 为 {} 的用户移动至 groupId 为 {} 的关注分组中", fanUid, fanUserId, uid, groupId);
        followFanMapper.moveFollowGroup(uid, fanUid, groupId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功将 uid 为 {} 的用户移动至 groupId 为 {} 的关注分组中", fanUid, fanUserId, uid, groupId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyGroupName(Integer uid, String userId, String groupName, Integer groupId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试修改关注分组 groupId 为 {} 的名称, 提供的名称为 {}", uid, userId, groupId, groupName);
        followFanMapper.modifyFollowGroupNameByUid(uid, groupName, groupId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功修改关注分组 groupId 为 {} 的名称, 提供的名称为 {}", uid, userId, groupId, groupName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFollowGroup(Integer uid, String userId, Integer groupId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除关注分组 groupId 为 {}", uid, userId, groupId);
        followFanMapper.deleteFollowGroupByUid(uid, groupId);
        log.debug("uid, userId 分别为 {}, {} 的用户尝试成功关注分组 groupId 为 {}", uid, userId, groupId);
    }
}
