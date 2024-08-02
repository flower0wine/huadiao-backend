package com.huadiao.enumeration;

/**
 * @author flowerwine
 * @date 2024 年 07 月 06 日
 */
public enum MessageTypeEnum {
    REPLY(1),
    LIKE(2),
    WHISPER(3),
    SYSTEM(4)
    ;

    public int type;

    MessageTypeEnum(int type) {
        this.type = type;
    }
}
