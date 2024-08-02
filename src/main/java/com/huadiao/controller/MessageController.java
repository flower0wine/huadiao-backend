package com.huadiao.controller;

import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2024 年 07 月 06 日
 */
public interface MessageController extends Controller {

    /**
     * @param session HttpSession 对象
     * @return 返回未读消息数量
     */
    Result<?> getUnreadMessageCount(HttpSession session);
}
