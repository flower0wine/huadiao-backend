package com.huadiao.entity.vo;

import com.huadiao.entity.dto.followfan.BothRelationDto;
import com.huadiao.entity.dto.note.NoteRelationDto;
import com.huadiao.entity.note.NoteTag;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author flowerwine
 * @date 2024 年 09 月 17 日
 */
@Data
public class NoteVO {
    private Boolean me;
    private String noteTitle;
    private String noteContent;
    private Integer viewCount;
    private Integer likeCount;
    private Integer starCount;
    private Integer commentCount;
    private Date publishTime;
    private List<NoteTag> noteTags;
    private BothRelationDto authorAndMeRelation;
    private NoteRelationDto noteAndMeRelation;
}
