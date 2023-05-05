package com.huadiao.entity.dto.followfan;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 装载关注与粉丝最基本的信息, 如全部关注的数量, 全部粉丝的数量
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FollowFanBaseInfoDto {
    private Integer uid;
    private Integer followCount;
    private Integer fanCount;
}
