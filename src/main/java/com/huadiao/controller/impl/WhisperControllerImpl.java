package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.WhisperMessageController;
import com.huadiao.entity.Result;
import com.huadiao.service.WhisperMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2023 年 10 月 10 日
 */
@RestController
@RequestMapping("/message/whisper")
public class WhisperControllerImpl extends AbstractController implements WhisperMessageController {
    private WhisperMessageService whisperMessageService;

    @Autowired
    public WhisperControllerImpl(WhisperMessageService whisperMessageService) {
        this.whisperMessageService = whisperMessageService;
    }

    @Override
    @GetMapping("/user")
    public Result<?> getLatestUser(HttpSession session, Integer offset, Integer row) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return whisperMessageService.getLatestUser(myUid, userId, offset, row);
    }

    @Override
    @GetMapping("/user/single")
    public Result<?> getSingleLatestUser(HttpSession session, Integer uid) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return whisperMessageService.getSingleLatestUser(myUid, userId, uid);
    }

    @Override
    @GetMapping("/user/delete")
    public Result<?> deleteLatestUser(HttpSession session, Integer uid) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return whisperMessageService.deleteLatestUser(myUid, userId, uid);
    }

    @Override
    @GetMapping("/get")
    public Result<?> getWhisperMessage(HttpSession session, Integer uid, Integer offset, Integer row) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return whisperMessageService.getWhisperMessage(myUid, userId, uid, offset, row);
    }

    @Override
    @GetMapping("/delete")
    public Result<?> deleteWhisperMessage(HttpSession session, Integer messageId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return whisperMessageService.deleteWhisperMessage(myUid, userId, messageId);
    }

    @Override
    @PostMapping("/add")
    public Result<?> addWhisperMessage(HttpSession session, Integer uid, @RequestBody Map<String, String> map) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return whisperMessageService.addWhisperMessage(myUid, userId, uid, map.get("messageContent"));
    }
}
