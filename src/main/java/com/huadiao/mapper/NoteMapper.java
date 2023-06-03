package com.huadiao.mapper;

import com.huadiao.entity.dto.note.NoteRelationDto;
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
     * @param noteId 笔记 id
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
     * 根据 uid 和 笔记 id 来判断笔记是否存在, 由于返回类型为 bool, 所以要求 sql 语句只能返回 0 和 1 两个数字
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @return 返回是否查找到的结果
     */
    Boolean selectExistByNoteIdAndUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId);

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
    List<ShareNoteDto> selectAllNoteByUid(@Param("uid") Integer uid);

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

    /**
     * 判断当前用户是否点赞过某个笔记
     * @param uid 当前用户的 uid
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @return 点赞过返回当前用户 uid, 否则返回 null
     */
    Integer selectMyLikeWithNote(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 判断当前用户是否不喜欢过某个笔记
     * @param uid 当前用户的 uid
     * @param noteId 笔记 id
     * @param authorUid 作者 uid
     * @return 不喜欢过返回当前用户 uid, 否则返回 null
     */
    Integer selectMyUnlikeWithNote(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 判断作者 uid 是否存在 noteId 的笔记
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @return 如果存在该笔记返回作者 uid, 否则返回 null
     */
    Integer selectAuthorUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId);

}
