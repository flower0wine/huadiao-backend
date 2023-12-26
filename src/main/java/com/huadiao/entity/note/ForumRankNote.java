package com.huadiao.entity.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @date 2023 年 12 月 25 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class ForumRankNote {
    private Integer uid;
    private Integer noteId;
    private String title;
}
