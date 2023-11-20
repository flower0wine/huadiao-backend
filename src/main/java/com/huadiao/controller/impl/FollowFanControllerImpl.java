package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.FollowFanController;
import com.huadiao.entity.Result;
import com.huadiao.service.FollowFanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户关注与粉丝控制器实现类
 */
@RestController
@RequestMapping("/relation")
public class FollowFanControllerImpl extends AbstractController implements FollowFanController {

    private FollowFanService followFanService;

    @Autowired
    public FollowFanControllerImpl(FollowFanService followFanService) {
        this.followFanService = followFanService;
    }

    @Override
    @GetMapping("/follow/group/add")
    public Result<?> addNewFollowGroup(HttpSession session, String groupName) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return followFanService.addNewFollowGroup(uid, userId, groupName);
    }

    @Override
    @GetMapping("/follow/group")
    public Result<?> getFollowGroup(HttpSession httpSession) {
        Integer myUid = (Integer) httpSession.getAttribute(uidKey);
        String userId = (String) httpSession.getAttribute(userIdKey);
        return followFanService.getFollowGroup(myUid, userId);
    }

    @Override
    @GetMapping("/follow")
    public Result<?> getUserFollow(HttpSession session, Integer uid, Integer groupId, Integer offset, Integer row) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return followFanService.getUserFollow(myUid, userId, uid, groupId, offset, row);
    }

    @Override
    @GetMapping("/fan")
    public Result<?> getUserFan(HttpSession session, Integer uid, Integer offset, Integer row) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return followFanService.getUserFan(myUid, userId, uid, offset, row);
    }

    @Override
    @GetMapping("/count")
    public Result<?> getUserFollowFanInfo(HttpSession session, Integer uid) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return followFanService.getUserFollowFanInfo(myUid, userId, uid);
    }

    @Override
    @GetMapping("/friend")
    public Result<?> buildRelationBetweenBoth(HttpSession session, Integer uid) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return followFanService.buildRelationBetweenBoth(myUid, userId, uid);
    }

    @Override
    @GetMapping("/friend/cancel")
    public Result<?> cancelBuildRelationBetweenBoth(HttpSession session, Integer uid) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return followFanService.cancelBuildRelationBetweenBoth(myUid, userId, uid);
    }

    @Override
    @GetMapping("/fan/remove")
    public Result<?> removeFan(HttpSession session, Integer uid) {
        Integer myUid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return followFanService.removeFan(myUid, userId, uid);
    }

    @Override
    @GetMapping("/follow/group/modify")
    public void modifyFollowGroupName(HttpSession session, String groupName, Integer groupId) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        followFanService.modifyGroupName(uid, userId, groupName, groupId);
    }

    @Override
    @GetMapping("/follow/group/delete")
    public Result<?> deleteFollowGroup(HttpSession session, Integer groupId) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return followFanService.deleteFollowGroup(uid, userId, groupId);
    }

    @Override
    @PostMapping("/follow/copy")
    public Result<?> copyFollow(HttpSession session,
                                @RequestParam Integer srcGroupId,
                                @RequestParam Integer destGroupId,
                                @RequestParam(name = "uid") List<Integer> followerList) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return followFanService.copyFollow(uid, userId, srcGroupId, destGroupId, followerList);
    }

    @Override
    @PostMapping("/follow/move")
    public Result<?> moveFollow(HttpSession session,
                                @RequestParam Integer srcGroupId,
                                @RequestParam Integer destGroupId,
                                @RequestParam(name = "uid") List<Integer> followerList) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        String userId = (String) session.getAttribute(userIdKey);
        return followFanService.moveFollow(uid, userId, srcGroupId, destGroupId, followerList);
    }
}
