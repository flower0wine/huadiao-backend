package com.huadiao.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户简要信息
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserAbstractInfoDto {
    private Integer uid;
    private String userId;
    private String username;
    private String nickname;
    private String userAvatar;
    /**
     * 关注和粉丝数量
     */
    private Integer follow;
    private Integer fan;
}
