package com.huadiao.entity.dto.userinfodto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private String sex;
    private String canvases;
    private String school;
    private String bornDate;
}
