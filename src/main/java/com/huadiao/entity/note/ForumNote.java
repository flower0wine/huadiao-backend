package com.huadiao.entity.note;

import com.huadiao.entity.Label;
import com.huadiao.entity.dto.userdto.UserShareDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 12 月 24 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class ForumNote {
    private Integer id;
    private String title;
    private String summary;
    private String cover;
    private Date time;
    private Integer view;
    private Integer like;
    private List<Label> label;
    private UserShareDto author;
}
