package com.huadiao.entity.vo;

import com.huadiao.entity.dao.EmoteDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author flowerwine
 * @date 2024 年 07 月 21 日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmoteVo {
    private String name;
    private Integer gid;
    private Integer type;
    private String filename;
    private List<EmoteDao> emotes;
}
