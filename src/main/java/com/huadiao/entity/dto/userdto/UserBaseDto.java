package com.huadiao.entity.dto.userdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户基本信息
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserBaseDto {
    private Integer uid;
    private String userId;
    private String username;
}
