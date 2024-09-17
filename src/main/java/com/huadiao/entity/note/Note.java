package com.huadiao.entity.note;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 10 月 21 日
 */
@Data
public class Note {
    private Integer noteId;
    private Integer authorUid;
    private String noteTitle;
    private String noteContent;
    private Date publishTime;
    private Integer viewCount;
    private Integer starCount;
    private Integer likeCount;
    private Integer commentCount;
    private List<NoteTag> noteTags;
}
