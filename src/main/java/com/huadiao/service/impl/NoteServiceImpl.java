package com.huadiao.service.impl;

import com.huadiao.entity.AccountSettings;
import com.huadiao.entity.dto.note.SelfNoteDto;
import com.huadiao.entity.dto.note.ShareNoteDto;
import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.mapper.*;
import com.huadiao.service.AbstractNoteService;
import com.huadiao.service.AbstractUserSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Slf4j
@Service
public class NoteServiceImpl extends AbstractNoteService {

    private UserMapper userMapper;
    private NoteMapper noteMapper;
    private UserSettingsMapper userSettingsMapper;
    private FollowFanMapper followFanMapper;
    private JedisPool jedisPool;

    @Autowired
    public NoteServiceImpl(UserMapper userMapper, NoteMapper noteMapper, UserSettingsMapper userSettingsMapper, FollowFanMapper followFanMapper, JedisPool jedisPool) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
        this.userSettingsMapper = userSettingsMapper;
        this.followFanMapper = followFanMapper;
        this.jedisPool = jedisPool;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertNewNote(Integer uid, String userId, String noteTitle, String noteContent) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增笔记", uid, userId);
        // 检查笔记标题
        if(!checkTitle(noteTitle, uid, userId)) {
            return WRONG_NOTE_TITLE;
        }
        // 检查笔记内容, 至少不能为空
        if(!checkContent(noteContent, uid, userId)) {
            return NULL_NOTE_CONTENT;
        }
        // 笔记内容很大, 不做日志输出
        noteMapper.insertNewNoteByUid(uid, null, noteTitle, noteContent);
        log.debug("uid, userId 分别为 {}, {} 的用户新增笔记成功", uid, userId);
        return ADD_NEW_NOTE_SUCCEED;
    }

    @Override
    public Map<String, Object> getSingleNote(Integer uid, String userId, Integer authorUid, Integer noteId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的 noteId 为 {} 的笔记", uid, userId, authorUid, noteId);

        // 检查用户提供的 authorUid 是否存在
        String authorUserId = userMapper.selectUserIdByUid(authorUid);
        if(authorUserId == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 authorUid: {} 不存在", uid, userId, authorUid);
            Map<String, Object> map = new HashMap<>(2);
            map.put(WRONG_MESSAGE_KEY, NO_EXIST_UID);
            return map;
        }

        // 判断是否是本人
        boolean me = uid.equals(authorUid);
        if(!me) {
            // 检查作者是否公开笔记
            AccountSettings accountSettings = AbstractUserSettingsService.getAccountSettings(authorUid, jedisPool, userSettingsMapper);
            if(!accountSettings.getPublicNoteStatus()) {
                log.debug("uid 为 {} 的用户选择不公开笔记", authorUid);
                Map<String, Object> map = new HashMap<>(2);
                map.put(PRIVATE_SETTINGS_KEY, PRIVATE_USER_INFO);
                return map;
            }
        }

        // 查找笔记
        ShareNoteDto shareNoteDto = noteMapper.selectNoteByUidAndNoteId(authorUid, noteId);

        // 填充数据
        Map<String, Object> map = new HashMap<>(4);
        map.put("noteInfo", shareNoteDto);
        map.put("me", me);
        return map;
    }

    @Override
    public SelfNoteDto getSingleNote(Integer uid, String userId,  Integer noteId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取自己的 noteId: {} 的笔记, 用于编辑", uid, userId, noteId);
        ShareNoteDto shareNoteDto = noteMapper.selectNoteByUidAndNoteId(uid, noteId);
        SelfNoteDto selfNoteDto = new SelfNoteDto();
        if(shareNoteDto == null) {
            return selfNoteDto;
        }
        selfNoteDto.setNoteTitle(shareNoteDto.getNoteTitle());
        selfNoteDto.setNoteContent(shareNoteDto.getNoteContent());
        return selfNoteDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteNote(Integer uid, String userId, Integer noteId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除自己的 noteId 为 {} 笔记", uid, userId, noteId);
        noteMapper.deleteNoteByUidAndNoteId(uid, noteId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除自己的 noteId 为 {} 笔记", uid, userId, noteId);
        return DELETE_NOTE_SUCCEED;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String modifyNote(Integer uid, String userId, Integer noteId, String noteTitle, String noteContent) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试修改自己的 noteId: {} 的笔记", uid, userId, noteId);
        // 检查标题
        if(!checkTitle(noteTitle, uid, userId)) {
            return WRONG_NOTE_TITLE;
        }
        // 检查笔记内容
        if(!checkContent(noteContent, uid, userId)) {
            return NULL_NOTE_CONTENT;
        }
        noteMapper.insertNewNoteByUid(uid, noteId, noteTitle, noteContent);
        return MODIFY_NOTE_SUCCEED;
    }

    @Override
    public Map<String, Object> getAllNote(Integer uid, String userId, Integer authorUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的用户的所有笔记", uid, userId, authorUid);
        if(authorUid == null) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("wrongMessage", NULL_UID);
            return map;
        }

        boolean me = uid.equals(authorUid);
        // 不是本人查看是否公开笔记
        if(!me) {
            AccountSettings accountSettings = AbstractUserSettingsService.getAccountSettings(authorUid, jedisPool, userSettingsMapper);
            if(!accountSettings.getPublicNoteStatus()) {
                Map<String, Object> map = new HashMap<>(2);
                map.put(PRIVATE_SETTINGS_KEY, PRIVATE_USER_INFO);
                return map;
            }
        }

        Map<String, Object> map = new HashMap<>(4);
        List<ShareNoteDto> shareNoteDtoList = noteMapper.selectNoteDetailsByUid(authorUid);
        UserAbstractDto authorInfo = getUserInfoByUid(userMapper, followFanMapper, authorUid);

        map.put("me", me);
        map.put("authorInfo", authorInfo);
        map.put("noteList", shareNoteDtoList);
        return map;
    }

    /**
     * 检查标题是否正确
     * @param noteTitle 笔记标题
     * @return 笔记标题符合要求返回 true, 否则返回 false
     */
    private boolean checkTitle(String noteTitle, Integer uid, String userId) {
        if(noteTitle != null && noteTitle.length() >= MIN_NOTE_TITLE_LENGTH && noteTitle.length() <= MAX_NOTE_TITLE_LENGTH) {
            return true;
        }
        log.debug("uid, userId 分别为 {}, {} 的用户提供的 noteTitle 存在问题: {}", uid, userId, noteTitle);
        return false;
    }

    /**
     * 检查笔记内容是否符合要求
     * @param noteContent 笔记内容
     * @return 笔记内容符合要求返回 true, 否则返回 false
     */
    private boolean checkContent(String noteContent, Integer uid, String userId) {
        if(noteContent != null) {
            return true;
        }
        log.debug("uid, userId 分别为 {}, {} 的用户提供的笔记内容为 null", uid, userId);
        return false;
    }
}
