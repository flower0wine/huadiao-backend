package com.huadiao.entity.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 10 月 21 日
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Note {
    private Integer noteId;
    private String noteTitle;
    private String noteContent;
    private Date publishTime;
    private Integer viewCount;
    private Integer starCount;
    private Integer likeCount;
    private Integer commentCount;
}
