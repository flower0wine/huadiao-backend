package com.huadiao.service.design.template.login;

import com.huadiao.entity.Result;
import com.huadiao.enumeration.ResultCodeEnum;

/**
 * @author flowerwine
 * @date 2024 年 02 月 11 日
 */
public interface LoginInspector {

    /**
     * 登陆检查
     * @return 如果返回的 {@link com.huadiao.entity.Result} 的 code 为 {@link ResultCodeEnum#SUCCEED} 则可以进行登录, 否则不行
     */
    Result<?> check();

    Integer getUid();

    String getUserId();

    /**
     * 调用 ThreadLocal 的 remove 方法
     */
    void flushThreadLocal();
}
