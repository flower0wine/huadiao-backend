package com.huadiao.entity.dto.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 当自己编辑笔记时获取笔记信息
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class SelfNoteDto {
    private String noteTitle;
    private String noteContent;
}
