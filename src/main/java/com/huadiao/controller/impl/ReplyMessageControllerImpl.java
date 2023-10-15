package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.ReplyMessageController;
import com.huadiao.entity.Result;
import com.huadiao.service.ReplyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 10 月 09 日
 */
@RestController
@RequestMapping("/message/reply")
public class ReplyMessageControllerImpl extends AbstractController implements ReplyMessageController {
    private ReplyMessageService  replyMessageService;

    @Autowired
    public ReplyMessageControllerImpl(ReplyMessageService replyMessageService) {
        this.replyMessageService = replyMessageService;
    }

    @Override
    @GetMapping("/get")
    public Result<?> getReplyMessage(HttpSession session, Integer offset, Integer row) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return replyMessageService.getReplyMessage(uid, userId, offset, row);
    }

    @Override
    @GetMapping("/delete")
    public Result<?> deleteReplyMessage(HttpSession session, Integer noteId, Integer uid, Integer rootCommentId, Integer subCommentId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return replyMessageService.deleteReplyMessage(myUid, userId, noteId, uid, rootCommentId, subCommentId);
    }
}
