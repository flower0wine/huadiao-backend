package com.huadiao.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author flowerwine
 * @date 2024 年 07 月 06 日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnreadCountVO {
    private Integer reply;
    private Integer like;
    private Integer whisper;
    private Integer system;
}
