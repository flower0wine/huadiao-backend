package com.huadiao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 09 月 24 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class NoteStarInfo {
    private Integer uid;
    private Integer noteId;
    private Integer groupId;
    private String userId;
    private String nickname;
    private String userAvatar;
    private String noteTitle;
    private String noteContent;
    private Date starTime;
}
