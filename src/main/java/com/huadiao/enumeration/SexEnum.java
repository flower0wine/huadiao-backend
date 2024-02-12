package com.huadiao.enumeration;

/**
 * 性别
 * @author flowerwine
 * @date 2023 年 09 月 15 日
 */
public enum SexEnum {
    /**
     * 未知性别
     */
    NO_KNOWN("0"),
    /**
     * 男性
     */
    MAN("1"),
    /**
     * 女性
     */
    WOMEN("2");

    public final String value;

    SexEnum(String value) {
        this.value = value;
    }

    /**
     * 根据 value 匹配 SexEnum
     * @param value 要匹配的值
     * @return 返回匹配的 SexEnum, 没有匹配的返回 null
     */
    public static SexEnum match(String value) {
        SexEnum[] sexEnums = values();
        for (SexEnum sex : sexEnums) {
            if(sex.value.equals(value)) {
                return sex;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
