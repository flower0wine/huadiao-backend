package com.huadiao.mapper;

import com.huadiao.entity.NoteHistory;
import com.huadiao.entity.dto.note.NoteCommentDto;
import com.huadiao.entity.dto.note.NoteRelationDto;
import com.huadiao.entity.dto.note.ShareNoteDto;
import com.huadiao.entity.note.Note;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mapper 映射文件, 笔记相关
 *
 * @author flowerwine
 */

public interface NoteMapper {

    /**
     * 根据 uid 新增笔记或者修改笔记
     *
     * @param uid          用户 uid
     * @param noteId       笔记 id
     * @param noteTitle    笔记标题
     * @param noteAbstract 笔记摘要
     * @param noteContent  笔记内容
     */
    void insertNewNoteByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("noteTitle") String noteTitle,
                            @Param("noteAbstract") String noteAbstract, @Param("noteContent") String noteContent);

    /**
     * 根据笔记 id 和用户 uid 删除笔记
     *
     * @param uid    用户 uid
     * @param noteId 笔记 唯一标识
     */
    void deleteNoteByUidAndNoteId(@Param("uid") Integer uid, @Param("noteId") Integer noteId);

    /**
     * 根据 uid 和笔记唯一标识获取笔记
     *
     * @param uid    用户 uid
     * @param noteId 笔记唯一标识
     * @return 返回查找到的笔记
     */
    Note selectNoteByUidAndNoteId(@Param("uid") Integer uid, @Param("noteId") Integer noteId);

    /**
     * 根据 uid 和 笔记 id 来判断笔记是否存在, 不存在时返回 null, 存在时返回 数字
     *
     * @param uid    作者 uid
     * @param noteId 笔记 id
     * @return 返回是否查找到的结果
     */
    Integer selectExistByNoteIdAndUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId);

    /**
     * 获取用户的笔记总数
     *
     * @param uid 用户 uid
     * @return 返回笔记数量
     */
    Integer countNoteByUid(@Param("uid") Integer uid);

    /**
     * 返回指定用户的全部笔记
     *
     * @param uid 用户 uid
     * @return 返回指定用户全部笔记
     */
    List<ShareNoteDto> selectAllNoteByUid(@Param("uid") Integer uid);

    /**
     * 根据笔记被喜欢次数
     *
     * @param uid    作者 uid
     * @param noteId 笔记 id
     * @return 笔记喜欢次数
     */
    Integer countNoteLikeByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId);

    /**
     * 获取笔记被不喜欢
     *
     * @param authorUid 作者 uid
     * @param noteId    笔记 id
     * @return 笔记被不喜欢次数
     */
    Integer countNoteUnlikeByUid(@Param("authorUid") Integer authorUid, @Param("noteId") Integer noteId);

    /**
     * 获取笔记被访问次数
     *
     * @param authorUid 作者 uid
     * @param noteId    笔记 id
     * @return 笔记被访问次数
     */
    Integer countNoteViewByUid(@Param("authorUid") Integer authorUid, @Param("noteId") Integer noteId);

    /**
     * 获取用户与笔记的关系
     *
     * @param uid       用户 uid
     * @param noteId    笔记 id
     * @param authorUid 作者 uid
     * @return 返回笔记与用户的关系
     */
    NoteRelationDto selectNoteRelationByUidAndNoteId(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 判断当前用户是否点赞过某个笔记
     *
     * @param uid       当前用户的 uid
     * @param noteId    笔记 id
     * @param authorUid 作者 uid
     * @return 点赞过返回当前用户 uid, 否则返回 null
     */
    Integer selectMyLikeWithNote(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 判断当前用户是否不喜欢过某个笔记
     *
     * @param uid       当前用户的 uid
     * @param noteId    笔记 id
     * @param authorUid 作者 uid
     * @return 不喜欢了返回当前用户 uid, 否则返回 null
     */
    Integer selectMyUnlikeWithNote(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 判断当前用户是否收藏了某个笔记
     *
     * @param uid       当前用户 uid
     * @param noteId    笔记 id
     * @param authorUid 作者 uid
     * @return 收藏返回当前用户 uid, 否则返回 null
     */
    Integer selectMyStarWithNote(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid);

    /**
     * 判断作者 uid 是否存在 noteId 的笔记
     *
     * @param uid    作者 uid
     * @param noteId 笔记 id
     * @return 如果存在该笔记返回作者 uid, 否则返回 null
     */
    Integer judgeNoteExist(@Param("uid") Integer uid, @Param("noteId") Integer noteId);

    /**
     * 判断作者 uid 的笔记 id 中是否有某条评论
     *
     * @param uid           评论的用户 uid
     * @param authorUid     作者 uid
     * @param noteId        笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId  子评论 id
     * @return 找到返回 1, 没有找到返回 null
     */
    Integer judgeNoteCommentExist(@Param("uid") Integer uid, @Param("authorUid") Integer authorUid, @Param("noteId") Integer noteId, @Param("rootCommentId") Long rootCommentId,
                                  @Param("subCommentId") Long subCommentId);

    /**
     * 获取指定用户的指定笔记的评论
     *
     * @param uid       用户 uid
     * @param noteId    笔记 id
     * @param authorUid 作者 uid
     * @param offset    偏移量
     * @param row       一页有多少条数据
     * @return 返回评论
     */
    List<NoteCommentDto> selectNoteCommentByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId,
                                                @Param("authorUid") Integer authorUid, @Param("offset") Integer offset,
                                                @Param("row") Integer row);

    /**
     * 返回笔记评论总数
     *
     * @param noteId 笔记 id
     * @param uid    作者 uid
     * @return 返回笔记评论数
     */
    Integer countAllNoteCommentByUidNoteId(@Param("noteId") Integer noteId, @Param("uid") Integer uid);

    /**
     * 新增笔记评论
     *
     * @param uid            评论的用户 uid
     * @param noteId         笔记 id
     * @param replyUid       被回复的用户 uid
     * @param authorUid      作者 uid
     * @param rootCommentId  父评论 id
     * @param subCommentId   子评论 id
     * @param commentContent 评论内容
     */
    void insertNoteCommentByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("replyUid") Integer replyUid,
                                @Param("authorUid") Integer authorUid, @Param("rootCommentId") Long rootCommentId,
                                @Param("subCommentId") Long subCommentId, @Param("commentContent") String commentContent);

    /**
     * 删除笔记评论
     *
     * @param uid           评论用户的 uid
     * @param noteId        笔记 id
     * @param authorUid     作者 uid
     * @param rootCommentId 父评论 id
     * @param subCommentId  子评论 id
     */
    void deleteNoteCommentByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("authorUid") Integer authorUid,
                                @Param("rootCommentId") Long rootCommentId, @Param("subCommentId") Long subCommentId);

    /**
     * 获取用户的所有笔记收藏数量
     *
     * @param uid 用户 uid
     * @return 返回笔记收藏总数
     */
    Integer selectAllNoteStarCountByUid(@Param("uid") Integer uid);

    /**
     * 获取用户所有的笔记被点赞的数量
     *
     * @param uid 用户 uid
     * @return 返回所有笔记点赞总数
     */
    Integer selectAllNoteLikeCountByUid(@Param("uid") Integer uid);

    /**
     * 获取笔记某条评论被点赞数
     *
     * @param uid           用户 uid
     * @param noteId        笔记 id
     * @param rootCommentId 父评论 id
     * @param subCommentId  子评论 id
     * @return 返回评论被点赞数
     */
    Integer countCommentLikeByUid(@Param("uid") Integer uid, @Param("noteId") Integer noteId, @Param("rootCommentId") Integer rootCommentId,
                                  @Param("subCommentId") Integer subCommentId);

}
