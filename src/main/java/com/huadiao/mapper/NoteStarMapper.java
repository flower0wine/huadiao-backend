package com.huadiao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 映射文件, 与用户笔记收藏相关
 * @author flowewine
 */
public interface NoteStarMapper {
    /**
     * 新增笔记收藏或者再次收藏笔记
     * @param uid 用户 uid
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     */
    void insertNoteStar(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 删除笔记收藏
     * @param uid 用户 uid
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     */
    void deleteNoteStar(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 获取用户笔记收藏数量
     * @param uid 用户 uid
     * @return 返回笔记收藏数量
     */
    Integer countNoteStarByUid(@Param("uid") Integer uid);
}
