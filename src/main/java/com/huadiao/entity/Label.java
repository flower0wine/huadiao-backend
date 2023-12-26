package com.huadiao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 12 月 24 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Label {
    private String title;
    private String link;
    /**
     * 创建日期
     */
    private Date time;
}
