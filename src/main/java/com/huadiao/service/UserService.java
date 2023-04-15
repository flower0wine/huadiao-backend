package com.huadiao.service;

import com.huadiao.entity.dto.UserBaseInfoDto;
import com.huadiao.entity.dto.UserDetailDto;
import com.huadiao.entity.dto.UserIndexDto;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 业务层: 与用户信息, 想用账号相关的操作的接口
 * @author flowerwine
 */
public interface UserService {

    /**
     * 获取用户在花凋首页的信息
     * @param request 请求对象
     * @param response 响应对象
     * @return 获取用户在花凋首页的信息
     * @throws Exception 可能抛出异常
     */
    UserIndexDto getUserHuadiaoIndexInfo(HttpServletRequest request, HttpServletResponse response) throws Exception;


    /**
     * 花凋用户登录
     * @param request 请求对象
     * @param response 响应对象
     * @param username 用户名
     * @param password 密码
     * @throws Exception 可能抛出异常
     */
    void huadiaoUserLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) throws Exception;

    /**
     * 退出登录
     * @param cookie 退出登录, 要删除的 cookie -> User_ID
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
     * 注册花凋新用户
     * @param session session 对象
     * @param username 用户名
     * @param password 密码
     * @param confirmPassword 再次确认密码
     * @param checkCode 验证码
     * @return 返回错误或正确标识
     */
    String registerHuadiao(HttpSession session, String username, String password, String confirmPassword, String checkCode) throws Exception;

    /**
     * 新增用户信息, 如果用户信息存在则更改用户信息
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param nickname 昵称
     * @param canvases 个人简介
     * @param sex 性别
     * @param bornDate 出生日期
     * @param school 学校
     * @throws Exception 可能抛出异常
     */
    void insertOrUpdateUserInfo(Integer uid, String userId, String nickname,
                                String canvases, String sex,
                                String bornDate, String school) throws Exception;

    /**
     * 获取用户信息
     * @param uid 用户 uid
     * @return 返回用户详细信息
     * @throws Exception 可能抛出异常
     */
    UserDetailDto getUserInfo(Integer uid) throws Exception;

}
