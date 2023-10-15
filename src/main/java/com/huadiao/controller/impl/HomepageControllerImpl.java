package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.HomepageController;
import com.huadiao.entity.Result;
import com.huadiao.service.HomepageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户个人主页控制器实现类
 */
@Slf4j
@RestController
@RequestMapping("/homepage")
public class HomepageControllerImpl extends AbstractController implements HomepageController {

    private HomepageService homepageService;

    @Autowired
    public HomepageControllerImpl(HomepageService homepageService) {
        this.homepageService = homepageService;
    }

    @Override
    @GetMapping("/info")
    public Result<?> getHomepageInfo(HttpSession session, Integer viewedUid) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return homepageService.getHomepageInfo(uid, userId, viewedUid);
    }

    @Override
    @PostMapping("/avatar/modify")
    public Result<?> updateUserAvatar(HttpSession session, MultipartFile userAvatar) throws IOException {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return homepageService.updateUserAvatar(uid, userId, userAvatar);
    }

    @Override
    @PostMapping("/background/modify")
    public Result<?> updateHomepageBackground(HttpSession session, MultipartFile background) throws IOException {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return homepageService.updateHomepageBackground(uid, userId, background);
    }
}
