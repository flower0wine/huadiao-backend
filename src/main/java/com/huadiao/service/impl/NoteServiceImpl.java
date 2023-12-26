package com.huadiao.service.impl;

import com.huadiao.entity.account.AccountSettings;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.followfan.BothRelationDto;
import com.huadiao.entity.dto.note.NoteCommentDto;
import com.huadiao.entity.dto.note.NoteRelationDto;
import com.huadiao.entity.dto.note.SelfNoteDto;
import com.huadiao.entity.dto.note.ShareNoteDto;
import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.entity.elasticsearch.NoteEs;
import com.huadiao.entity.elasticsearch.NoteHistoryEs;
import com.huadiao.entity.note.Note;
import com.huadiao.mapper.*;
import com.huadiao.service.AbstractFollowFanService;
import com.huadiao.service.AbstractNoteService;
import com.huadiao.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    private NoteOperateMapper noteOperateMapper;
    private HistoryMapper historyMapper;

    @Autowired
    public NoteServiceImpl(UserMapper userMapper, NoteMapper noteMapper, UserSettingsMapper userSettingsMapper, FollowFanMapper followFanMapper, NoteOperateMapper noteOperateMapper, HistoryMapper historyMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
        this.userSettingsMapper = userSettingsMapper;
        this.followFanMapper = followFanMapper;
        this.noteOperateMapper = noteOperateMapper;
        this.historyMapper = historyMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> insertNewNote(Integer uid, String userId, String noteSummary, String noteTitle, String noteContent) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增笔记, noteSummary: {}", uid, userId, noteSummary);
        if (!checkNoteParams(noteTitle, noteContent, noteSummary, uid, userId)) {
            return Result.errorParam();
        }
        // 生成笔记 id
        int noteId = noteJedisUtil.generateNoteId();
        // 笔记内容很大, 不做日志输出
        noteMapper.insertNewNoteByUid(uid, noteId, noteTitle, noteSummary, noteContent);
        // 保存到首页论坛
        forumJedisUtil.addNoteId(noteId);
        // 保存笔记信息到 elasticsearch
        NoteEs noteEs = new NoteEs();
        noteEs.setNoteId(noteId);
        noteEs.setSummary(noteSummary);
        noteEs.setTitle(noteTitle);
        noteEs.setAuthorUid(uid);
        noteEs.setTime(new Date());
        noteRepository.save(noteEs);
        log.debug("uid, userId 分别为 {}, {} 的用户新增笔记成功, noteSummary: {}", uid, userId, noteSummary);
        return Result.ok(null);
    }

    @Override
    public Result<?> getSingleNote(Integer uid, String userId, Integer authorUid, Integer noteId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的 noteId 为 {} 的笔记", uid, userId, authorUid, noteId);
        if (authorUid == null || noteId == null) {
            return Result.errorParam();
        }
        // 检查用户提供的 authorUid 和 笔记 id 是否存在
        Integer queryAuthorUid = noteMapper.judgeNoteExist(authorUid, noteId);
        if (queryAuthorUid == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 authorUid: {} 并不存在 noteId: {} 的笔记", uid, userId, authorUid, noteId);
            return Result.notExist();
        }

        // 判断是否是本人
        boolean me = authorUid.equals(uid);
        if (!me) {
            // 检查作者是否公开笔记
            AccountSettings accountSettings = userSettingJedisUtil.getAccountSettings(authorUid);
            if (!accountSettings.getPublicNoteStatus()) {
                log.debug("uid 为 {} 的用户选择不公开笔记", authorUid);
                return Result.notAllowed();
            }
        }

        // 查找笔记
        Note note = noteMapper.selectNoteByUidAndNoteId(authorUid, noteId);
        // 查找作者信息
        UserAbstractDto authorInfo = userInfoJedisUtil.getUserAbstractDto(authorUid);
        BothRelationDto relation = null;
        NoteRelationDto noteRelationDto = null;
        // 由于笔记允许未登录访问, 所以 uid 可能为 null
        if(uid != null) {
            // 查找我和作者的关系
            relation = AbstractFollowFanService.judgeRelationBetweenBoth(followFanMapper.selectRelationByBothUid(uid, authorUid));
            // 查找我和笔记的关系
            noteRelationDto = new NoteRelationDto();
            noteRelationDto.setMyLike(uid.equals(noteMapper.selectMyLikeWithNote(uid, noteId, authorUid)));
            noteRelationDto.setMyUnlike(uid.equals(noteMapper.selectMyUnlikeWithNote(uid, noteId, authorUid)));
            noteRelationDto.setMyStar(uid.equals(noteMapper.selectMyStarWithNote(uid, noteId, authorUid)));
            // 新增笔记浏览记录, 一个人一条浏览记录, 不包含作者本身
            if (!uid.equals(authorUid)) {
                addNewNoteView(uid, userId, noteId, authorUid);
                // 保存笔记历史到 es
                NoteHistoryEs noteHistoryEs = new NoteHistoryEs();
                noteHistoryEs.setAuthorUid(authorUid);
                noteHistoryEs.setNoteId(noteId);
                noteHistoryEs.setNoteTitle(note.getNoteTitle());
                noteHistoryEs.setUid(uid);
                noteHistoryEs.setCompositionId(uid,  noteId);
                noteHistoryEs.setTime(new Date());
                noteHistoryRepository.save(noteHistoryEs);
            }
        }

        // 填充数据
        Map<String, Object> map = new HashMap<>(32);
        map.put("me", me);
        map.put("noteTitle", note.getNoteTitle());
        map.put("noteContent", note.getNoteContent());
        map.put("viewCount", note.getViewCount());
        map.put("likeCount", note.getLikeCount());
        map.put("starCount", note.getStarCount());
        map.put("commentCount", note.getCommentCount());
        map.put("publishTime", note.getPublishTime());
        map.put("authorAndMeRelation", relation);
        map.put("noteAndMeRelation", noteRelationDto);
        return Result.ok(map);
    }

    @Override
    public Result<?> getNoteComment(Integer uid, String userId, Integer authorUid, Integer noteId, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取用户 uid 为 {} 的笔记 noteId 为 {} 的评论, 获取页码为 {}, 行数为 {}", uid, userId, authorUid, noteId, offset, row);
        if (offset == null || offset < 0 || row == null || row < 0 || row > MAX_ROW) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的参数有误, page 为 {}, row 为 {}", uid, userId, offset, row);
            return Result.errorParam();
        }
        List<NoteCommentDto> noteCommentDtoList = noteMapper.selectNoteCommentByUid(uid, noteId, authorUid, offset, row);
        // 由于本身的水平原因, 需要对查询的数据进一步处理
        noteCommentDtoList.forEach((commentDto) -> {
            List<NoteCommentDto> commentList = commentDto.getCommentList();
            List<NoteCommentDto> collect = commentList.stream().filter((noteComment) -> {
                if (noteComment.getCommentId() == 0) {
                    commentDto.setCommentContent(noteComment.getCommentContent());
                    commentDto.setMyLike(noteComment.getMyLike());
                    commentDto.setMyUnlike(noteComment.getMyUnlike());
                    commentDto.setLikeNumber(noteComment.getLikeNumber());
                    commentDto.setNickname(noteComment.getNickname());
                    commentDto.setUid(noteComment.getUid());
                    commentDto.setUserId(noteComment.getUserId());
                    commentDto.setUserAvatar(noteComment.getUserAvatar());
                    commentDto.setCommentDate(noteComment.getCommentDate());
                }
                return noteComment.getCommentId() != 0;
            }).collect(Collectors.toList());
            commentDto.setCommentList(collect);
        });
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取用户 uid 为 {} 的笔记 noteId 为 {} 的评论, 获取页码为 {}, 指定获取行数为 {}, 实际获取行数为 {}", uid, userId, authorUid, noteId, offset, row, noteCommentDtoList.size());
        if (noteCommentDtoList.size() == 0) {
            return Result.notExist();
        }
        return Result.ok(noteCommentDtoList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addNewNoteView(Integer uid, String userId, Integer noteId, Integer authorUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增对用户 uid 为 {} 的笔记 noteId 为 {} 的访问记录", uid, userId, authorUid, noteId);
        historyMapper.insertNoteViewByUid(uid, noteId, authorUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功新增对用户 uid 为 {} 的笔记 noteId 为 {} 的访问记录", uid, userId, authorUid, noteId);
    }

    @Override
    public SelfNoteDto getSingleNote(Integer uid, String userId, Integer noteId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取自己的 noteId: {} 的笔记, 用于编辑", uid, userId, noteId);
        Note note = noteMapper.selectNoteByUidAndNoteId(uid, noteId);
        SelfNoteDto selfNoteDto = new SelfNoteDto();
        if (note == null) {
            return selfNoteDto;
        }
        selfNoteDto.setNoteTitle(note.getNoteTitle());
        selfNoteDto.setNoteContent(note.getNoteContent());
        return selfNoteDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteNote(Integer uid, String userId, Integer noteId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除自己的 noteId 为 {} 笔记", uid, userId, noteId);
        noteMapper.deleteNoteByUidAndNoteId(uid, noteId);
        noteRepository.deleteById(noteId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除自己的 noteId 为 {} 笔记", uid, userId, noteId);
        return DELETE_NOTE_SUCCEED;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> modifyNote(Integer uid, String userId, Integer noteId, String noteTitle, String noteSummary, String noteContent) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试修改自己的 noteId: {} 的笔记", uid, userId, noteId);
        if (!checkNoteParams(noteTitle, noteContent, noteSummary, uid, userId)) {
            return Result.errorParam();
        }
        noteMapper.insertNewNoteByUid(uid, noteId, noteTitle, noteSummary, noteContent);
        // 修改 elasticsearch 中的笔记信息
        NoteEs sourceNoteEs;
        Optional<NoteEs> noteEsOptional = noteRepository.findById(noteId);
        NoteEs noteEs = new NoteEs();
        noteEs.setNoteId(noteId);
        noteEs.setTitle(noteTitle);
        noteEs.setSummary(noteSummary);
        if (noteEsOptional.isPresent()) {
            sourceNoteEs = noteEsOptional.get();
            BeanUtil.moveProperties(noteEs, sourceNoteEs);
        }
        noteRepository.save(noteEs);
        return Result.ok(null);
    }

    @Override
    public Result<?> getAllNote(Integer uid, String userId, Integer authorUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的用户的所有笔记", uid, userId, authorUid);
        String authorUserId = userMapper.selectUserIdByUid(authorUid);
        if (authorUserId == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 authorUid: {} 不存在", uid, userId, authorUid);
            return Result.notExist();
        }

        if (authorUid == null) {
            log.debug("uid 为 {} 提供的 authorUid: {} 不存在", uid, authorUid);
            return Result.notExist();
        }

        boolean me = authorUid.equals(uid);
        // 不是本人查看是否公开笔记
        if (!me) {
            AccountSettings accountSettings = userSettingJedisUtil.getAccountSettings(authorUid);
            if (!accountSettings.getPublicNoteStatus()) {
                log.debug("uid 为 {} 的用户不公开笔记", authorUid);
                return Result.notAllowed();
            }
        }

        List<ShareNoteDto> shareNoteDtoList = noteMapper.selectAllNoteByUid(authorUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取 uid 为 {} 的用户的所有笔记", uid, userId, authorUid);
        return Result.ok(shareNoteDtoList);
    }

    /**
     * 检查标题是否正确
     *
     * @param noteTitle 笔记标题
     * @return 笔记标题符合要求返回 true, 否则返回 false
     */
    private boolean checkTitle(String noteTitle, Integer uid, String userId) {
        if (noteTitle != null && noteTitle.length() >= MIN_NOTE_TITLE_LENGTH && noteTitle.length() <= MAX_NOTE_TITLE_LENGTH) {
            return true;
        }
        log.debug("uid, userId 分别为 {}, {} 的用户提供的 noteTitle 存在问题: {}", uid, userId, noteTitle);
        return false;
    }

    /**
     * 检查笔记内容是否符合要求
     *
     * @param noteContent 笔记内容
     * @return 笔记内容符合要求返回 true, 否则返回 false
     */
    private boolean checkContent(String noteContent, Integer uid, String userId) {
        if (noteContent != null) {
            return true;
        }
        log.debug("uid, userId 分别为 {}, {} 的用户提供的笔记内容为 null", uid, userId);
        return false;
    }

    /**
     * 检查笔记摘要是否存在问题
     *
     * @param noteSummary 笔记摘要
     * @param uid         用户 uid
     * @param userId      用户 id
     * @return 符合要求返回 true, 否则返回 false
     */
    private boolean checkSummary(String noteSummary, Integer uid, String userId) {
        if (noteSummary != null && noteSummaryMinLength <= noteSummary.length() && noteSummary.length() <= noteSummaryMaxLength) {
            return true;
        }
        int noteSummaryLength = noteSummary == null ? 0 : noteSummary.length();
        log.debug("uid, userId 分别为 {}, {} 的用户提供的 noteSummary 长度不符合要求 length: {}", uid, userId, noteSummaryLength);
        return false;
    }

    private boolean checkNoteParams(String title, String content, String summary, Integer uid, String userId) {
        return checkTitle(title, uid, userId) && checkContent(content, uid, userId) && checkSummary(summary, uid, userId);
    }
}
