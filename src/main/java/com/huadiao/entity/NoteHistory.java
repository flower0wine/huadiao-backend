package com.huadiao.entity;

import com.huadiao.entity.dto.user.UserShareDto;
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
    private String noteTitle;
    private String noteContent;
    private Integer noteId;
    private Date visitTime;
    private UserShareDto author;
}
