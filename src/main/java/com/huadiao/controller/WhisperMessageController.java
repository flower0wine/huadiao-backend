package com.huadiao.controller;

import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2023 年 10 月 10 日
 */
public interface WhisperMessageController extends Controller {
    /**
     * 获取最近消息中的用户
     *
     * @param session session 对象
     * @param offset  偏移量
     * @param row     行数
     * @return 返回最近消息中的用户
     */
    Result<?> getLatestUser(HttpSession session, Integer offset, Integer row);

    /**
     * 单独获取最近消息中的用户
     * @param session session 对象
     * @param uid 当前页面聊天的用户 uid
     * @return 返回最近消息中的某个用户
     */
    Result<?> getSingleLatestUser(HttpSession session, Integer uid);

    /**
     * 删除最近消息中的用户
     *
     * @param session session 对象
     * @param uid     要删除的用户 uid
     * @return 返回删除过程中的提示
     */
    Result<?> deleteLatestUser(HttpSession session, Integer uid);

    /**
     * 获取私信消息
     *
     * @param session session 对象
     * @param uid     私信对象 uid
     * @param offset  偏移量
     * @param row     行数
     * @return 返回私信消息
     */
    Result<?> getWhisperMessage(HttpSession session, Integer uid, Integer offset, Integer row);

    /**
     * 删除私信消息
     * @param session session 对象
     * @param messageId 私信 id
     * @return 返回删除过程中的提示
     */
    Result<?> deleteWhisperMessage(HttpSession session, Integer messageId);

    /**
     *  添加私信消息
     * @param session session 对象
     * @param uid 接收者 uid
     * @param map 请求体
     * @return 成功则返回新增产生的 messageId
     */
    Result<?> addWhisperMessage(HttpSession session, Integer uid, Map<String, String> map);
}
