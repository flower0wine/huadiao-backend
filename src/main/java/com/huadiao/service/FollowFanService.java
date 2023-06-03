package com.huadiao.service;

import com.huadiao.entity.FollowFan;
import com.huadiao.entity.FollowGroup;
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
     * 用户自定义的关注分组最少为 10, 规定前 10 个是官方定义的关注分组, 用户不可对其进行操作
     */
    int FOLLOW_GROUP_ORIGIN_ID = 10;

    /**
     * 新增用户关注分组
     * @param groupName 分组名称
     * @param uid 用户 uid
     * @param userId 用户 id
     * @return 返回增加成功与否提示信息
     */
    String addNewFollowGroup(String groupName, Integer uid, String userId);

    /**
     * 获取用户关注分组
     * @param uid 用户 uid, 访问者
     * @param userId 用户 id
     * @return 返回关注分组
     */
    List<FollowGroup> getFollowGroup(Integer uid, String userId);

    /**
     * 根据 viewedUid 获取用户关注信息
     * @param uid 用户 uid, 访问者
     * @param userId 用户 id
     * @param viewedUid 被访问者
     * @param groupId 关注分组 id
     * @param begin 开始分页
     * @param page 查询页数
     * @return 返回被访问者关注信息
     */
    Map<String, Object> getUserFollow(Integer uid, String userId, Integer viewedUid, Integer groupId, Integer begin, Integer page);

    /**
     * 根据 viewedUid 获取用户粉丝信息
     * @param uid 用户 uid, 访问者
     * @param userId 用户 id
     * @param viewedUid 被访问者
     * @param begin 开始分页
     * @param page 每页数据条数
     * @return 返回被访问者粉丝信息
     */
    Map<String, Object> getUserFan(Integer uid, String userId, Integer viewedUid, Integer begin, Integer page);

    /**
     * 获取指定 uid 的关注和粉丝信息, 关注数量和粉丝数量
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param viewedUid 被访问的用户 uid
     * @return 返回关注和粉丝信息
     */
    FollowFanBaseInfoDto getUserFollowFanInfo(Integer uid, String userId, Integer viewedUid);

    /**
     * 建立用户关系
     * @param uid 被关注者
     * @param fanUserId 用户 id
     * @param fanUid 关注者
     * @param groupId 分组 id, 分组 id 为 null 则分配到 默认分组
     * @return 返回添加成功或失败提示
     */
    String buildRelationBetweenBoth(Integer uid, String fanUserId, Integer fanUid, Integer groupId);

    /**
     * 解除两人的关系
     * @param uid 被关注折
     * @param fanUid 关注者
     * @param fanUserId 用户 id
     */
    void cancelBuildRelationBetweenBoth(Integer uid, Integer fanUid, String fanUserId);

    /**
     * 移除粉丝
     * @param uid 被关注者 uid
     * @param userId 用户 id
     * @param fanUid 关注者 uid
     */
    void removeFan(Integer uid, String userId, Integer fanUid);

    /**
     * 修改关注的人的分组
     * @param uid 被关注者
     * @param fanUid 关注者
     * @param fanUserId 用户 id
     * @param groupId 分组 id
     */
    void moveFollowGroup(Integer uid, Integer fanUid, String fanUserId, Integer groupId);

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
     */
    void deleteFollowGroup(Integer uid, String userId, Integer groupId);
}
