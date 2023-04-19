package com.huadiao.entity.dto.userdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 可共享至其他平台的用户信息
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserShareDto {
    private Integer uid;
    private String userId;
    private String nickname;
    private String userAvatar;
}
