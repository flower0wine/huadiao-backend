package com.huadiao.controller.elasticsearch.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.elasticsearch.NoteController;
import com.huadiao.entity.Result;
import com.huadiao.service.elasticsearch.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
@RestController
@RequestMapping("/search/note")
public class NoteEsControllerImpl extends AbstractController implements NoteController {
    private NoteService noteService;

    @Autowired
    public NoteEsControllerImpl(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    @GetMapping("/title")
    public Result<?> findNoteByNoteTitle(HttpSession session, String noteTitle, Integer offset, Integer row) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String myUserId = (String) session.getAttribute(userIdKey);
        return noteService.findNoteByNoteTitle(myUid, myUserId, noteTitle, offset, row);
    }

    @Override
    @GetMapping("/summary")
    public Result<?> findNoteByNoteSummary(HttpSession session, String noteSummary, Integer offset, Integer row) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String myUserId = (String) session.getAttribute(userIdKey);
        return noteService.findNoteByNoteSummary(myUid, myUserId, noteSummary, offset, row);
    }
}
