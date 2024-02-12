package com.huadiao.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @date 2024 年 02 月 11 日
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserOAuthDto {
    private Integer uid;
    private String userId;
    private String username;
    private String password;
    private Integer loginType;
    private Integer oauthId;
    private String accessToken;
    private Integer accountType;
}
