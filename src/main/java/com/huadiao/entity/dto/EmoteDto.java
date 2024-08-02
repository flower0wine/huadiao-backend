package com.huadiao.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author flowerwine
 * @date 2024 年 07 月 20 日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmoteDto {
    private String name;
    private Integer gid;
    private Integer type;
}
