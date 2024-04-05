package com.huadiao.entity.dto.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.List;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 笔记评论传输对象
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class NoteCommentDto {
    private Integer uid;
    private String userId;
    private String userAvatar;
    private String nickname;
    private Integer commentId;
    private Date commentDate;
    private String commentContent;
    private Boolean myLike;
    private Boolean myUnlike;
    private Integer likeCount;
    private List<NoteCommentDto> commentList;
}
