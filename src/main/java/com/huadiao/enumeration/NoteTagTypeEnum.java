package com.huadiao.enumeration;

import lombok.Getter;

/**
 * @author flowerwine
 * @date 2024 年 09 月 16 日
 */
@Getter
public enum NoteTagTypeEnum {
    /**
     * 前端
     */
    FRONTEND(1),

    /**
     * 后端
     */
    BACKEND(2),
    ;

    private final int type;

    NoteTagTypeEnum(int type) {
        this.type = type;
    }
}
