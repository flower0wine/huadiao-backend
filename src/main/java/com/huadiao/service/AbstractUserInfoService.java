package com.huadiao.service;

import java.util.regex.Pattern;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public abstract class AbstractUserInfoService implements UserInfoService {

    /**
     * 默认的用户个人简介
     */
    public static String DEFAULT_USER_CANVASES = "这个人十分神秘...";

    /**
     * 默认的用户性别, 为未知, 0(未知), 1(男性), 2(女性)
     */
    public static String DEFAULT_USER_SEX = "0";

    /**
     * 男性
     */
    public static String SEX_MAN = "1";

    /**
     * 女性
     */
    public static String SEX_WOMEN = "2";

    /**
     * 默认的出生日期
     */
    public static String DEFAULT_USER_BORN_DATE = "";

    /**
     * 默认的学校
     */
    public static String DEFAULT_USER_SCHOOL = "";

    /**
     * 错误的日期格式
     */
    public static String WRONG_BORN_DATE = "wrongBornDate";

    /**
     * 昵称不能为空或只包含空格
     */
    public static String WRONG_NULL_NICKNAME = "wrongNullNickname";

    /**
     * 昵称最长为 20
     */
    public static int NICKNAME_MAX_LENGTH = 20;

    /**
     * 昵称长度最大为 20 字符
     */
    public static String WRONG_LENGTH_NICKNAME = "wrongLengthNickname";

    /**
     * 个人简介长度最长为 50 字符
     */
    public static String WRONG_LENGTH_CANVASES = "wrongLengthCanvases";

    /**
     * 个人简介长度最长为 50 字符
     */
    public static int CANVASES_MAX_LENGTH = 50;

    /**
     * 错误的性别
     */
    public static String WRONG_SEX = "wrongSex";

    /**
     * 学校长度最大为 30 字符
     */
    public static String WRONG_LENGTH_SCHOOL = "wrongLengthSchool";

    /**
     * 学校最大长度为 30
     */
    public static int SCHOOL_MAX_LENGTH = 30;

    /**
     * 出生日期正则表达式
     */
    public static Pattern bornDateReg = Pattern.compile("^\\d{4}([-/])\\d{1,2}\\1\\d{1,2}$");

}
