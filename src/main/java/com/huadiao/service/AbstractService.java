package com.huadiao.service;

import com.huadiao.redis.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public abstract class AbstractService implements Service {
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected UserInfoJedisUtil userInfoJedisUtil;

    @Autowired
    protected UserSettingJedisUtil userSettingJedisUtil;

    @Autowired
    protected StarJedisUtil starJedisUtil;

    @Autowired
    protected FollowFanJedisUtil idGeneratorJedisUtil;

    @Autowired
    protected HuadiaoHouseJedisUtil huadiaoHouseJedisUtil;

    /**
     * 匹配设置字段, 要求全部为英文字母
     */
    public static Pattern pattern = Pattern.compile("^\\w+$");

    public String imageHost = "/images/";

    /**
     * 字段格式化, 例如: exampleExample --> example_example
     * @param field 字段
     * @return 转化后的字段
     */
    protected String fieldFormat(String field) {
        String settingReplaceRegex = "([a-z])([A-Z])";
        String replace = "$1_$2";
        return  field.replaceAll(settingReplaceRegex, replace).toLowerCase();
    }
}
