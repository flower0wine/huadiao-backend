package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.LikeMessageController;
import com.huadiao.dto.note.NoteCommentDTO;
import com.huadiao.entity.Result;
import com.huadiao.service.LikeMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
@Controller
@RestController
@RequestMapping("/message/like")
public class LikeMessageControllerImpl extends AbstractController implements LikeMessageController {
    private LikeMessageService likeMessageService;

    @Autowired
    public LikeMessageControllerImpl(LikeMessageService likeMessageService) {
        this.likeMessageService = likeMessageService;
    }

    @Override
    @GetMapping("/get")
    public Result<?> getLikeMessage(HttpSession session, Integer offset, Integer row) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return likeMessageService.getLikeMessage(uid, userId, offset, row);
    }

    @Override
    @GetMapping("/delete")
    public Result<?> deleteLikeNoteMessage(HttpSession session, NoteCommentDTO noteCommentDTO) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        return likeMessageService.deleteLikeNoteMessage(uid, noteCommentDTO);
    }

    @Override
    @GetMapping("/comment/delete")
    public Result<?> deleteLikeNoteMessage(HttpSession session, Integer noteId, Integer rootCommentId, Integer subCommentId) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return likeMessageService.deleteLikeNoteMessage(uid, userId, noteId, rootCommentId, subCommentId);
    }

    @Override
    @GetMapping("/note")
    public Result<?> getLikeNoteUser(HttpSession session, Integer noteId, Integer offset, Integer row) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return likeMessageService.getLikeNoteUser(uid, userId, noteId, offset, row);
    }

    @Override
    @GetMapping("/comment")
    public Result<?> getLikeCommentUser(HttpSession session, Integer noteId, Integer rootCommentId, Integer subCommentId, Integer offset, Integer row) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return likeMessageService.getLikeCommentUser(uid, userId, noteId, rootCommentId, subCommentId, offset, row);
    }
}
