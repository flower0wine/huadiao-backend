package com.huadiao.service.impl;

import com.huadiao.entity.AccountSettings;
import com.huadiao.entity.dto.accountsettings.MessageSettingsDto;
import com.huadiao.entity.dto.accountsettings.PublicInfoSettingsDto;
import com.huadiao.mapper.UserSettingsMapper;
import com.huadiao.service.AbstractUserSettingsService;
import com.huadiao.service.UserSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Service
@Slf4j
public class UserSettingsServiceImpl extends AbstractUserSettingsService {

    private UserSettingsMapper userSettingsMapper;

    @Autowired
    public UserSettingsServiceImpl(UserSettingsMapper userSettingsMapper) {
        this.userSettingsMapper = userSettingsMapper;
    }

    @Override
    public AccountSettings getUserSettings(Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取自己的账号设置", uid, userId);
        AccountSettings accountSettings = userSettingsMapper.selectAccountSettingsByUid(uid);
        log.debug("uid, userId 分别为 {}, {} 的用户获取账号设置成功", uid, userId);
        return accountSettings;
    }

    @Override
    public String modifyAccountSettings(Integer uid, String userId, Set<String> settingsSet) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试更新自己的账号设置, settingsList: {}", uid, userId, settingsSet);
        String[] settingsArr = settingsSet.toArray(new String[0]);
        String settingReplaceRegex = "([a-z])([A-Z])";
        String replace = "$1_$2";
        // 格式转换, 例如将 publicStarStatus --> public_star_status
        for(int index = 0; index < settingsArr.length; index++) {
            String str = settingsArr[index];
            // 如果不是全英文返回更新失败
            if(!pattern.matcher(str).find()) {
                return ACCOUNT_SETTING_UPDATE_FAIL;
            }
            settingsArr[index] = str.replaceAll(settingReplaceRegex, replace).toLowerCase();
        }
        userSettingsMapper.insertOrUpdateUserSettingsByUid(uid, settingsArr);
        log.debug("uid, userId 分别为 {}, {} 的用户更新账号设置成功, settingsList: {}", uid, userId, settingsSet);
        return ACCOUNT_SETTING_UPDATE_SUCCEED;
    }
}
