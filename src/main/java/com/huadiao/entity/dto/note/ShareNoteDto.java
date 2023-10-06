package com.huadiao.entity.dto.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 公开给其他人看的笔记信息
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ShareNoteDto {
    private Integer noteId;
    private String noteTitle;
    private String noteContent;
    private Date publishTime;
    private Integer viewCount;
    private Integer starCount;
    private Integer likeCount;
    private Integer commentCount;
}
