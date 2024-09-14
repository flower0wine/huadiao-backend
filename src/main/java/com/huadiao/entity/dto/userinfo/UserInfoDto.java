package com.huadiao.entity.dto.userinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户真实信息
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserInfoDto {
    /**
     * 三种情况: 0 (未知), 1 (男), 2 (女)
     */
    private Integer sex;
    private String canvases;
    private String school;
    private Date bornDate;
    private String nickname;
    private String userId;
    private Integer uid;
}
