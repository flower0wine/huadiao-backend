package com.huadiao.entity.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @date 2023 年 09 月 27 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class NoteStarIdDao {
    private Integer noteId;
    private Integer authorUid;
}
