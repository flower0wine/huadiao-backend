package com.huadiao.controller;

import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
public interface SystemMessageController extends Controller {

    /**
     * 获取系统消息
     * @param session session
     * @param offset 偏移量
     * @param row 行数
     * @return 返回系统消息
     */
    Result<?> getSystemMessage(HttpSession session, Integer offset, Integer row);
}
