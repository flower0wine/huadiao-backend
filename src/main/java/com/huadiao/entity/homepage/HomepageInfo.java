package com.huadiao.entity.homepage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 个人主页信息
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class HomepageInfo {
    private Integer uid;
    private String pageBackground;
    private String userAvatar;
    private Integer visitCount;
}
