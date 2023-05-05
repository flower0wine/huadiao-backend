package com.huadiao.controller.impl;

import com.huadiao.controller.FollowFanController;
import com.huadiao.entity.FollowGroup;
import com.huadiao.entity.dto.followfan.FollowFanBaseInfoDto;
import com.huadiao.service.AbstractFollowFanService;
import com.huadiao.service.FollowFanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户关注与粉丝控制器实现类
 */
@CrossOrigin
@RestController
@RequestMapping("/relation")
public class FollowFanControllerImpl implements FollowFanController {

    private FollowFanService followFanService;

    @Autowired
    public FollowFanControllerImpl(FollowFanService followFanService) {
        this.followFanService = followFanService;
    }

    @Override
    @PostMapping("/follow/group")
    public String addNewFollowGroup(HttpSession session, @RequestBody Map<String, String> map) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return followFanService.addNewFollowGroup(map.get("groupName"), uid, userId);
    }

    @Override
    @GetMapping("/follow/group")
    public List<FollowGroup> getFollowGroup(HttpSession httpSession) {
        Integer uid = (Integer) httpSession.getAttribute("uid");
        String userId = (String) httpSession.getAttribute("userId");
        return followFanService.getFollowGroup(uid, userId);
    }

    @Override
    @GetMapping("/follow")
    public Map<String, Object> getUserFollow(HttpSession session, Integer viewedUid, Integer groupId, Integer begin, Integer end) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return followFanService.getUserFollow(uid, userId, viewedUid, groupId, begin, end);
    }

    @Override
    @GetMapping("/fan")
    public Map<String, Object> getUserFan(HttpSession session, Integer viewedUid, Integer begin, Integer page) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return followFanService.getUserFan(uid, userId, viewedUid, begin, page);
    }

    @Override
    @GetMapping("/stat")
    public FollowFanBaseInfoDto getUserFollowFanInfo(HttpSession session, Integer viewedUid) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return followFanService.getUserFollowFanInfo(uid, userId, viewedUid);
    }

    @Override
    @GetMapping("/newFriend")
    public String buildRelationBetweenBoth(HttpSession session, Integer uid, Integer groupId) {
        Integer fanUid = (Integer) session.getAttribute("uid");
        String fanUserId = (String) session.getAttribute("userId");
        return followFanService.buildRelationBetweenBoth(uid, fanUserId, fanUid, groupId);
    }

    @Override
    @GetMapping("/modify")
    public void cancelBuildRelationBetweenBoth(HttpSession session, Integer uid) {
        Integer fanUid = (Integer) session.getAttribute("uid");
        String fanUserId = (String) session.getAttribute("userId");
        followFanService.cancelBuildRelationBetweenBoth(uid, fanUid, fanUserId);
    }

    @Override
    @GetMapping("/removeFan")
    public void removeFan(HttpSession session, Integer fanUid) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        followFanService.removeFan(uid, userId, fanUid);
    }

    @Override
    @GetMapping("/moveFollow")
    public void moveFollowGroup(HttpSession session, Integer uid, Integer groupId) {
        Integer fanUid = (Integer) session.getAttribute("uid");
        String fanUserId = (String) session.getAttribute("userId");
        followFanService.moveFollowGroup(uid, fanUid, fanUserId, groupId);
    }

    @Override
    @GetMapping("/modifyFollowGroupName")
    public void modifyFollowGroupName(HttpSession session, String groupName, Integer groupId) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        followFanService.modifyGroupName(uid, userId, groupName, groupId);
    }

    @Override
    @GetMapping("/deleteFollowGroup")
    public void deleteFollowGroup(HttpSession session, Integer groupId) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        followFanService.deleteFollowGroup(uid, userId, groupId);
    }
}
