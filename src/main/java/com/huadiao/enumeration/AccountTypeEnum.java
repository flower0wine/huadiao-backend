package com.huadiao.enumeration;

/**
 * @author flowerwine
 * @date 2024 年 02 月 11 日
 */
public enum AccountTypeEnum {
    /**
     * 普通用户
     */
    USER(0),
    /**
     * 管理员
     */
    ADMIN(1),
    /**
     * 官方账号
     */
    OFFICIAL(2),
    /**
     * 政府账号
     */
    GOVERNMENT(3),
    ;
    private final int type;

    AccountTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
