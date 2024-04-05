package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.NoteOperateController;
import com.huadiao.entity.Result;
import com.huadiao.service.NoteOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@RestController
@RequestMapping("/note")
public class NoteOperateControllerImpl extends AbstractController implements NoteOperateController {
    private NoteOperateService noteOperateService;

    @Autowired
    public NoteOperateControllerImpl(NoteOperateService noteOperateService) {
        this.noteOperateService = noteOperateService;
    }

    @Override
    @GetMapping("/comment/delete")
    public Result<?> deleteNoteComment(HttpSession session, Integer uid, Integer noteId, Long rootCommentId, Long subCommentId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteOperateService.deleteNoteComment(myUid, userId, noteId, uid, rootCommentId, subCommentId);
    }

    @Override
    @GetMapping("/star/add")
    public String addNewNoteStar(HttpSession session, Integer uid, Integer noteId, Integer groupId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteOperateService.addNewNoteStar(myUid, userId, noteId, uid, groupId);
    }

    @Override
    @GetMapping("/star/delete")
    public String deleteNoteStar(HttpSession session, Integer uid, Integer noteId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteOperateService.deleteNoteStar(myUid, userId, noteId, uid);
    }

    @Override
    @GetMapping("/unlike/add")
    public String addNoteUnlike(HttpSession session, Integer uid, Integer noteId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteOperateService.addNoteUnlike(myUid, userId, noteId, uid);
    }

    @Override
    @GetMapping("/unlike/delete")
    public String deleteNoteUnlike(HttpSession session, Integer uid, Integer noteId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteOperateService.deleteNoteUnlike(myUid, userId, noteId, uid);
    }

    @Override
    @GetMapping("/like/add")
    public String addNoteLike(HttpSession session, Integer uid, Integer noteId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteOperateService.addNoteLike(myUid, userId, uid, noteId);
    }

    @Override
    @GetMapping("/like/delete")
    public String deleteNoteLike(HttpSession session, Integer uid, Integer noteId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteOperateService.deleteNoteLike(myUid, userId, uid, noteId);
    }

    @Override
    @GetMapping("/comment/like/add")
    public Result<?> addNoteCommentLike(HttpSession session, Integer uid, Integer noteId, Long rootCommentId, Long subCommentId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteOperateService.addNoteCommentLike(myUid, userId, noteId, uid, rootCommentId, subCommentId);
    }

    @Override
    @GetMapping("/comment/like/delete")
    public Result<?> deleteNoteCommentLike(HttpSession session, Integer uid, Integer noteId, Long rootCommentId, Long subCommentId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteOperateService.deleteNoteCommentLike(myUid, userId, noteId, uid, rootCommentId, subCommentId);
    }

    @Override
    @GetMapping("/comment/unlike/add")
    public Result<?> addNoteCommentUnlike(HttpSession session, Integer uid, Integer noteId, Long rootCommentId, Long subCommentId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteOperateService.addNoteCommentUnlike(myUid, userId, noteId, uid, rootCommentId, subCommentId);
    }

    @Override
    @GetMapping("/comment/unlike/delete")
    public Result<?> deleteNoteCommentUnlike(HttpSession session, Integer uid, Integer noteId, Long rootCommentId, Long subCommentId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteOperateService.deleteNoteCommentUnlike(myUid, userId, noteId, uid, rootCommentId, subCommentId);
    }

    @Override
    @GetMapping("/comment/report")
    public Result<?> reportNoteComment(HttpSession session, Integer uid, Integer noteId, Integer authorUid, Long rootCommentId, Long subCommentId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteOperateService.reportNoteComment(myUid, userId, uid, noteId, authorUid, rootCommentId, subCommentId);
    }
}
