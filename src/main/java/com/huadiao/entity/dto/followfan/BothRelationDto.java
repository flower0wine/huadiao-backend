package com.huadiao.entity.dto.followfan;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 我和别人的关系
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class BothRelationDto {
    /** 我的关注? */
    private Boolean following;
    /** 我的粉丝? */
    private Boolean followed;
}
