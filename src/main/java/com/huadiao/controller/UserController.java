package com.huadiao.controller;

import com.huadiao.entity.AccountSettings;
import com.huadiao.entity.dto.accountsettings.MessageSettingsDto;
import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.entity.dto.userdto.UserShareDto;
import com.huadiao.entity.dto.userinfodto.UserInfoDto;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 用户信息控制器接口
 * @author flowerwine
 */
public interface UserController extends Controller {

    /**
     * 获取花凋头部面板用户信息
     * @param request 请求对象
     * @return 返回用户信息
     */
    UserAbstractDto getHuadiaoHeaderUserInfo(HttpServletRequest request);

    /**
     * 或吊用户进行登录
     * @param request 请求对象
     * @param response 响应对象
     * @param map 请求体参数集合
     * @throws Exception 可能抛出异常
     */
    void huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, Map<String, String> map) throws Exception;

    /**
     * 退出登录
     * @param cookie 对应的 User_ID cookie
     * @param request 请求对象
     */
    void logoutHuadiao(Cookie cookie, HttpServletRequest request);

    /**
     * 获取注册账号的验证码
     * @param session session
     * @param response 响应对象
     * @throws Exception 可能抛出异常
     */
    void getCheckCode(HttpSession session, HttpServletResponse response) throws Exception;

    /**
     * 注册花凋账号
     * @param map 账号, 密码, 验证码的集合
     * @param session session 对象
     * @param request 请求对象
     * @param response 响应对象
     * @return 返回错误或正确标识
     * @throws Exception 可能抛出异常
     */
    String registerHuadiao(Map<String, String> map, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 新增用户信息, 如果用户信息存在则更改用户信息
     * @param map 用户信息集合
     * @param session session 对象
     * @param request 请求对象
     * @param response 响应对象
     * @return 返回错误提示信息, 如日期错误返回 wrongBornDate
     * @throws Exception 可能抛出异常
     */
    String insertOrUpdateUserInfo(Map<String, String> map, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 获取用户信息
     * @param request 请求对象
     * @param response 响应对象
     * @param session session 对象
     * @return 返回用户详细信息
     */
    UserInfoDto getUserInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session);

    /**
     * 获取用户消息设置
     * @param request 请求对象
     * @return 返回消息设置
     */
    MessageSettingsDto getUserMessageSettings(HttpServletRequest request);

    /**
     * 获取用户设置
     * @param request 请求对象
     * @return 返回用户设置
     */
    AccountSettings getUserAccountSettings(HttpServletRequest request);

    /**
     * 更新账号设置
     * @param request 请求对象
     * @param settingMap 设置集合
     * @return 返回更新成功提示信息
     */
    String modifyUserSettings(HttpServletRequest request, Map<String, String> settingMap);

    /**
     * 获取可共享用户信息
     * @param httpSession session 对象
     * @return 返回可共享对象
     */
    UserShareDto getUserShare(HttpSession httpSession);
}
