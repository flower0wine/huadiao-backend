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
@Setter
@Getter
@ToString
@NoArgsConstructor
public class LikeCommentMessage implements LikeMessageItem {
    private String commentContent;
    private Integer noteId;
    private Integer authorUid;
    private Integer rootCommentId;
    private Integer subCommentId;
}
