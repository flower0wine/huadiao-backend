package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.NoteController;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.note.SelfNoteDto;
import com.huadiao.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户笔记控制器实现类
 */
@Slf4j
@RestController
@RequestMapping("/note")
public class NoteControllerImpl extends AbstractController implements NoteController {

    private NoteService noteService;
    private NoteOperateService noteOperateService;

    @Autowired
    public NoteControllerImpl(NoteService noteService, NoteOperateService noteOperateService) {
        this.noteService = noteService;
        this.noteOperateService = noteOperateService;
    }

    @Override
    @PostMapping("/publish")
    public Result<?> publishNote(HttpSession session, @RequestBody Map<String, String> map) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        String noteTitle = map.get("title");
        String summary  = map.get("summary");
        String noteContent = map.get("content");
        return noteService.insertNewNote(uid, userId, summary, noteTitle, noteContent);
    }

    @Override
    @GetMapping("/delete")
    public Result<?> deleteNote(HttpSession session, Integer noteId) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);

        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除自己的 noteId 为 {} 笔记", uid, userId, noteId);

        if(noteId == null || noteId <= 0) {
            return Result.errorParam();
        }

        try {
            noteService.deleteNote(uid, userId, noteId);
            log.debug("uid, userId 分别为 {}, {} 的用户成功删除自己的 noteId 为 {} 笔记", uid, userId, noteId);
            return Result.ok(null);
        } catch (Exception e) {
            log.debug("uid, userid 分别为 {}, {} 的用户删除笔记失败：{}", uid, userId, e.getMessage());
            return Result.errorParam();
        }
    }

    @Override
    @PostMapping("/modify")
    public Result<?> modifyNote(HttpSession session, Integer noteId, @RequestBody Map<String, String> map) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        String noteTitle = map.get("title");
        String noteSummary = map.get("summary");
        String noteContent = map.get("content");
        return noteService.modifyNote(uid, userId, noteId, noteTitle, noteSummary, noteContent);
    }

    @Override
    @GetMapping("/get")
    public Result<?> getSingleNote(HttpSession session, Integer uid, Integer noteId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteService.getSingleNote(myUid, userId, uid, noteId);
    }

    @Override
    @GetMapping("/edit")
    public SelfNoteDto getSingleNote(HttpSession session, Integer noteId) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteService.getSingleNote(uid, userId, noteId);
    }

    @Override
    @GetMapping("/all")
    public Result<?> getAllNote(HttpSession session, Integer uid, Integer offset, Integer row) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteService.getAllNote(myUid, userId, uid, offset, row);
    }

    @Override
    @GetMapping("/comment")
    public Result<?> getNoteComment(HttpSession session, Integer uid, Integer noteId, Integer offset, Integer row) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteService.getNoteComment(myUid, userId, uid, noteId, offset, row);
    }

    @Override
    @PostMapping("/comment/add")
    public Result<?> addNoteComment(HttpSession session, Integer repliedUid, Integer uid, Integer noteId, @RequestBody Map<String, String> map) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        String rootCommentId = map.get("rootCommentId");
        String commentContent = map.get("commentContent");
        Integer commentId = null;
        if(rootCommentId != null) {
            try {
                commentId = Integer.parseInt(rootCommentId);
            } catch (Exception e) {
                return Result.errorParam();
            }
        }
        // 如果被回复的人的 uid 为 null, 则将被回复者视为作者
        if(repliedUid == null) {
            repliedUid = uid;
        }
        return noteOperateService.addNoteComment(myUid, userId, noteId, repliedUid, uid, commentId, commentContent);
    }

}
