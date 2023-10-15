package com.huadiao.entity.message.whisper;

/**
 * 该消息类型指的是消息的展现形式, 位于客户端的样式类型
 *
 * @author flowerwine
 * @date 2023 年 10 月 12 日
 */
public enum MessageType {
    /**
     * 普通消息, 收发者消息内容两边对齐
     */
    NORMAL(1);

    MessageType(int type) {
        this.type = type;
    }

    public final int type;

    public static MessageType getMessageType(int type) {
        for (MessageType messageType : MessageType.values()) {
            if (messageType.type == type) {
                return messageType;
            }
        }
        return null;
    }
}
