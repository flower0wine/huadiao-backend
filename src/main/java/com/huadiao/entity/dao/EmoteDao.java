package com.huadiao.entity.dao;

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
public class EmoteDao {
    private Integer id;
    private String name;
    private Integer type;
    private String filename;
    private Integer gid;
}
