package com.huadiao.service.impl;

import com.huadiao.mapper.NoteMapper;
import com.huadiao.mapper.NoteStarMapper;
import com.huadiao.service.AbstractNoteStarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Slf4j
@Service
public class NoteStarServiceImpl extends AbstractNoteStarService {
    private NoteStarMapper noteStarMapper;
    private NoteMapper noteMapper;

    @Autowired
    public NoteStarServiceImpl(NoteStarMapper noteStarMapper, NoteMapper noteMapper) {
        this.noteStarMapper = noteStarMapper;
        this.noteMapper = noteMapper;
    }

    @Override
    public String addNewNoteStar(Integer uid, String userId, Integer noteId, Integer authorUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增笔记收藏, noteId: {}, authorUid: {}", uid, userId, noteId, authorUid);
        // 获取笔记收藏数量
        Integer starAmount = starJedisUtil.getUserStarAmount(uid);
        if(starAmount >= MAX_NOTE_STAR_VALUE) {
            log.debug("uid, userId 分别为 {}, {} 为用户笔记收藏已达到最大数量", uid, userId);
            return ATTACH_MAX_NOTE_STAR;
        }
        // 查询用户笔记是否存在
        Boolean exist = noteMapper.selectExistByNoteIdAndUid(authorUid, noteId);
        if(!exist) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 authorUid: {}, noteId: {} 不存在", uid, userId, authorUid, noteId);
            return NOTE_NOT_EXIST;
        }
        noteStarMapper.insertNoteStar(uid, noteId, authorUid);
        log.debug("uid, userId 分别为 {}, {} 的用户新增用户 authorUid: {} 的 noteId: {} 的笔记收藏成功", uid, userId, authorUid, noteId);
        return ADD_NOTE_STAR_SUCCEED;
    }

    @Override
    public String deleteNoteStar(Integer uid, String userId, Integer noteId, Integer authorUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除关于 authorUid: {} 的笔记 id 为 {}的笔记收藏", uid, userId, authorUid, noteId);
        noteStarMapper.deleteNoteStar(uid, noteId, authorUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除关于 authorUid: {} 的笔记 id 为 {}的笔记收藏", uid, userId, authorUid, noteId);
        return DELETE_NOTE_STAR_SUCCEED;
    }
}
