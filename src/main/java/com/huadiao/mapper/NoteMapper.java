package com.huadiao.mapper;

import com.huadiao.entity.dto.note.ShareNoteDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mapper 映射文件, 笔记相关
 * @author flowerwine
 */

public interface NoteMapper {

    /**
     * 根据 uid 新增笔记或者修改笔记
     * @param uid 用户 uid
     * @param noteTitle 笔记标题
     * @param noteContent 笔记内容
     */
    void insertNewNoteByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("noteTitle") String noteTitle, @Param("noteContent") String noteContent);

    /**
     * 根据笔记 id 和用户 uid 删除笔记
     * @param uid 用户 uid
     * @param noteId 笔记 唯一标识
     */
    void deleteNoteByUidAndNoteId(@Param("uid") Integer uid, @Param("noteId") Integer noteId);

    /**
     * 根据 uid 和笔记唯一标识获取笔记
     * @param uid 用户 uid
     * @param noteId 笔记唯一标识
     * @return 返回查找到的笔记
     */
    ShareNoteDto selectNoteByUidAndNoteId(@Param("uid") Integer uid, @Param("noteId") Integer noteId);

    /**
     * 获取用户的笔记总数
     * @param uid 用户 uid
     * @return 返回笔记数量
     */
    Integer countNoteByUid(@Param("uid") Integer uid);

    /**
     * 返回指定用户的全部笔记
     * @param uid 用户 uid
     * @return 返回指定用户全部笔记
     */
    List<ShareNoteDto> selectNoteDetailsByUid(@Param("uid") Integer uid);

    /**
     * 根据笔记被喜欢次数
     * @param authorUid 作者 uid
     * @param noteId 笔记 id
     * @return 笔记喜欢次数
     */
    Integer countNoteLikeByUid(@Param("authorUid") Integer authorUid, @Param("noteId") Integer noteId);

    /**
     * 获取笔记被不喜欢
     * @param authorUid 作者 uid
     * @param noteId 笔记 id
     * @return 笔记被不喜欢次数
     */
    Integer countNoteUnlikeByUid(@Param("authorUid") Integer authorUid, @Param("noteId") Integer noteId);

    /**
     * 获取笔记被访问次数
     * @param authorUid 作者 uid
     * @param noteId 笔记 id
     * @return 笔记被访问次数
     */
    Integer countNoteViewByUid(@Param("authorUid") Integer authorUid, @Param("noteId") Integer noteId);
}
