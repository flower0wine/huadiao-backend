package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.StarController;
import com.huadiao.entity.Result;
import com.huadiao.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2023 年 09 月 24 日
 */
@RestController
@RequestMapping("/star")
public class StarControllerImpl extends AbstractController implements StarController {
    private StarService starService;

    @Autowired
    public StarControllerImpl(StarService starService) {
        this.starService = starService;
    }

    @Override
    @GetMapping("/note/group/add")
    public Result<?> addNoteStarGroup(HttpSession session, String groupName, String groupDescription, Integer open) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return starService.addNoteStarGroup(myUid, userId, groupName, groupDescription, open);
    }

    @Override
    @GetMapping("/note/group/delete")
    public Result<?> deleteNoteStarGroup(HttpSession session, Integer groupId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return starService.deleteNoteStarGroup(myUid, userId, groupId);
    }

    @Override
    @GetMapping("/note/group/modify")
    public Result<?> modifyNoteStarGroup(HttpSession session, String groupName, String groupDescription, Integer groupId, Integer open) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return starService.modifyNoteStarGroup(myUid, userId, groupName, groupDescription, groupId, open);
    }

    @Override
    @GetMapping("/note/get")
    public Result<?> selectNoteStar(HttpSession session, Integer uid, Integer groupId, Integer offset, Integer row) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return starService.getNoteStar(myUid, userId, uid, groupId, offset, row);
    }

    @Override
    @GetMapping("/note/group")
    public Result<?> getNoteStarGroup(HttpSession session, Integer uid) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return starService.getNoteStarGroup(myUid, userId, uid);
    }

    @Override
    @GetMapping("/note/delete")
    public Result<?> deleteNoteStar(HttpSession session, @RequestParam Integer groupId,
                                    @RequestParam(required = false) List<Integer> uid,
                                    @RequestParam(required = false) List<Integer> noteId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return starService.deleteNoteStar(myUid, userId, groupId, uid, noteId);
    }

    @Override
    @PostMapping("/note/copy")
    public Result<?> copyNoteStar(HttpSession session, Integer srcGroupId, Integer destGroupId,
                                  @RequestParam List<Integer> uid,
                                  @RequestParam List<Integer> noteId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return starService.copyNoteStarToOtherGroup(myUid, userId, srcGroupId, destGroupId, noteId, uid);
    }

    @Override
    @PostMapping("/note/move")
    public Result<?> modifyNoteStar(HttpSession session, Integer srcGroupId, Integer destGroupId,
                                    @RequestParam List<Integer> uid,
                                    @RequestParam List<Integer> noteId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return starService.moveNoteStarToOtherGroup(myUid, userId, srcGroupId, destGroupId, noteId, uid);
    }
}
