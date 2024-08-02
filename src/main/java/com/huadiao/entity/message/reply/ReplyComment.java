package com.huadiao.entity.message.reply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 10 月 09 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class ReplyComment implements ReplyMessage {
    private Integer uid;
    private String userId;
    private Integer repliedUid;
    private String nickname;
    private String avatar;
    private Integer noteId;
    private String noteTitle;
    private Integer authorUid;
    private Integer rootCommentId;
    private Integer subCommentId;
    private String commentContent;
    private Date time;
}
