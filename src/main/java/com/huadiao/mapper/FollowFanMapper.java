package com.huadiao.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * mapper 映射文件, 关注粉丝相关
 * @author flowerwine
 */
public interface FollowFanMapper {

    /**
     * 获取用户关注的数量
     * @param uid 用户 uid
     * @return 返回关注和粉丝数量
     */
    List<Integer> countFollowAndFansByUid(@Param("uid") Integer uid);

    /**
     * 新增两人的关系
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param fanUid 粉丝的 uid
     * @param fanUserId 粉丝的 id
     */
    void insertRelationByBothUid(@Param("uid") String uid, @Param("userId") String userId,
                                          @Param("fanUid") String fanUid, @Param("fanUserId") String fanUserId);

}