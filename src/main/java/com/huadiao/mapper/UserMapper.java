package com.huadiao.mapper;

import com.huadiao.entity.dto.UserOAuthDto;
import com.huadiao.entity.dto.user.UserBaseDto;
import com.huadiao.entity.dto.user.UserShareDto;
import org.apache.ibatis.annotations.Param;

/**
 * mapper 映射文件, 与用户账号相关
 * @author flowerwine
 */
public interface UserMapper {

    /**
     * 新增第三方用户
     * @param userOAuthDto 用户数据
     */
    void insertOauthUser(@Param("user") UserOAuthDto userOAuthDto);

    /**
     * 计数所有的用户数量
     * @return 返回用户表中的用户数量
     */
    Integer countAllUser();

    /**
     * 通过用户名和密码来获取用户
     * @param username 用户名
     * @param password 密码
     * @return 返回查找的用户
     */
    UserBaseDto selectUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 加入花凋新用户, 用户注册
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param username 用户名
     * @param password 用户密码
     */
    void insertNewHuadiaoUser(@Param("uid") Integer uid, @Param("userId") String userId, @Param("username") String username, @Param("password") String password);

    /**
     * 更新最近一次登录时间
     * @param uid 用户 uid
     */
    void updateUserLatestLoginTime(@Param("uid") Integer uid);

    /**
     * 根据用户 userId 查找 uid
     * @param userId 用户 userId
     * @return 返回用户 uid
     */
    Integer selectUidByUserId(@Param("userId") String userId);

    /**
     * 根据 oauthId 和 oauthType 查找用户是否存在
     * @param oauthId 第三方用户 id
     * @param oauthType 第三方用户类型
     * @return 查找不到返回 null
     */
    UserShareDto selectOAuthUserExist(@Param("oauthId") Integer oauthId, @Param("oauthType") Integer oauthType);

    /**
     * 根据用户 uid 查找 userId
     * @param uid 用户 uid
     * @return 返回用户 userId
     */
    String selectUserIdByUid(@Param("uid") Integer uid);

    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return 返回查找到的用户
     */
    UserBaseDto selectUserByUsername(@Param("username") String username);

    /**
     * 通过 uid 获取用户共享信息
     * @param uid 用户 uid
     * @return 返回共享信息
     */
    UserShareDto selectUserShareDtoByUid(@Param("uid") Integer uid);
}