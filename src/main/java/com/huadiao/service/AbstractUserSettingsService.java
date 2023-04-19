package com.huadiao.service;

import java.util.regex.Pattern;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public abstract class AbstractUserSettingsService implements UserSettingsService {

    /**
     * 用户账号设置更新成功
     */
    public static String ACCOUNT_SETTING_UPDATE_SUCCEED = "accountSettingUpdateSucceed";

    /**
     * 用户设置更新失败
     */
    public static String ACCOUNT_SETTING_UPDATE_FAIL = "accountSettingUpdateFail";

    /**
     * 匹配设置字段, 要求全部为英文字母
     */
    public static Pattern pattern = Pattern.compile("^\\w+$");
}
