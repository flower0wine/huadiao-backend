package com.huadiao.service.forum.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.note.ForumNote;
import com.huadiao.entity.req.RandomGetNote;
import com.huadiao.mapper.ForumNoteMapper;
import com.huadiao.redis.entity.NoteRank;
import com.huadiao.service.forum.AbstractForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 12 月 24 日
 */
@Slf4j
@Service
public class ForumServiceImpl extends AbstractForumService {
    private ForumNoteMapper forumNoteMapper;

    @Autowired
    public ForumServiceImpl(ForumNoteMapper forumNoteMapper) {
        this.forumNoteMapper = forumNoteMapper;
    }

    @Override
    public Result<?> randomGetNote(Integer uid, String userId, RandomGetNote randomGetNote) {
        // 随机笔记目前无法实现
        Integer page = randomGetNote.getPage();
        Integer size = randomGetNote.getSize();
        Integer tagId = randomGetNote.getTagId();

        return checkPageAndSize(page, size, (o, r) -> {
            List<ForumNote> forumNotes = forumNoteMapper.selectForumNote(o, r, tagId);
            if (forumNotes.size() == 0) {
                return Result.emptyData();
            }
            return Result.ok(forumNotes);
        });
    }

    @Override
    public Result<?> getNoteRank(Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取笔记排行榜", uid, userId);
        NoteRank noteRank = forumJedisUtil.getRangeNoteRank(noteRankMaxLength);
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取笔记排行榜", uid, userId);

        if(noteRank == null) {
            return Result.emptyData();
        }

        return Result.ok(noteRank);
    }
}
