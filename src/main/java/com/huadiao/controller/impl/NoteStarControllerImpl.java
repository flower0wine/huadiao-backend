package com.huadiao.controller.impl;

import com.huadiao.controller.NoteStarController;
import com.huadiao.service.NoteStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Controller
@RequestMapping("/note/star")
public class NoteStarControllerImpl implements NoteStarController {
    private NoteStarService noteStarService;

    @Autowired
    public NoteStarControllerImpl(NoteStarService noteStarService) {
        this.noteStarService = noteStarService;
    }

    @Override
    @GetMapping("/new")
    public String addNewNoteStar(HttpSession session, Integer uid, Integer noteId) {
        Integer myUid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return noteStarService.addNewNoteStar(myUid, userId, noteId, uid);
    }
}
