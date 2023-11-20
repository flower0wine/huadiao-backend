package com.huadiao.mapper;

import com.huadiao.entity.homepage.HomepageInfo;
import org.apache.ibatis.annotations.Param;

/**
 * mapper 映射文件, 关注粉丝相关
 * @author flowerwine
 */
public interface HomepageMapper {

    /**
     * 添加访问记录
     * @param uid 访问的用户 uid
     * @param viewedUid 被访问的用户 uid
     */
    void insertVisitRecordByUid(@Param("uid") Integer uid, @Param("viewedUid") Integer viewedUid);

    /**
     * 根据 uid 获取个人主页部分信息
     * @param uid 用户 uid
     * @return 返回个人主页部分信息
     */
    HomepageInfo selectHomepageInfoByUid(@Param("uid") Integer uid);

    /**
     * 修改用户头像
     * @param uid 用户 uid
     * @param userAvatar 用户头像文件
     * @return 更新条数
     */
    Integer updateUserAvatarByUid(@Param("uid") Integer uid, @Param("userAvatar") String userAvatar);

    /**
     * 修改个人主页背景
     * @param uid 用户 uid
     * @param homepageBackground 个人主页背景文件
     */
    void updateHomepageBackgroundByUid(@Param("uid") Integer uid, @Param("homepageBackground") String homepageBackground);
}
