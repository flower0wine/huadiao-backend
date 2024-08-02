package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.MessageController;
import com.huadiao.entity.Result;
import com.huadiao.entity.vo.UnreadCountVO;
import com.huadiao.service.LikeMessageService;
import com.huadiao.service.ReplyMessageService;
import com.huadiao.service.SystemMessageService;
import com.huadiao.service.WhisperMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2024 年 07 月 06 日
 */
@RestController
@RequestMapping("/message")
public class MessageControllerImpl extends AbstractController implements MessageController {

    @Resource
    private WhisperMessageService whisperMessageService;

    @Resource
    private LikeMessageService likeMessageService;

    @Resource
    private SystemMessageService systemMessageService;

    @Resource
    private ReplyMessageService replyMessageService;

    @Override
    @GetMapping("/unread/count")
    public Result<?> getUnreadMessageCount(HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);

        Result<Integer> whisperUnreadCount = whisperMessageService.countUnreadMessage(uid, userId);
        Result<Integer> systemUnreadCount = systemMessageService.countUnreadMessage(uid, userId);
        Result<Integer> likeUnreadCount = likeMessageService.countUnreadMessage(uid, userId);
        Result<Integer> replyUnreadCount = replyMessageService.countUnreadMessage(uid, userId);

        return Result.ok(new UnreadCountVO(
                replyUnreadCount.getData(),
                likeUnreadCount.getData(),
                whisperUnreadCount.getData(),
                systemUnreadCount.getData()
        ));
    }
}
