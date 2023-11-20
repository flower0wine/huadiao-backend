package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.UserSettingController;
import com.huadiao.entity.Result;
import com.huadiao.service.UserSettingsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author flowerwine
 * @date 2023 年 10 月 16 日
 */
@RestController
@RequestMapping("/setting")
public class UserSettingControllerImpl extends AbstractController implements UserSettingController {
    private UserSettingsService userSettingsService;

    public UserSettingControllerImpl(UserSettingsService userSettingsService) {
        this.userSettingsService = userSettingsService;
    }

    @Override
    @GetMapping("/message/get")
    public Result<?> getUserMessageSettings(HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return userSettingsService.getMessageSettings(uid, userId);
    }

    @Override
    @PostMapping("/modify")
    public Result<?> modifyUserSettings(HttpSession session, @RequestBody Map<String, String> settingMap) throws Exception {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        if(settingMap == null) {
            return Result.blankParam();
        }
        // set 集合去重, 防止可能的错误, 或者入侵
        Set<String> settingsSet = new HashSet<>(settingMap.values());
        return userSettingsService.modifyAccountSettings(uid, userId, settingsSet);
    }

    @Override
    @GetMapping("/account/get")
    public Result<?> getUserAccountSettings(HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return userSettingsService.getUserSettings(uid, userId);
    }

}
