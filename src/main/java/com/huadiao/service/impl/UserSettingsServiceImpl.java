package com.huadiao.service.impl;

import com.huadiao.entity.account.AccountSettings;
import com.huadiao.entity.message.setting.MessageSetting;
import com.huadiao.entity.Result;
import com.huadiao.mapper.UserSettingsMapper;
import com.huadiao.service.AbstractUserSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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
    public Result<?> getUserSettings(Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取自己的账号设置", uid, userId);
        AccountSettings accountSettings = userSettingJedisUtil.getAccountSettings(uid);
        log.debug("uid, userId 分别为 {}, {} 的用户获取账号设置成功", uid, userId);
        return Result.ok(accountSettings);
    }

    @Override
    public Result<?> getMessageSettings(Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取自己的消息设置", uid, userId);
        AccountSettings accountSettings = userSettingJedisUtil.getAccountSettings(uid);
        MessageSetting messageSetting = new MessageSetting();
        messageSetting.setMessageRemindStatus(accountSettings.getMessageRemindStatus());
        messageSetting.setMessageLikeStatus(accountSettings.getMessageLikeStatus());
        messageSetting.setMessageReplyStatus(accountSettings.getMessageReplyStatus());
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取自己的消息设置", uid, userId);
        return Result.ok(messageSetting);
    }

    @Override
    public Result<?> modifyAccountSettings(Integer uid, String userId, Set<String> settingsSet) throws Exception {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试更新自己的账号设置, 传递的参数为 settingsList: {}", uid, userId, settingsSet);
        String[] settingsArr = settingsSet.toArray(new String[0]);
        // 格式转换, 例如将 publicStarStatus --> public_star_status
        for (int index = 0; index < settingsArr.length; index++) {
            String str = settingsArr[index];
            // 如果不是全英文返回更新失败
            if (!pattern.matcher(str).find()) {
                log.warn("uid, userId 分别为 {}, {} 的用户传入的参数存在问题, settingsArr[{}]: {}", uid, userId, index, settingsArr[index]);
                return Result.errorParam();
            }
            settingsArr[index] = fieldFormat(str);
            log.debug("将用户传递的参数格式进行转换, {} -> {}", str, settingsArr[index]);
        }
        userSettingJedisUtil.setAccountSettings(uid, settingsArr, settingsSet);
        log.debug("uid, userId 分别为 {}, {} 的用户更新账号设置成功, settingsList: {}", uid, userId, settingsSet);
        return Result.ok(null);
    }
}
