package com.huadiao.service.impl;

import com.huadiao.controller.ErrorController;
import com.huadiao.entity.AccountSettings;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.followfan.BothRelationDto;
import com.huadiao.entity.dto.note.NoteCommentDto;
import com.huadiao.entity.dto.note.NoteRelationDto;
import com.huadiao.entity.dto.note.SelfNoteDto;
import com.huadiao.entity.dto.note.ShareNoteDto;
import com.huadiao.entity.dto.userdto.UserAbstractDto;
import com.huadiao.mapper.*;
import com.huadiao.service.AbstractFollowFanService;
import com.huadiao.service.AbstractNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    public NoteServiceImpl(UserMapper userMapper, NoteMapper noteMapper, UserSettingsMapper userSettingsMapper, FollowFanMapper followFanMapper, NoteOperateMapper noteOperateMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
        this.userSettingsMapper = userSettingsMapper;
        this.followFanMapper = followFanMapper;
        this.noteOperateMapper = noteOperateMapper;
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
    public Map<String, Object> getSingleNote(HttpServletRequest request, HttpServletResponse response, Integer uid, String userId, Integer authorUid, Integer noteId) throws Exception {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的 noteId 为 {} 的笔记", uid, userId, authorUid, noteId);

        // 检查用户提供的 authorUid 和 笔记 id 是否存在
        Integer queryAuthorUid = noteMapper.judgeNoteExist(authorUid, noteId);
        if(queryAuthorUid == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 authorUid: {} 并不存在 noteId: {} 的笔记, 返回 404", uid, userId, authorUid, noteId);
            request.getRequestDispatcher(ErrorController.NOT_FOUND_DISPATCHER_PATH).forward(request, response);
            Map<String, Object> map = new HashMap<>(2);
            map.put(WRONG_MESSAGE_KEY, INVALID_PARAM);
            return map;
        }

        // 判断是否是本人
        boolean me = uid.equals(authorUid);
        if(!me) {
            // 检查作者是否公开笔记
            AccountSettings accountSettings = userSettingJedisUtil.getAccountSettings(authorUid);
            if(!accountSettings.getPublicNoteStatus()) {
                log.debug("uid 为 {} 的用户选择不公开笔记", authorUid);
                Map<String, Object> map = new HashMap<>(2);
                map.put(PRIVATE_SETTINGS_KEY, PRIVATE_USER_INFO);
                return map;
            }
        }

        // 查找笔记
        ShareNoteDto shareNoteDto = noteMapper.selectNoteByUidAndNoteId(authorUid, noteId);
        // 查找作者信息
        UserAbstractDto authorInfo = userInfoJedisUtil.getUserInfoByUid(authorUid);
        // 查找我和作者的关系
        BothRelationDto relation = AbstractFollowFanService.judgeRelationBetweenBoth(followFanMapper.selectRelationByBothUid(uid, authorUid));
        // 查找我和笔记的关系
        NoteRelationDto noteRelationDto = new NoteRelationDto();
        noteRelationDto.setMe(uid.equals(queryAuthorUid));
        noteRelationDto.setMyLike(uid.equals(noteMapper.selectMyLikeWithNote(uid, noteId, authorUid)));
        noteRelationDto.setMyUnlike(uid.equals(noteMapper.selectMyUnlikeWithNote(uid, noteId, authorUid)));
        noteRelationDto.setMyStar(uid.equals(noteMapper.selectMyStarWithNote(uid, noteId, authorUid)));
        // 获取评论数
        Integer countComment = noteMapper.countAllNoteCommentByUidNoteId(noteId, authorUid);
        // 新增笔记浏览记录, 一个人一条浏览记录, 不包含作者本身
        if(!uid.equals(authorUid)) {
            addNewNoteView(uid, userId, noteId, authorUid);
        }

        // 填充数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("noteTitle", shareNoteDto.getNoteTitle());
        map.put("noteContent", shareNoteDto.getNoteContent());
        map.put("viewNumber", shareNoteDto.getViewNumber());
        map.put("likeNumber", shareNoteDto.getLikeNumber());
        map.put("starNumber", shareNoteDto.getStarNumber());
        map.put("commentNumber", countComment);
        map.put("publishTime", shareNoteDto.getPublishTime());
        map.put("authorInfo", authorInfo);
        map.put("authorAndMeRelation", relation);
        map.put("noteAndMeRelation", noteRelationDto);
        return map;
    }

    @Override
    public Result<List<NoteCommentDto>> getNoteComment(Integer uid, String userId, Integer authorUid, Integer noteId, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取用户 uid 为 {} 的笔记 noteId 为 {} 的评论, 获取页码为 {}, 行数为 {}", uid, userId, authorUid, noteId, offset, row);
        if(offset == null || offset < 0 || row == null || row < 0 || row  > MAX_ROW) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的参数有误, page 为 {}, row 为 {}", uid, userId, offset, row);
            return Result.errorParam();
        }
        List<NoteCommentDto> noteCommentDtoList = noteMapper.selectNoteCommentByUid(uid, noteId, authorUid, offset, row);
        // 由于本身的水平原因, 需要对查询的数据进一步处理
        noteCommentDtoList.forEach((commentDto) -> {
            List<NoteCommentDto> commentList = commentDto.getCommentList();
            List<NoteCommentDto> collect = commentList.stream().filter((noteComment) -> {
                if(noteComment.getCommentId() == 0) {
                    commentDto.setCommentContent(noteComment.getCommentContent());
                    commentDto.setMyLike(noteComment.getMyLike());
                    commentDto.setMyUnlike(noteComment.getMyUnlike());
                    commentDto.setLikeNumber(noteComment.getLikeNumber());
                    commentDto.setNickname(noteComment.getNickname());
                    commentDto.setUid(noteComment.getUid());
                    commentDto.setUserAvatar(noteComment.getUserAvatar());
                    commentDto.setCommentDate(noteComment.getCommentDate());
                }
                return noteComment.getCommentId() != 0;
            }).collect(Collectors.toList());
            System.out.println(collect);
            commentDto.setCommentList(collect);
        });
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取用户 uid 为 {} 的笔记 noteId 为 {} 的评论, 获取页码为 {}, 指定获取行数为 {}, 实际获取行数为 {}", uid, userId, authorUid, noteId, offset, row, noteCommentDtoList.size());
        if(noteCommentDtoList.size() == 0) {
            return Result.notExist();
        }
        return Result.ok(noteCommentDtoList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addNewNoteView(Integer uid, String userId, Integer noteId, Integer authorUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增对用户 uid 为 {} 的笔记 noteId 为 {} 的访问记录", uid, userId, authorUid, noteId);
        noteOperateMapper.insertNoteViewByUid(uid, noteId, authorUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功新增对用户 uid 为 {} 的笔记 noteId 为 {} 的访问记录", uid, userId, authorUid, noteId);
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
            AccountSettings accountSettings = userSettingJedisUtil.getAccountSettings(authorUid);
            if(!accountSettings.getPublicNoteStatus()) {
                Map<String, Object> map = new HashMap<>(2);
                map.put(PRIVATE_SETTINGS_KEY, PRIVATE_USER_INFO);
                return map;
            }
        }

        Map<String, Object> map = new HashMap<>(4);
        List<ShareNoteDto> shareNoteDtoList = noteMapper.selectAllNoteByUid(authorUid);
        UserAbstractDto authorInfo = userInfoJedisUtil.getUserInfoByUid(authorUid);

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
