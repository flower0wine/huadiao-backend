package com.huadiao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 09 月 25 日
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NoteStarCatalogue {
    private Integer groupId;
    private String groupName;
    private String groupDescription;
    private Integer count;
    private Boolean allowOperate;
    private Boolean open;
    private Date createTime;
    private Date modifyTime;
}
