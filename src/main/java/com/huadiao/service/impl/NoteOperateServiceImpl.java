package com.huadiao.service.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.ResultCodeEnum;
import com.huadiao.mapper.NoteMapper;
import com.huadiao.mapper.NoteOperateMapper;
import com.huadiao.service.AbstractNoteOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Slf4j
@Service
public class NoteOperateServiceImpl extends AbstractNoteOperateService {
    private NoteOperateMapper noteOperateMapper;
    private NoteMapper noteMapper;

    @Autowired
    public NoteOperateServiceImpl(NoteOperateMapper noteOperateMapper, NoteMapper noteMapper) {
        this.noteOperateMapper = noteOperateMapper;
        this.noteMapper = noteMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addNewNoteStar(Integer uid, String userId, Integer noteId, Integer authorUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增笔记收藏, noteId: {}, authorUid: {}", uid, userId, noteId, authorUid);
        // 获取笔记收藏数量
        Integer starAmount = starJedisUtil.getUserStarAmount(uid);
        if(starAmount >= MAX_NOTE_STAR_VALUE) {
            log.debug("uid, userId 分别为 {}, {} 为用户笔记收藏已达到最大数量", uid, userId);
            return ATTACH_MAX_NOTE_STAR;
        }
        // 查询用户笔记是否存在
        Integer exist = noteMapper.selectExistByNoteIdAndUid(authorUid, noteId);
        if(exist == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 authorUid: {}, noteId: {} 不存在", uid, userId, authorUid, noteId);
            return NOTE_NOT_EXIST;
        }
        noteOperateMapper.insertNoteStarByUid(uid, noteId, authorUid);
        log.debug("uid, userId 分别为 {}, {} 的用户新增用户 authorUid: {} 的 noteId: {} 的笔记收藏成功", uid, userId, authorUid, noteId);
        return ADD_NOTE_STAR_SUCCEED;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteNoteStar(Integer uid, String userId, Integer noteId, Integer authorUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除关于 authorUid: {} 的笔记 id 为 {}的笔记收藏", uid, userId, authorUid, noteId);
        noteOperateMapper.deleteNoteStarByUid(uid, noteId, authorUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除关于 authorUid: {} 的笔记 id 为 {}的笔记收藏", uid, userId, authorUid, noteId);
        return DELETE_NOTE_STAR_SUCCEED;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addNoteUnlike(Integer uid, String userId, Integer noteId, Integer authorUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增对用户 uid 为 {} 的笔记 id 为 {} 的不喜欢", uid, userId, authorUid, noteId);
        Integer exist = noteMapper.selectExistByNoteIdAndUid(authorUid, noteId);
        if(exist == null) {
            log.debug("uid, userId 为 {}, {} 的用户提供的 uid 为 {} 的用户的 id 为 {} 的笔记不存在", uid, userId, authorUid, noteId);
            return NOTE_NOT_EXIST;
        }
        noteOperateMapper.insertNoteUnlikeByUid(uid, noteId, authorUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功新增对用户 uid 为 {} 的笔记 id 为 {} 的不喜欢", uid, userId, authorUid, noteId);
        return ADD_NOTE_UNLIKE_SUCCEED;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteNoteUnlike(Integer uid, String userId, Integer noteId, Integer authorUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除对 uid 为 {} 的用户的 id 为 {} 的笔记的不喜欢", uid, userId, authorUid, noteId);
        noteOperateMapper.deleteNoteUnlikeByUid(uid, noteId, authorUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除对 uid 为 {} 的用户的 id 为 {} 的笔记的不喜欢", uid, userId, authorUid, noteId);
        return DELETE_NOTE_UNLIKE_SUCCEED;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addNoteLike(Integer uid, String userId, Integer authorUid, Integer noteId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试对 uid 为 {} 的用户的 noteId 为 {} 的笔记进行点赞", uid, userId, authorUid, noteId);
        Integer exist = noteMapper.selectExistByNoteIdAndUid(authorUid, noteId);
        if(exist == null) {
            log.debug("uid, userId 为 {}, {} 的用户提供的 uid 为 {} 的用户的 id 为 {} 的笔记不存在", uid, userId, authorUid, noteId);
            return NOTE_NOT_EXIST;
        }
        noteOperateMapper.insertNoteLikeByUid(uid, noteId, authorUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功对 uid 为 {} 的用户的 noteId 为 {} 的笔记进行点赞", uid, userId, authorUid, noteId);
        return ADD_NOTE_LIKE_SUCCEED;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteNoteLike(Integer uid, String userId, Integer authorUid, Integer noteId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除对 uid 为 {} 的用户的 noteId 为 {} 的笔记的点赞", uid, userId, authorUid, noteId);
        noteOperateMapper.deleteNoteLikeByUid(uid, noteId, authorUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除对 uid 为 {} 的用户的 noteId 为 {} 的笔记的点赞", uid, userId, authorUid, noteId);
        return DELETE_NOTE_LIKE_SUCCEED;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> addNoteCommentLike(Integer uid, String userId, Integer noteId, Integer authorUid, Long rootCommentId, Long subCommentId) {
        log.debug("uid, userId 为 {}, {} 的用户尝试对用户 uid 为 {} 的笔记 noteId为 {} 的评论点赞, rootCommentId 为 {}, subCommentId 为 {}", uid, userId, authorUid, noteId, rootCommentId, subCommentId);
        // 查询对应的笔记评论是否存在
        Integer commentExist = noteOperateMapper.selectNoteCommentExist(noteId, authorUid, rootCommentId, subCommentId);
        if(commentExist == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的参数对应的评论不存在, 参数分别为 authorUid: {}, noteId: {}, rootCommentId: {}, subCommentId: {}", uid, userId, authorUid, noteId, rootCommentId, subCommentId);
            return new Result<>(ResultCodeEnum.NOT_EXIST, null);
        }
        // 为父评论时, 设置为 0L
        if(subCommentId == null) {
            subCommentId = UNDISTRIBUTED_COMMENT_ID;
        }
        noteOperateMapper.insertNoteCommentLike(uid, noteId, authorUid, rootCommentId, subCommentId);
        log.debug("uid, userId 为 {}, {} 的用户成功对用户 uid 为 {} 的笔记 noteId为 {} 的评论点赞, rootCommentId 为 {}, subCommentId 为 {}", uid, userId, authorUid, noteId, rootCommentId, subCommentId);
        return Result.ok(ADD_NOTE_COMMENT_LIKE_SUCCEED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> deleteNoteCommentLike(Integer uid, String userId, Integer noteId, Integer authorUid, Long rootCommentId, Long subCommentId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除对用户 uid 为 {} 的笔记 noteId为 {} 的评论点赞, rootCommentId 为 {}, subCommentId 为 {}", uid, userId, authorUid, noteId, rootCommentId, subCommentId);
        if(subCommentId == null) {
            subCommentId = UNDISTRIBUTED_COMMENT_ID;
        }
        noteOperateMapper.deleteNoteCommentLike(uid, noteId, authorUid, rootCommentId, subCommentId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除对用户 uid 为 {} 的笔记 noteId为 {} 的评论点赞, rootCommentId 为 {}, subCommentId 为 {}", uid, userId, authorUid, noteId, rootCommentId, subCommentId);
        return Result.ok(DELETE_NOTE_LIKE_SUCCEED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> addNoteCommentUnlike(Integer uid, String userId, Integer noteId, Integer authorUid, Long rootCommentId, Long subCommentId) {
        log.debug("uid, userId 为 {}, {} 的用户尝试对用户 uid 为 {} 的笔记 noteId为 {} 的评论设置为不喜欢, rootCommentId 为 {}, subCommentId 为 {}", uid, userId, authorUid, noteId, rootCommentId, subCommentId);
        // 查询对应的笔记评论是否存在
        Integer commentExist = noteOperateMapper.selectNoteCommentExist(noteId, authorUid, rootCommentId, subCommentId);
        if(commentExist == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的参数对应的评论不存在, 参数分别为 authorUid: {}, noteId: {}, rootCommentId: {}, subCommentId: {}", uid, userId, authorUid, noteId, rootCommentId, subCommentId);
            return new Result<>(ResultCodeEnum.NOT_EXIST, null);
        }
        // 为父评论时, 设置为 0L
        if(subCommentId == null) {
            subCommentId = UNDISTRIBUTED_COMMENT_ID;
        }
        noteOperateMapper.insertNoteCommentUnlike(uid, noteId, authorUid, rootCommentId, subCommentId);
        log.debug("uid, userId 为 {}, {} 的用户成功对用户 uid 为 {} 的笔记 noteId为 {} 的评论设置为不喜欢, rootCommentId 为 {}, subCommentId 为 {}", uid, userId, authorUid, noteId, rootCommentId, subCommentId);
        return Result.ok(ADD_NOTE_COMMENT_LIKE_SUCCEED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> deleteNoteCommentUnlike(Integer uid, String userId, Integer noteId, Integer authorUid, Long rootCommentId, Long subCommentId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除对用户 uid 为 {} 的笔记 noteId为 {} 的评论不喜欢, rootCommentId 为 {}, subCommentId 为 {}", uid, userId, authorUid, noteId, rootCommentId, subCommentId);
        if(subCommentId == null) {
            subCommentId = UNDISTRIBUTED_COMMENT_ID;
        }
        noteOperateMapper.deleteNoteCommentUnlike(uid, noteId, authorUid, rootCommentId, subCommentId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除对用户 uid 为 {} 的笔记 noteId为 {} 的评论不喜欢, rootCommentId 为 {}, subCommentId 为 {}", uid, userId, authorUid, noteId, rootCommentId, subCommentId);
        return Result.ok(DELETE_NOTE_COMMENT_UNLIKE_SUCCEED);
    }
}
