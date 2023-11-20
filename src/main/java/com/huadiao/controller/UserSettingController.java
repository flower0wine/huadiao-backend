package com.huadiao.controller;

import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2023 年 10 月 16 日
 */
public interface UserSettingController extends Controller {

    /**
     * 获取用户消息设置
     *
     * @param session session 对象
     * @return 返回消息设置
     */
    Result<?> getUserMessageSettings(HttpSession session);


    /**
     * 获取用户设置
     *
     * @param session session 对象
     * @return 返回用户设置
     */
    Result<?> getUserAccountSettings(HttpSession session);


    /**
     * 更新账号设置
     *
     * @param settingMap 设置集合
     * @param session    session 对象
     * @return 返回更新成功提示信息
     * @throws Exception 可能会抛出异常
     */
    Result<?> modifyUserSettings(HttpSession session, Map<String, String> settingMap) throws Exception;


}
