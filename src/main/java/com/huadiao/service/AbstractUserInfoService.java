package com.huadiao.service;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public abstract class AbstractUserInfoService extends AbstractService implements UserInfoService {

    /**
     * 昵称最长为 20
     */
    public static int NICKNAME_MAX_LENGTH = 20;

    /**
     * 个人简介长度最长为 50 字符
     */
    public static int CANVASES_MAX_LENGTH = 50;

    /**
     * 学校最大长度为 30
     */
    public static int SCHOOL_MAX_LENGTH = 30;

    @Value("${userInfo.nullNickname}")
    protected String nullNickname;

    @Value("${userInfo.wrongNicknameLength}")
    protected String wrongNicknameLength;

    @Value("${userInfo.wrongSex}")
    protected String wrongSex;

    @Value("${userInfo.wrongSchoolLength}")
    protected String wrongSchoolLength;

    @Value("${userInfo.wrongBornDate}")
    protected String wrongBornDate;

}
