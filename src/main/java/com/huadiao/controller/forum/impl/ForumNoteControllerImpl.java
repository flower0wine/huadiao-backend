package com.huadiao.controller.forum.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.forum.ForumNoteController;
import com.huadiao.entity.Result;
import com.huadiao.entity.req.RandomGetNote;
import com.huadiao.service.forum.ForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 12 月 24 日
 */
@Slf4j
@RestController
@RequestMapping("/forum/note")
public class ForumNoteControllerImpl extends AbstractController implements ForumNoteController {
    private ForumService forumService;

    @Autowired
    public ForumNoteControllerImpl(ForumService forumService) {
        this.forumService = forumService;
    }

    @Override
    @PostMapping
    public Result<?> randomGetNote(HttpSession session,
                                   @RequestBody RandomGetNote randomGetNote) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return forumService.randomGetNote(myUid, userId, randomGetNote);
    }

    @Override
    @GetMapping("/rank")
    public Result<?> getNoteRank(HttpSession session) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return forumService.getNoteRank(myUid, userId);
    }
}
