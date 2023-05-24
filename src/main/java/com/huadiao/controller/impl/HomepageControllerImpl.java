package com.huadiao.controller.impl;

import com.huadiao.controller.HomepageController;
import com.huadiao.service.HomepageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
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
public class HomepageControllerImpl implements HomepageController {

    private HomepageService homepageService;

    @Autowired
    public HomepageControllerImpl(HomepageService homepageService) {
        this.homepageService = homepageService;
    }

    @Override
    @GetMapping("/info")
    public Map<String, Object> getHomepageInfo(HttpSession session, Integer viewedUid) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return homepageService.getHomepageInfo(uid, userId, viewedUid);
    }
}
