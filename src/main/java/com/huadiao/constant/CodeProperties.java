package com.huadiao.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 验证码相关配置
 *
 * @author flowerwine
 * @date 2024 年 09 月 18 日
 */
@Data
@Component
public class CodeProperties {

    /**
     * 验证码图片宽度
     */
    @Value("${user.codeImageWidth}")
    public Integer codeImageWidth;

    /**
     * 验证码图片高度
     */
    @Value("${user.codeImageHeight}")
    public Integer codeImageHeight;

    /**
     * 验证码长度
     */
    @Value("${user.codeLength}")
    public Integer codeLength;

    /**
     * 干扰字符的数量
     */
    @Value("${user.codeDisturbCount}")
    public Integer codeDisturbCount;
}
