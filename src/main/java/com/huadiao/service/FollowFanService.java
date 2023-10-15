package com.huadiao.service;

import com.huadiao.entity.FollowFan;
import com.huadiao.entity.FollowGroup;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.followfan.FollowFanBaseInfoDto;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 业务层: 关注与粉丝处理接口
 *
 * @author flowerwine
 */
public interface FollowFanService {

    /**
     * 从数据库获取关注数量时, list 集合索引
     */
    int FOLLOW_INDEX = 0;

    /**
     * 从数据库获取粉丝数量时, list 集合索引
     */
    int FAN_INDEX = 1;

    /**
     * 新增用户关注分组
     * @param groupName 分组名称
     * @param uid 用户 uid
     * @param userId 用户 id
     * @return 返回增加成功与否提示信息
     */
    Result<?> addNewFollowGroup(Integer uid, String userId, String groupName);

    /**
     * 获取用户关注分组
     * @param uid 用户 uid
     * @param userId 用户 id
     * @return 返回关注分组
     */
    Result<?> getFollowGroup(Integer uid, String userId);

    /**
     * 根据 viewedUid 获取用户关注信息
     * @param uid 用户 uid, 访问者
     * @param userId 用户 id
     * @param viewedUid 被访问者
     * @param groupId 关注分组 id
     * @param offset 偏移量
     * @param row 行数
     * @return 返回被访问者关注信息
     */
    Result<?> getUserFollow(Integer uid, String userId, Integer viewedUid, Integer groupId, Integer offset, Integer row);

    /**
     * 根据 viewedUid 获取用户粉丝信息
     * @param uid 用户 uid, 访问者
     * @param userId 用户 id
     * @param viewedUid 被访问者
     * @param begin 开始分页
     * @param page 每页数据条数
     * @return 返回被访问者粉丝信息
     */
    Result<?> getUserFan(Integer uid, String userId, Integer viewedUid, Integer begin, Integer page);

    /**
     * 获取指定 uid 的关注和粉丝信息, 关注数量和粉丝数量
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param viewedUid 被访问的用户 uid
     * @return 返回关注和粉丝信息
     */
    Result<?> getUserFollowFanInfo(Integer uid, String userId, Integer viewedUid);

    /**
     * 建立用户关系
     * @param uid 关注者
     * @param userId 用户 id
     * @param followUid 被关注者 uid
     * @return 返回添加成功或失败提示
     */
    Result<?> buildRelationBetweenBoth(Integer uid, String userId, Integer followUid);

    /**
     * 解除两人的关系
     * @param uid 关注者
     * @param userId 用户 id
     * @param followUid 被关注者 uid
     * @return 返回删除成功或失败提示
     */
    Result<?> cancelBuildRelationBetweenBoth(Integer uid, String userId, Integer followUid);

    /**
     * 移除粉丝
     * @param uid 被关注者 uid
     * @param userId 用户 id
     * @param fanUid 关注者 uid
     * @return 返回移除成功或失败提示
     */
    Result<?> removeFan(Integer uid, String userId, Integer fanUid);

    /**
     * 修改关注分组名称
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param groupName 修改后的关注分组名称
     * @param groupId 关注分组 id
     */
    void modifyGroupName(Integer uid, String userId, String groupName, Integer groupId);

    /**
     * 删除关注分组
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param groupId 关注分组 id
     * @return 返回删除成功或失败提示
     */
    Result<?> deleteFollowGroup(Integer uid, String userId, Integer groupId);

    /**
     * 复制关注到其他分组
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param srcGroupId 原关注分组 id
     * @param destGroupId 目标关注分组 id
     * @param followerList 被关注的用户 uid
     * @return 返回复制过程中的提示
     */
    Result<?> copyFollow(Integer uid, String userId, Integer srcGroupId, Integer destGroupId, List<Integer> followerList);

    /**
     * 复制关注到其他分组
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param srcGroupId 原关注分组 id
     * @param destGroupId 目标关注分组 id
     * @param followerList 被关注的用户 uid
     * @return 返回移动过程中的提示
     */
    Result<?> moveFollow(Integer uid, String userId, Integer srcGroupId, Integer destGroupId, List<Integer> followerList);
}
