package com.huadiao.mapper;

import com.huadiao.entity.dto.userinfodto.UserInfoDto;
import org.apache.ibatis.annotations.Param;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 映射文件, 与用户信息相关
 */
public interface UserInfoMapper {


    /**
     * 新增用户信息, 存在则修改用户信息, 根据 uid
     * @param uid 用户 uid
     * @param nickname 昵称
     * @param canvases 个人简介
     * @param sex 性别
     * @param bornDate 出生日期
     * @param school 学校
     */
    void insertOrUpdateUserInfoByUid(@Param("uid") Integer uid, @Param("nickname") String nickname,
                             @Param("canvases") String canvases, @Param("sex") String sex,
                             @Param("bornDate") String bornDate, @Param("school") String school);

    /**
     * 新增个人主页
     * @param uid 用户 uid
     */
    void insertUserHomepageByUid(@Param("uid") Integer uid);

    /**
     * 根据用户 uid 获取用户信息
     * @param uid 用户 uid
     * @return 返回用户详细信息
     */
    UserInfoDto selectUserInfoByUid(Integer uid);

}
