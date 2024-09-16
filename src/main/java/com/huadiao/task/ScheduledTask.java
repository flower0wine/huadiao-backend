package com.huadiao.task;

import com.huadiao.redis.ForumJedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author flowerwine
 * @date 2024 年 09 月 16 日
 */
@Component
public class ScheduledTask {

    @Resource
    private ForumJedisUtil forumJedisUtil;

    @Value("${huadiaoForum.noteRankMaxLength}")
    protected int noteRankMaxLength;

    @PostConstruct
    public void init() {
        forumJedisUtil.updateForumRankNote(noteRankMaxLength);
    }

    @Scheduled(cron = "0 0 0 */1 * ?")
    public void runTask() {
        forumJedisUtil.updateForumRankNote(noteRankMaxLength);
    }
}