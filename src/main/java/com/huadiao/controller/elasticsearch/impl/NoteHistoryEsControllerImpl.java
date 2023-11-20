package com.huadiao.controller.elasticsearch.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.elasticsearch.NoteHistoryController;
import com.huadiao.entity.Result;
import com.huadiao.service.elasticsearch.NoteHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 11 月 13 日
 */
@RestController
@RequestMapping("/search/history/note")
public class NoteHistoryEsControllerImpl extends AbstractController implements NoteHistoryController {
    private NoteHistoryService noteHistoryService;

    @Autowired
    public NoteHistoryEsControllerImpl(NoteHistoryService noteHistoryService) {
        this.noteHistoryService = noteHistoryService;
    }

    @Override
    @GetMapping("/title")
    public Result<?> findNoteHistoryByNoteTitle(HttpSession session, String title, Integer offset, Integer row) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteHistoryService.findNoteHistoryByNoteTitle(uid, userId, title, offset, row);
    }

    @Override
    @GetMapping("/delete/all")
    public Result<?> deleteAllByUid(HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteHistoryService.deleteAllByUid(uid, userId);
    }

    @Override
    @GetMapping("/delete/specific")
    public Result<?> deleteSpecificNoteHistory(HttpSession session, Integer id) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return noteHistoryService.deleteSpecificNoteHistory(uid, userId, id);
    }
}
