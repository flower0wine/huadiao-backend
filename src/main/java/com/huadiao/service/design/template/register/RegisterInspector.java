package com.huadiao.service.design.template.register;

import com.huadiao.entity.Result;
import com.huadiao.enumeration.LoginTypeEnum;
import com.huadiao.enumeration.ResultCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 该使用模板设计模式, 使得本站账号登录注册和第三方应用登录注册的代码易于维护和扩展
 *
 * @author flowerwine
 * @date 2024 年 02 月 10 日
 */
public interface RegisterInspector {

    /**
     * 检查是否可以进行注册
     *
     * @return 如果返回的 {@link com.huadiao.entity.Result} 的 code 为 {@link ResultCodeEnum#SUCCEED} 则可以进行注册, 否则不行
     */
    Result<?> check();

    /**
     * 添加新用户
     *
     * @param uid      用户 uid
     * @param userId   用户 id
     * @param request  请求
     * @param response 响应
     */
    void addUser(Integer uid, String userId, HttpServletRequest request, HttpServletResponse response);

    /**
     * 调用 ThreadLocal 的 remove 方法
     */
    void flushThreadLocal();

    /**
     * 获取授权码 ThreadLocal 对象
     * @return 返回授权码 ThreadLocal 对象
     */
    default ThreadLocal<String> getCodeThreadLocal() {
        return null;
    }

    /**
     * 获取登录类型
     * @return 登录类型
     */
    LoginTypeEnum getLoginType();
}
