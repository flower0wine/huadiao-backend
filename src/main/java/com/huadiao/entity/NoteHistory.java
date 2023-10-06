package com.huadiao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 09 月 17 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class NoteHistory {
    private Integer uid;
    private String nickname;
    private String userId;
    private String userAvatar;
    private String noteTitle;
    private String noteContent;
    private Integer noteId;
    private Date visitTime;
}
