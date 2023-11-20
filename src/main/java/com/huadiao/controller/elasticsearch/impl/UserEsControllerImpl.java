package com.huadiao.controller.elasticsearch.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.elasticsearch.UserController;
import com.huadiao.entity.Result;
import com.huadiao.service.elasticsearch.UserService;
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
@RequestMapping("/search/user")
public class UserEsControllerImpl extends AbstractController implements UserController {
    private UserService userService;

    @Autowired
    public UserEsControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/nickname")
    public Result<?> findUserByNickname(HttpSession session, String nickname, Integer offset, Integer row) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String myUserId = (String) session.getAttribute(userIdKey);
        return userService.findUserByNickname(myUid, myUserId, nickname, offset, row);
    }

    @Override
    @GetMapping("/userid")
    public Result<?> findUserByUserId(HttpSession session, String userId) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String myUserId = (String) session.getAttribute(userIdKey);
        return userService.findUserByUserId(myUid, myUserId, userId);
    }
}
