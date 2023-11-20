package com.huadiao.redis;

/**
 * @author flowerwine
 * @date 2023 年 10 月 01 日
 */
public interface UserBaseJedisUtil {

    /**
     * 生成用户 uid, 从 1 开始
     *
     * @return 返回唯一的 uid
     */
    int generateUid();

    /**
     * 存储验证码, 时间五分钟
     * @param jsessionid jsessionid
     * @param checkCode 验证码
     */
    void setCheckCode(String jsessionid, String checkCode);

    /**
     * 获取验证码
     * @param jsessionid jsessionid
     * @return 返回验证码
     */
    String getCheckCode(String jsessionid);

    /**
     * 删除验证码
     * @param jsessionid jsessionid
     */
    void deleteCheckCode(String jsessionid);
}
