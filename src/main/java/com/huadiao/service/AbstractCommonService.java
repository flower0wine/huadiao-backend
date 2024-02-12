package com.huadiao.service;

import com.huadiao.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author flowerwine
 * @date 2024 年 02 月 09 日
 */
@Slf4j
public abstract class AbstractCommonService extends AbstractService implements CommonService {
    /**
     * 维持用户登录状态的 session, 服务端 session 存活时间, 以秒为单位, 存活时间为一个月
     */
    @Value("${user.sessionSurvivalTime}")
    protected int sessionSurvivalTime;

    /**
     * 验证码图片宽度
     */
    @Value("${user.codeImageWidth}")
    protected int codeImageWidth;

    /**
     * 验证码图片高度
     */
    @Value("${user.codeImageHeight}")
    protected int codeImageHeight;

    /**
     * 验证码长度
     */
    @Value("${user.codeLength}")
    protected int codeLength;

    /**
     * 干扰字符的数量
     */
    @Value("${user.codeDisturbCount}")
    protected int codeDisturbCount;

    /**
     * 恭喜! 注册成功!
     */
    protected static final String succeedRegister = "succeedRegister";


}
