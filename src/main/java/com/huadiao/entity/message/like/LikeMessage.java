package com.huadiao.entity.message.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeMessage {
    private Integer uid;
    private String userId;
    private Integer authorUid;
    private Integer replyUid;
    private Integer repliedUid;
    private String nickname;
    private String avatar;
    private Integer noteId;
    private String noteTitle;
    private String comment;
    private Integer rootCommentId;
    private Integer subCommentId;
    private Date likeTime;
}
