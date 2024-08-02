package com.huadiao.service;

import com.huadiao.entity.Result;

/**
 * @author flowerwine
 * @date 2023 年 10 月 10 日
 */
public interface WhisperMessageService extends Service {

    /**
     * 获取最近消息中的用户
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param offset 偏移量
     * @param row 行数
     * @return 返回最近消息中的用户
     */
    Result<?> getLatestUser(Integer uid, String userId, Integer offset, Integer row);

    /**
     * 获单独取最近消息中的用户
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param chatUid 当前页面聊天的用户 uid
     * @return 返回最近消息中的某个用户
     */
    Result<?> getSingleLatestUser(Integer uid, String userId, Integer chatUid);

    /**
     * 删除最近消息中的用户
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param latestUid 要删除的用户 uid
     * @return 返回删除过程中的提示
     */
    Result<?> deleteLatestUser(Integer uid, String userId, Integer latestUid);

    /**
     * 获取私信消息
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param whisperUid 私信对象 uid
     * @param offset 偏移量
     * @param row 行数
     * @return 返回私信消息
     */
    Result<?> getWhisperMessage(Integer uid, String userId, Integer whisperUid, Integer offset, Integer row);

    /**
     * 删除私信消息
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param messageId 私信 id
     * @return 返回删除过程中的提示
     */
    Result<?> deleteWhisperMessage(Integer uid, String userId, Integer messageId);

    /**
     *  添加私信消息
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param receiveUid 接收者 uid
     * @param messageContent 消息内容
     * @return 成功则返回新增产生的 messageId
     */
    Result<?> addWhisperMessage(Integer uid, String userId, Integer receiveUid, String messageContent);

    /**
     * 获取私信消息未读数
     * @param uid 接收者 uid
     * @param userId 接收者 id
     * @return 返回未读数
     */
    Result<Integer> countUnreadMessage(Integer uid, String userId);
}
