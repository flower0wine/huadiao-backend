package com.huadiao.service.forum.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.note.ForumNote;
import com.huadiao.entity.note.ForumRankNote;
import com.huadiao.mapper.ForumNoteMapper;
import com.huadiao.service.forum.AbstractForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author flowerwine
 * @date 2023 年 12 月 24 日
 */
@Slf4j
@Service
public class ForumServiceImpl extends AbstractForumService {
    private ForumNoteMapper forumNoteMapper;
    private Date updateForumNoteRankTime;

    @Autowired
    public ForumServiceImpl(ForumNoteMapper forumNoteMapper) {
        this.forumNoteMapper = forumNoteMapper;
    }

    /**
     * 更新首页论坛笔记排行榜
     */
    @PostConstruct
    private void updateForumNoteRank() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            forumJedisUtil.updateForumRankNote(noteRankMaxLength);
            this.updateForumNoteRankTime = new Date();
        };
        long delay = 3;
        // 三天执行一次
        executor.schedule(task, delay, TimeUnit.DAYS);
        task.run();
    }

    @Override
    public Result<?> randomGetNote(Integer uid, String userId, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取随机的笔记", uid, userId);
        // 随机笔记目前无法实现
        log.debug("目前不是随机获取文章, offset 为 {}, row 为 {}", offset, row);
        Result<?> result = checkOffsetAndRow(offset, row, (o, r) -> {
            List<ForumNote> forumNotes = forumNoteMapper.selectForumNote(o, r);
            if (forumNotes.size() == 0) {
                return Result.notExist();
            }
            return Result.ok(forumNotes);
        });
        if (!result.succeed()) {
            return result;
        }
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取随机的笔记", uid, userId);
        return result;
    }

    @Override
    public Result<?> getNoteRank(Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取笔记排行榜", uid, userId);
        List<ForumRankNote> forumRankNoteList = forumJedisUtil.getRangeNoteRank(noteRankMaxLength);
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取笔记排行榜", uid, userId);

        if(forumRankNoteList.size() == 0) {
            return Result.notExist();
        }

        Map<String, Object> map = new HashMap<>(4);
        map.put("noteRank", forumRankNoteList);
        map.put("updateTime", updateForumNoteRankTime);
        return Result.ok(map);
    }
}
