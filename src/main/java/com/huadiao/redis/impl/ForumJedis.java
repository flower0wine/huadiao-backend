package com.huadiao.redis.impl;

import cn.hutool.json.JSONUtil;
import com.huadiao.entity.note.ForumRankNote;
import com.huadiao.redis.AbstractJedis;
import com.huadiao.redis.ForumJedisUtil;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
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
        long len = jedis.llen(forumNoteRank);
        if(len != 0L) {
            jedis.del(forumNoteRank);
        }
        List<ForumRankNote> forumRankNoteList = forumNoteMapper.selectForumRankNote(noteRankMaxLength);
        jedis.rpush(forumNoteRank, forumRankNoteList.stream().map(JSONUtil::toJsonStr).toArray(String[]::new));
        jedis.close();
    }

    @Override
    public List<ForumRankNote> getRangeNoteRank(int noteRankMaxLength) {
        Jedis jedis = jedisPool.getResource();
        List<String> forumRankNoteList = jedis.lrange(forumNoteRank, 0, noteRankMaxLength);
        jedis.close();
        return forumRankNoteList.stream().map((item) -> JSONUtil.toBean(item, ForumRankNote.class)).collect(Collectors.toList());
    }
}
