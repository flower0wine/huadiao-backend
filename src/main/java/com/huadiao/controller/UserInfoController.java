package com.huadiao.controller;

import com.huadiao.entity.Result;
import com.huadiao.entity.dto.user.UserShareDto;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2023 年 10 月 16 日
 */
public interface UserInfoController extends Controller {


    /**
     * 新增用户信息, 如果用户信息存在则更改用户信息
     *
     * @param map     用户信息集合
     * @param session session 对象
     * @return 返回错误提示信息, 如日期错误返回 wrongBornDate
     * @throws Exception 可能抛出异常
     */
    Result<?> insertOrUpdateUserInfo(Map<String, String> map, HttpSession session) throws Exception;

    /**
     * 获取用户信息, 如果指定了 uid 则查询该 uid
     *
     * @param session session 对象
     * @param uid 指定查询的用户 uid
     * @return 返回用户详细信息
     */
    Result<?> getUserInfo(HttpSession session, Integer uid);

    /**
     * 获取可共享用户信息
     *
     * @param httpSession session 对象
     * @return 返回可共享对象
     */
    UserShareDto getUserShare(HttpSession httpSession);

    @GetMapping("/account")
    Result<?> getAccountInfo(HttpSession session);
}
