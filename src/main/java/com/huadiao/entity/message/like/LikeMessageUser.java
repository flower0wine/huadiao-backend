package com.huadiao.entity.message.like;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LikeMessageUser {
    private Integer uid;
    private String userId;
    private String nickname;
    private String avatar;
    private Boolean follow;
    private Boolean fan;
    private Date likeTime;
}
