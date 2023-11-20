package com.huadiao.mapper;

import com.huadiao.entity.account.AccountSettings;
import com.huadiao.entity.dto.accountsettings.PublicInfoSettingsDto;
import org.apache.ibatis.annotations.Param;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 映射文件, 与用户账号设置相关
 */
public interface UserSettingsMapper {

    /**
     * 新增用户账号设置
     * @param uid 用户 uid
     * @param settingsArray 要更改的设置
     */
    void insertOrUpdateUserSettingsByUid(@Param("uid") Integer uid, @Param("settingsList") String[] settingsArray);

    /**
     * 根据 uid 获取用户公开信息设置
     *
     * @param uid 用户 uid
     * @return 返回公开信息设置
     */
    PublicInfoSettingsDto selectUserPublicInfoSettingsByUid(@Param("uid") Integer uid);

    /**
     * 根据 uid 获取用户账号设置
     * @param uid 用户 uid
     * @return 返回用户账号设置
     */
    AccountSettings selectAccountSettingsByUid(@Param("uid") Integer uid);

}
