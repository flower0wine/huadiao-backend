package com.huadiao.entity.message.whisper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 10 月 10 日
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LatestMessageUser {
    private Integer uid;
    private String nickname;
    private String userId;
    private String avatar;
    private String latestMessage;
    private Integer messageId;
    private Integer messageType;
    private Integer unreadCount;
    private Date sendTime;
}
