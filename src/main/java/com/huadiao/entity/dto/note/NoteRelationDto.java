package com.huadiao.entity.dto.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 我和笔记的关系
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class NoteRelationDto {
    /** 我的笔记? */
    private Boolean me;
    private Boolean myLike;
    private Boolean myUnlike;
    private Boolean myStar;
}
