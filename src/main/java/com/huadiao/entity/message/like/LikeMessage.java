package com.huadiao.entity.message.like;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class LikeMessage {
    private Integer type;
    private Integer count;
    private Date time;
    private LikeMessageItem likeMessageItem;
    private List<LikeMessageUser> userList;
}
