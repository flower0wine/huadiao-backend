package com.huadiao.redis.impl;

import cn.hutool.json.JSONUtil;
import com.huadiao.entity.note.ForumRankNote;
import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.ForumJedisUtil;
import com.huadiao.redis.entity.NoteRank;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author flowerwine
 * @date 2023 年 12 月 24 日
 */
@Component
public class ForumJedis extends AbstractJedis implements ForumJedisUtil {

    /**
     * 使用 redis 的 set 集合来存储论坛帖子 id <br>
     * 集合名称为 forumNoteId <br>
     * 存储的是笔记 id
     */
    private String forumNoteId = "forumNoteId";

    private String forumNoteRank = "forumNoteRank";

    @Override
    public void addNoteId(Integer noteId) {
        Jedis jedis = jedisPool.getResource();
        if(jedis.scard(forumNoteId) == 0L) {
            List<Integer> list = noteMapper.selectAllNoteId();
            jedis.sadd(forumNoteId, list.stream().map(String::valueOf).toArray(String[]::new));
        }
        jedis.sadd(forumNoteId, noteId.toString());
        jedis.close();
    }

    @Override
    public List<Integer> randomGetNoteId(int count) {
        Jedis jedis = jedisPool.getResource();
        List<String> list = jedis.srandmember(forumNoteId, count);
        jedis.close();
        return list.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    @Override
    public void updateForumRankNote(int noteRankMaxLength) {
        Jedis jedis = jedisPool.getResource();
        List<ForumRankNote> forumRankNoteList = forumNoteMapper.selectForumRankNote(noteRankMaxLength);

        NoteRank noteRank = new NoteRank();
        noteRank.setNoteRank(forumRankNoteList);
        noteRank.setUpdateTime(new Date());

        if(forumRankNoteList.size() != 0) {
            jedis.set(forumNoteRank, JSONUtil.toJsonStr(noteRank));
        }
        jedis.close();
    }

    @Override
    public NoteRank getRangeNoteRank(int noteRankMaxLength) {
        Jedis jedis = jedisPool.getResource();
        String forumRankNoteListStr = jedis.get(forumNoteRank);
        jedis.close();
        return JSONUtil.toBean(forumRankNoteListStr, NoteRank.class);
    }
}
