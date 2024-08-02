package com.huadiao.service;

import com.huadiao.entity.Result;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
public interface SystemMessageService {

    /**
     * 新增系统消息
     * @param adminId 管理员id
     * @param messageTitle 消息标题
     * @param messageContent 消息内容
     * @param form 消息展示形式
     * @return 返回新增过程中的提示
     */
    Result<?> addSystemMessage(Integer adminId, String messageTitle, String messageContent, Integer form);

    /**
     * 删除系统消息
     * @param adminId 管理员id
     * @param messageId 消息id
     * @return 返回删除过程中的提示
     */
    Result<?> deleteSystemMessage(Integer adminId, Integer messageId);

    /**
     * 获取系统消息
     * @param uid 用户 uid
     * @param offset 偏移量
     * @param row 行数
     * @return 返回系统消息
     */
    Result<?> getSystemMessage(Integer uid, Integer offset, Integer row);

    /**
     * 获取未读消息数量
     * @param uid 用户 uid
     * @param userId 用户 id
     * @return 返回未读消息数量
     */
    Result<Integer> countUnreadMessage(Integer uid, String userId);
}
