package com.huadiao.entity.dto;

import com.huadiao.entity.Poem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 花凋首页用户信息
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserIndexDto {
    private Boolean login;
    private Integer uid;
    private Integer fan;
    private Integer follow;
    private String userId;
    private String nickname;
    private String userAvatar;
}
