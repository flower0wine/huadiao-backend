package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.UserInfoController;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.user.UserShareDto;
import com.huadiao.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2023 年 10 月 16 日
 */
@RestController
@RequestMapping("/userinfo")
public class UserInfoControllerImpl extends AbstractController implements UserInfoController {
    private UserInfoService userInfoService;

    public UserInfoControllerImpl(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    @PostMapping
    public Result<?> insertOrUpdateUserInfo(@RequestBody Map<String, String> map, HttpSession session) throws Exception {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String bd = map.get("bornDate");
        Long bornDate = null;
        if (bd != null) {
            bornDate = Long.parseLong(bd);
        }
        String sex = map.get("sex");
        String school = map.get("school");
        String canvases = map.get("canvases");
        String nickname = map.get(nicknameKey);
        String userId = session.getAttribute(userIdKey).toString();
        return userInfoService.insertOrUpdateUserInfo(uid, userId, nickname, canvases, sex, bornDate, school);
    }

    @Override
    @GetMapping
    public Result<?> getUserInfo(HttpSession session, Integer uid) {
        String userId = (String) session.getAttribute(userIdKey);
        Integer myUid = (Integer) session.getAttribute(uidKey);
        return userInfoService.getUserInfo(myUid, uid, userId);
    }

    @Override
    @GetMapping("/share")
    public UserShareDto getUserShare(HttpSession httpSession) {
        Integer uid = (Integer) httpSession.getAttribute(uidKey);
        return userInfoService.getUserShareInfo(uid);
    }

    @Override
    @GetMapping("/account")
    public Result<?> getAccountInfo(HttpSession session) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        return userInfoService.getAccountInfo(uid);
    }

}
