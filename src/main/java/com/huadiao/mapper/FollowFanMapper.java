package com.huadiao.mapper;

import com.huadiao.entity.followfan.FollowFan;
import com.huadiao.entity.followfan.FollowGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mapper 映射文件, 关注粉丝相关
 *
 * @author flowerwine
 */
public interface FollowFanMapper {

    /**
     * 获取用户关注的数量
     *
     * @param uid 用户 uid
     * @return 返回关注和粉丝数量
     */
    List<Integer> countFollowAndFansByUid(@Param("uid") Integer uid);

    /**
     * 获取关注分组的数量
     *
     * @param uid 用户 uid
     * @return 返回关注分组数量
     */
    Integer countFollowGroupByUid(@Param("uid") Integer uid);

    /**
     * 获取关注者 uid
     * @param uid 用户 uid
     * @param groupId 关注分组 id
     * @return 返回关注者 uid
     */
    List<Integer> selectFollowerUidByUid(@Param("uid") Integer uid, @Param("groupId") Integer groupId);

    /**
     * 复制到其他分组
     *
     * @param uid     用户 uid
     * @param groupId 关注分组 id
     * @param list    被关注的人的 uid
     */
    void insertFollowToOtherGroup(@Param("uid") Integer uid, @Param("groupId") Integer groupId, @Param("list") List<Integer> list);

    /**
     * 新增两人的关系
     *
     * @param uid     用户 uid
     * @param fanUid  粉丝的 uid
     * @param groupId 关注分组 id
     */
    void insertRelationByBothUid(@Param("uid") Integer uid, @Param("fanUid") Integer fanUid, @Param("groupId") Integer groupId);

    /**
     * 删除两人关系, 真实删除
     *
     * @param uid     用户 uid
     * @param groupId 分组 id
     * @param list    被关注者 uid
     */
    void deleteRelation(@Param("uid") Integer uid, @Param("groupId") Integer groupId, @Param("list") List<Integer> list);

    /**
     * 根据两者的 uid 取消两人的关系
     *
     * @param uid    用户 uid
     * @param fanUid 关注者的 uid
     */
    void deleteRelationByBothUid(@Param("uid") Integer uid, @Param("fanUid") Integer fanUid);

    /**
     * 根据 uid 获取用户关注分组信息
     *
     * @param uid 用户 uid
     * @return 返回用户关注分组
     */
    List<FollowGroup> selectFollowGroupByUid(@Param("uid") Integer uid);

    /**
     * 根据分组 id 获取关注数量
     *
     * @param uid     关注者
     * @param groupId 分组 id
     * @return 返回该用户该分组关注数量
     */
    Integer countFollowByGroupId(@Param("uid") Integer uid, @Param("groupId") Integer groupId);

    /**
     * 根据 uid 新增用户关注分组
     *
     * @param uid       用户 uid
     * @param groupId   分组 id
     * @param groupName 分组名称
     */
    void insertFollowGroupByUid(@Param("uid") Integer uid, @Param("groupId") Integer groupId, @Param("groupName") String groupName);

    /**
     * 根据 uid 获取用户关注
     *
     * @param uid     用户 uid
     * @param groupId 分组 id
     * @param offset  偏移量
     * @param row     行数
     * @return 用户关注
     */
    List<FollowFan> selectUserFollowByUid(@Param("uid") Integer uid, @Param("groupId") Integer groupId, @Param("offset") Integer offset,
                                          @Param("row") Integer row);

    /**
     * 根据 uid 获取用户粉丝
     *
     * @param uid    用户 uid
     * @param offset 偏移量
     * @param row    行数
     * @return 用户粉丝
     */
    List<FollowFan> selectUserFanByUid(@Param("uid") Integer uid, @Param("offset") Integer offset, @Param("row") Integer row);

    /**
     * 根据用户 uid 修改关注分组名称
     *
     * @param uid       用户 uid
     * @param groupName 修改后的关注分组名称
     * @param groupId   要修改名称的关注分组
     */
    void modifyFollowGroupNameByUid(@Param("uid") Integer uid, @Param("groupName") String groupName, @Param("groupId") Integer groupId);

    /**
     * 删除关注分组
     *
     * @param uid     用户 uid
     * @param groupId 关注分组 id
     */
    void deleteFollowGroupByUid(@Param("uid") Integer uid, @Param("groupId") Integer groupId);

    /**
     * 获取两人的关系, 如果返回集合为个数为 0 则为互不关注, 返回集合个数为 1,
     * 并且值为 1 则为 uid 被 otherUid 关注, 为 2 则反之, 集合个数为 2 则为相互关注
     *
     * @param uid      当前用户 uid
     * @param otherUid 其他人 uid
     * @return 返回关系集合
     */
    List<Integer> selectRelationByBothUid(@Param("uid") Integer uid, @Param("otherUid") Integer otherUid);

}