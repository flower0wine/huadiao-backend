package com.huadiao.entity.message.like;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class LikeNoteMessage implements LikeMessageItem {
    private Integer noteId;
    private Integer authorUid;
    private String noteTitle;
}
