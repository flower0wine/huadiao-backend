package com.huadiao.redis.entity;

import com.huadiao.entity.note.ForumRankNote;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author flowerwine
 * @date 2024 年 09 月 16 日
 */
@Data
@NoArgsConstructor
public class NoteRank {
    private Date updateTime;
    private List<ForumRankNote> noteRank;
}
