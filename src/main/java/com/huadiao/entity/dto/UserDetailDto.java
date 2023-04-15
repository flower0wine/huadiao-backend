package com.huadiao.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户详细信息
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDetailDto {
    private Boolean login;
    private Integer uid;
    private String userId;
    private String nickname;
    private String userAvatar;
    /**
     * 关注和粉丝数量
     */
    private Integer follow;
    private Integer fan;
    /**
     * 三种情况: 0 (未知), 1 (男), 2 (女)
     */
    private String sex;
    private String canvases;
    private String school;
    private String bornDate;
}
