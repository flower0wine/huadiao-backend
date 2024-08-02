package com.huadiao.dto.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author flowerwine
 * @date 2024 年 07 月 07 日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteCommentDTO {
    private Integer uid;
    private Integer replyUid;
    private Integer repliedUid;
    private Integer noteId;
    private Integer authorUid;
    private Integer rootCommentId;
    private Integer subCommentId;
}
