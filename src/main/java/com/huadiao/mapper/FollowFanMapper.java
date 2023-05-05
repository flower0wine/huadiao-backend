package com.huadiao.mapper;

import com.huadiao.entity.FollowFan;
import com.huadiao.entity.FollowGroup;
import com.huadiao.entity.dto.followfan.FollowFanBaseInfoDto;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * mapper 映射文件, 关注粉丝相关
 * @author flowerwine
 */
public interface FollowFanMapper {

    /**
     * 获取关注分组个数
     * @return 返回关注分组个数
     */
    Integer countFollowGroup();

    /**
     * 获取用户关注的数量
     * @param uid 用户 uid
     * @return 返回关注和粉丝数量
     */
    List<Integer> countFollowAndFansByUid(@Param("uid") Integer uid);

    /**
     * 根据 uid 获取关注数量
     * @param uid 用户 uid
     * @return 返回用户关注数量
     */
    Integer countFollowByUid(@Param("uid") Integer uid);

    /**
     * 根据 uid 计算关注分组数量
     * @param uid 用户 uid
     * @return 返回关注分组数量
     */
    Integer countFollowGroupByUid(@Param("uid") Integer uid);

    /**
     * 新增两人的关系
     * @param uid 用户 uid
     * @param fanUid 粉丝的 uid
     */
    void insertRelationByBothUid(@Param("uid") Integer uid, @Param("fanUid") Integer fanUid);

    /**
     * 根据两者的 uid 取消两人的关系
     * @param uid 用户 uid
     * @param fanUid 关注者的 uid
     */
    void deleteRelationByBothUid(@Param("uid") Integer uid, @Param("fanUid") Integer fanUid);

    /**
     * 根据 uid 获取用户关注分组信息
     * @param uid 用户 uid
     * @return 返回用户关注分组
     */
    List<FollowGroup> selectFollowGroupByUid(@Param("uid") Integer uid);

    /**
     * 根据 uid 新增用户关注分组
     * @param uid 用户 uid
     * @param groupId 分组 id
     * @param groupName 分组名称
     */
    void insertFollowGroupByUid(@Param("uid") Integer uid, @Param("groupId") Integer groupId, @Param("groupName") String groupName);

    /**
     * 根据 uid 获取用户关注
     * @param uid 用户 uid
     * @return 用户关注
     * @param groupId 分组 id
     * @param begin 起始分页
     * @param page 查询页数
     */
    List<FollowFan> selectUserFollowByUid(@Param("uid") Integer uid, @Param("groupId") Integer groupId, @Param("begin") Integer begin, @Param("page") Integer page);

    /**
     * 根据 uid 获取用户粉丝
     * @param uid 用户 uid
     * @param begin 开始分页
     * @param page 单页数据条数
     * @return 用户粉丝
     */
    List<FollowFan> selectUserFanByUid(@Param("uid") Integer uid, @Param("begin") Integer begin, @Param("page") Integer page);

    /**
     * 根据 uid 和 groupId 来计算该分组的关注人数
     * @param uid 用户 uid
     * @param groupId 分组 id
     * @return 返回关注分组人数
     */
    Integer countFollowGroupByUidAndGroupId(@Param("uid") Integer uid, @Param("groupId") Integer groupId);

    /**
     * 根据 uid 和 关注分组 id 来查询关注分组
     * @param uid 用户 uid
     * @param groupId 分组 id
     * @return 返回关注分组
     */
    FollowGroup selectFollowGroupByUidAndGroupId(@Param("uid") Integer uid, @Param("groupId") Integer groupId);

    /**
     * 删除粉丝, 删除粉丝是被关注的人才能做的
     * @param uid 被关注的人
     * @param fanUid 关注者
     */
    void deleteFanByUid(@Param("uid") Integer uid, @Param("fanUid") Integer fanUid);

    /**
     * 修改关注的人的分组
     * @param uid 被关注者
     * @param fanUid 关注者
     * @param groupId 关注分组 id
     */
    void moveFollowGroup(@Param("uid") Integer uid, @Param("fanUid") Integer fanUid, @Param("groupId") Integer groupId);

    /**
     * 根据用户 uid 修改关注分组名称
     * @param uid 用户 uid
     * @param groupName 修改后的关注分组名称
     * @param groupId 要修改名称的关注分组
     */
    void modifyFollowGroupNameByUid(@Param("uid") Integer uid, @Param("groupName") String groupName, @Param("groupId") Integer groupId);

    /**
     * 删除关注分组
     * @param uid 用户 uid
     * @param groupId 关注分组 id
     */
    void deleteFollowGroupByUid(@Param("uid") Integer uid, @Param("groupId") Integer groupId);
}