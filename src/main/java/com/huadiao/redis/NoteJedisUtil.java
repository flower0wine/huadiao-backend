package com.huadiao.redis;

public interface NoteJedisUtil {
    /**
     * 生成唯一递增评论 id, 与 getCommentId 不同的是它既会获取, 也会增加 id
     *
     * @return 评论 id
     */
    int generateCommentId();

    /**
     * 获取当前的评论 id, 与 generateCommentId 不同的是它不会增加 id, 只是获取
     *
     * @return 评论 id
     */
    int getCommentId();

    /**
     * 生成唯一递增笔记 id
     *
     * @return 笔记 id
     */
    int generateNoteId();
}
