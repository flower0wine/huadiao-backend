package com.huadiao.redis;

import com.huadiao.entity.message.system.SystemMessage;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
public interface MessageJedisUtil {
    /**
     * 生成系统消息 id
     * @return 返回 id
     */
    int generateSystemMessageId();

    /**
     * 生成私信消息 id
     * @return 消息 id
     */
    int generateWhisperMessageId();

    /**
     * 获取系统消息
     * @param offset 偏移量
     * @param row 行数
     * @return 返回系统消息
     */
    List<SystemMessage> getSystemMessage(Integer offset, Integer row);

    /**
     * 添加系统消息
     * @param systemMessage 系统消息
     * @param adminId 管理员 id
     */
    void addSystemMessage(SystemMessage systemMessage, Integer adminId);
}
