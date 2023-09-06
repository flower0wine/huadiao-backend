package com.huadiao.controller.impl;

import cn.hutool.http.server.HttpServerRequest;
import com.huadiao.controller.NoteController;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.note.NoteCommentDto;
import com.huadiao.entity.dto.note.SelfNoteDto;
import com.huadiao.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户笔记控制器实现类
 */
@RestController
@RequestMapping("/note")
public class NoteControllerImpl implements NoteController {

    private UserService userService;
    private FollowFanService followFanService;
    private UserSettingsService userSettingsService;
    private NoteService noteService;
    private NoteOperateService noteOperateService;

    @Autowired
    public NoteControllerImpl(UserService userService, FollowFanService followFanService, UserSettingsService userSettingsService, NoteService noteService, NoteOperateService noteOperateService) {
        this.userService = userService;
        this.followFanService = followFanService;
        this.userSettingsService = userSettingsService;
        this.noteService = noteService;
        this.noteOperateService = noteOperateService;
    }

    @Override
    @PostMapping("/publish")
    public String publishNote(HttpSession session, @RequestBody Map<String, String> map) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        String noteTitle = map.get("title");
        String noteContent = map.get("content");
        return noteService.insertNewNote(uid, userId, noteTitle, noteContent);
    }

    @Override
    @GetMapping("/delete")
    public String deleteNote(HttpSession session, Integer noteId) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return noteService.deleteNote(uid, userId, noteId);
    }

    @Override
    @PostMapping("/modify")
    public String modifyNote(HttpSession session, Integer noteId, @RequestBody Map<String, String> map) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        String noteTitle = map.get("title");
        String noteContent = map.get("content");
        return noteService.modifyNote(uid, userId, noteId, noteTitle, noteContent);
    }

    @Override
    @GetMapping("/search")
    public Map<String, Object> getSingleNote(HttpServletRequest request, HttpServletResponse response, HttpSession session, Integer uid, Integer noteId) throws Exception {
        Integer myUid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return noteService.getSingleNote(request, response, myUid, userId, uid, noteId);
    }

    @Override
    @GetMapping("/myNote")
    public SelfNoteDto getSingleNote(HttpSession session, Integer noteId) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return noteService.getSingleNote(uid, userId, noteId);
    }

    @Override
    @GetMapping("/all")
    public Map<String, Object> getAllNotes(HttpSession session, Integer authorUid) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return noteService.getAllNote(uid, userId, authorUid);
    }

    @Override
    @GetMapping("/comment")
    public Result<List<NoteCommentDto>> getNoteComment(HttpSession session, Integer uid, Integer noteId, Integer offset, Integer row) {
        Integer myUid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return noteService.getNoteComment(myUid, userId, uid, noteId, offset, row);
    }

    @Override
    @PostMapping("/comment/add")
    public Result<Map<String, Object>> addNoteComment(HttpSession session, Integer uid, Integer noteId, @RequestBody Map<String, String> map) {
        Integer myUid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        String rootCommentId = map.get("rootCommentId");
        String commentContent = map.get("commentContent");
        Long commentId = null;
        if(rootCommentId != null) {
            commentId = Long.parseLong(rootCommentId);
        }
        return noteOperateService.addNoteComment(myUid, userId, noteId, uid, commentId, commentContent);
    }
}
