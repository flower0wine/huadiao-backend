package com.huadiao.enumeration;

/**
 * 登陆类型
 *
 * @author flowerwine
 * @date 2024 年 02 月 11 日
 */
public enum LoginTypeEnum {
    /**
     * 本站账号
     */
    HUADIAO(0),
    /**
     * github 账号
     */
    GITHUB(1),
    /**
     * QQ 账号
     */
    QQ(2),
    /**
     * 微信
     */
    WECHAT(3),
    /**
     * gitee 账号
     */
    GITEE(4),
    /**
     * 谷歌账号
     */
    GOOGLE(5),
    ;

    private final int type;

    LoginTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
