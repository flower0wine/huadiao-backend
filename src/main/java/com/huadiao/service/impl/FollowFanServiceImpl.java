package com.huadiao.service.impl;

import com.huadiao.entity.account.AccountSettings;
import com.huadiao.entity.followfan.FollowFan;
import com.huadiao.entity.followfan.FollowGroup;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.followfan.FollowFanBaseInfoDto;
import com.huadiao.mapper.FollowFanMapper;
import com.huadiao.mapper.UserMapper;
import com.huadiao.mapper.UserSettingsMapper;
import com.huadiao.service.AbstractFollowFanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Autowired
    public FollowFanServiceImpl(UserMapper userMapper, FollowFanMapper followFanMapper, UserSettingsMapper userSettingsMapper) {
        this.userMapper = userMapper;
        this.followFanMapper = followFanMapper;
        this.userSettingsMapper = userSettingsMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> addNewFollowGroup(Integer uid, String userId, String groupName) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增关注分组", uid, userId);
        // 检查关注分组是否达到最大限制
        Integer followGroup = followFanMapper.countFollowGroupByUid(uid);
        if (followGroup > followGroupMaxCount) {
            log.debug("uid, userId 分别为 {}, {} 的用户关注分组已达到最大数量", uid, userId);
            return Result.exceedLimit();
        }
        // 检查关注分组名称是否符合要求
        if (groupName == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户未提供关注分组名称", uid, userId);
            return Result.blankParam();
        }
        groupName = groupName.trim();
        if ("".equals(groupName)) {
            log.debug("uid, userId 分别为 {}, {} 提供的关注分组名为空或为空格", uid, userId);
            return Result.blankParam();
        }
        if (!(followGroupNameMinLength <= groupName.length() && groupName.length() <= followGroupNameMaxLength)) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的关注分组名不符合要求, groupName: {}", uid, userId, groupName);
            return Result.errorParam();
        }
        int followGroupId = followFanJedisUtil.generateFollowGroupId();
        followFanMapper.insertFollowGroupByUid(uid, followGroupId, groupName);
        Map<String, Integer> map = new HashMap<>(2);
        map.put("followGroupId", followGroupId);
        log.debug("uid, userId 分别为 {}, {} 的用户添加关注分组成功, (groupId: {}, groupName: {})", uid, userId, followGroupId, groupName);
        return Result.ok(map);
    }

    private Result<?> checkPublicFollowStatus(Integer uid, String userId, Integer viewedUid) {
        boolean me = uid.equals(viewedUid);
        if (!me) {
            AccountSettings accountSettings = userSettingJedisUtil.getAccountSettings(viewedUid);
            if (!accountSettings.getPublicFollowStatus()) {
                log.debug("uid, userId 分别为 {}, {} 的用户未公开关注分组, 无法获取关注分组", uid, userId);
                return Result.notAllowed();
            }
        }
        return Result.ok(null);
    }

    @Override
    public Result<?> getFollowGroup(Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取关注分组", uid, userId);
        List<FollowGroup> followGroupList = followFanMapper.selectFollowGroupByUid(uid);
        followGroupList.forEach((item) -> item.setAllowOperate(true));
        // 获取默认分组的关注数量
        Integer defaultFollowGroupCount = followFanMapper.countFollowByGroupId(uid, defaultFollowGroupId);
        FollowGroup defaultFollowGroup = getDefaultFollowGroup();
        defaultFollowGroup.setCount(defaultFollowGroupCount);
        followGroupList.add(0, defaultFollowGroup);
        log.debug("添加关注默认分组成功");
        log.debug("uid, userId 分别为 {}, {} 的用户获取关注分组成功", uid, userId);
        return Result.ok(followGroupList);
    }

    private abstract class AbstractGetFollowFanInfo {
        private Result<?> getFollowFanInfo(Integer uid, String userId, Integer viewedUid, Integer groupId, Integer offset, Integer row) {
            // uid 最小为 1, 新增用户则增大
            if (viewedUid == null || viewedUid < 1) {
                log.debug("uid, userId 分别为 {}, {} 的用户访问 viewedUid 为 {} 的用户不存在", uid, userId, viewedUid);
                return Result.pageNotExist();
            }

            return checkPageAndSize(offset, row, (page, size) -> {
                Result<?> result = checkPublicFollowStatus(uid, userId, viewedUid);
                if(!result.succeed()) {
                    return result;
                }
                boolean me = uid.equals(viewedUid);
                Integer groupIdTemp;
                // 不是本人只能查看全部关注, 或者提供的 group 为 null
                if(!me) {
                    groupIdTemp = null;
                } else {
                    groupIdTemp = Integer.valueOf(defaultAllGroupId).equals(groupId) ? null : groupId;
                }
                return afterGetFollowFanInfo(uid, userId, viewedUid, groupIdTemp, page, size);
            });
        }

        protected Result<?> afterGetFollowFanInfo(Integer uid, String userId, Integer viewedUid, Integer groupId, Integer offset, Integer row) {
            return Result.ok(null);
        }
    }

    private class GetFollowInfo extends AbstractGetFollowFanInfo {
        @Override
        protected Result<?> afterGetFollowFanInfo(Integer uid, String userId, Integer viewedUid, Integer groupId, Integer offset, Integer row) {
            // 获取用户关注信息
            List<FollowFan> followFan = followFanMapper.selectUserFollowByUid(viewedUid, groupId, offset, row);
            if(followFan.size() == 0) {
                return Result.emptyData();
            }
            Map<String, Object> map = new HashMap<>(4);

            map.put("offset", followFan.size());
            map.put("follow", followFan);
            return Result.ok(map);
        }
    }

    private class GetFanInfo extends AbstractGetFollowFanInfo {
        @Override
        protected Result<?> afterGetFollowFanInfo(Integer uid, String userId, Integer viewedUid, Integer groupId, Integer offset, Integer row) {
            // 获取用户粉丝信息
            List<FollowFan> followFan = followFanMapper.selectUserFanByUid(viewedUid, offset, row);
            if(followFan.size() == 0) {
                return Result.emptyData();
            }

            return Result.ok(followFan);
        }
    }

    private AbstractGetFollowFanInfo getFollowInfo = new GetFollowInfo();

    private AbstractGetFollowFanInfo getFanInfo = new GetFanInfo();

    @Override
    public Result<?> getUserFollow(Integer uid, String userId, Integer viewedUid, Integer groupId, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的用户的关注信息", uid, userId, viewedUid);
        Result<?> result = getFollowInfo.getFollowFanInfo(uid, userId, viewedUid, groupId, offset, row);
        if(!result.succeed()) {
            return result;
        }
        log.debug("uid, userId 分别为 {}, {} 的用户获取 uid 为 {} 的用户的关注信息成功, followFan: {}", uid, userId, viewedUid, result.getData());
        return result;
    }

    @Override
    public Result<?> getUserFan(Integer uid, String userId, Integer viewedUid, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的用户的粉丝信息, offset: {}, row: {}", uid, userId, viewedUid, offset, row);
        Result<?> result = getFanInfo.getFollowFanInfo(uid, userId, viewedUid, null, offset, row);
        if(!result.succeed()) {
            return result;
        }
        log.debug("uid, userId 分别为 {}, {} 的用户获取 uid 为 {} 的用户的粉丝信息成功", uid, userId, viewedUid);
        return result;
    }

    @Override
    public Result<?> getUserFollowFanInfo(Integer uid, String userId, Integer viewedUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的用户的关注和粉丝数量", uid, userId, viewedUid);
        FollowFanBaseInfoDto followFanBaseInfoDto = new FollowFanBaseInfoDto();
        List<Integer> list = followFanMapper.countFollowAndFansByUid(viewedUid);
        if (list.size() != 0) {
            followFanBaseInfoDto.setFollowCount(list.get(FOLLOW_INDEX));
            followFanBaseInfoDto.setFanCount(list.get(FAN_INDEX));
            followFanBaseInfoDto.setUid(viewedUid);
        }
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取 uid 为 {} 的用户的关注和粉丝数量, followFanBaseInfoDto: {}", uid, userId, viewedUid, followFanBaseInfoDto);
        return Result.ok(followFanBaseInfoDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> buildRelationBetweenBoth(Integer uid, String userId, Integer followUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试与 uid 为 {} 的用户建立关系", uid, userId, followUid);
        if(uid.equals(followUid)) {
            log.debug("uid, userId 分别为 {}, {} 的用户试图与自身建立关系", uid, userId);
            return Result.notAllowed();
        }
        // 判断用户是否存在
        String followUserId = userMapper.selectUserIdByUid(uid);
        if (followUserId == null) {
            log.debug("uid, userId 为 {}, {} 的用户提供了一个错误的 uid: {} 并试图与其建立关系, 但该用户不存在", uid, userId, uid);
            return Result.notExist();
        }
        // 获取用户关注数量
        Integer follow = followFanMapper.countFollowGroupByUid(uid);
        if (follow > followGroupMaxCount) {
            log.debug("uid, userId 分别为 {}, {} 的用户已达到最大关注数量", uid, userId);
            return Result.exceedLimit();
        }
        followFanMapper.insertRelationByBothUid(followUid, uid, defaultFollowGroupId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功与 uid 为 {} 的用户建立关系, 默认加入默认分组", uid, userId, followUid);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> cancelBuildRelationBetweenBoth(Integer uid, String userId, Integer followUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试解除与 uid 为 {} 的用户的关系", uid, userId, followUid);
        followFanMapper.deleteRelationByBothUid(followUid, uid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功解除与 uid 为 {} 的用户的关系", uid, userId, followUid);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> removeFan(Integer uid, String userId, Integer fanUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试移除 uid 为 {} 的粉丝", uid, userId, fanUid);
        followFanMapper.deleteRelationByBothUid(uid, fanUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功移除 uid 为 {} 的粉丝", uid, userId, fanUid);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> modifyGroupName(Integer uid, String userId, String groupName, Integer groupId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试修改关注分组 groupId 为 {} 的名称, 提供的名称为 {}", uid, userId, groupId, groupName);
        Integer count = followFanMapper.modifyFollowGroupNameByUid(uid, groupName, groupId);

        log.debug("uid, userId 分别为 {}, {} 的用户修改关注分组 groupId 为 {} 的名称成功, 修改了 {} 条记录", uid, userId, groupId, count);
        if (count == null || count == 0) {
            return Result.notExist();
        } else {
            return Result.ok("修改成功");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteFollowGroup(Integer uid, String userId, Integer groupId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除关注分组 groupId 为 {}", uid, userId, groupId);
        List<Integer> followerUidList = followFanMapper.selectFollowerUidByUid(uid, groupId);
        followFanMapper.deleteRelation(uid, groupId, followerUidList);
        followFanMapper.deleteFollowGroupByUid(uid, groupId);
        log.debug("uid, userId 分别为 {}, {} 的用户尝试成功关注分组 groupId 为 {}", uid, userId, groupId);
        return Result.ok(null);
    }

    private Result<?> checkChangeFollowParam(Integer uid, String userId, Integer srcGroupId, Integer destGroupId, List<Integer> followerList) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试复制关注从 srcGroupId 为 {} 到 destGroupId 为 {}", uid, userId, srcGroupId, destGroupId);
        if(srcGroupId == null || destGroupId == null ||  followerList == null || followerList.size() == 0) {
            log.debug("uid, userId 分别为 {}, {} 的用户在复制关注时提供的参数存在问题", uid, userId);
            return Result.blankParam();
        }
        if(srcGroupId.equals(destGroupId)) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的参数 srcGroupId 为 {} 与 destGroupId 为 {} 相同, 无法复制关注", uid, userId, srcGroupId, destGroupId);
            return Result.errorParam();
        }
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> copyFollow(Integer uid, String userId, Integer srcGroupId, Integer destGroupId, List<Integer> followerList) {
        Result<?> result = checkChangeFollowParam(uid, userId, srcGroupId, destGroupId, followerList);
        if(!result.succeed()) {
            return result;
        }
        followFanMapper.insertFollowToOtherGroup(uid, destGroupId, followerList);
        log.debug("uid, userId 分别为 {}, {} 的用户成功复制关注从 srcGroupId 为 {} 到 destGroupId 为 {}", uid, userId, srcGroupId, destGroupId);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> moveFollow(Integer uid, String userId, Integer srcGroupId, Integer destGroupId, List<Integer> followerList) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试移动关注从 srcGroupId 为 {} 到 destGroupId 为 {}", uid, userId, srcGroupId, destGroupId);
        Result<?> result = checkChangeFollowParam(uid, userId, srcGroupId, destGroupId, followerList);
        if(!result.succeed()) {
            return result;
        }
        // 这里没有对用户提供的关注者 uid List 进行检查
        followFanMapper.deleteRelation(uid, srcGroupId, followerList);
        followFanMapper.insertFollowToOtherGroup(uid, destGroupId, followerList);
        log.debug("uid, userId 分别为 {}, {} 的用户成功移动关注从 srcGroupId 为 {} 到 destGroupId 为 {}", uid, userId, srcGroupId, destGroupId);
        return Result.ok(null);
    }
}
