package com.huadiao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FollowGroup {
    private Integer groupId;
    private String groupName;
    private Date createDate;
    private Integer count;
}
