package com.huadiao.service;

import java.util.regex.Pattern;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户设置抽象类
 */
public abstract class AbstractUserSettingsService extends AbstractService implements UserSettingsService {

    /**
     * 用户账号设置更新成功
     */
    public static String ACCOUNT_SETTING_UPDATE_SUCCEED = "accountSettingUpdateSucceed";

    /**
     * 用户设置更新失败
     */
    public static String ACCOUNT_SETTING_UPDATE_FAIL = "accountSettingUpdateFail";

}
