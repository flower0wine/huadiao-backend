package com.huadiao.controller;

import com.huadiao.entity.FollowGroup;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.followfan.FollowFanBaseInfoDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 用户关注与粉丝控制器接口
 * @author flowerwine
 */
public interface FollowFanController extends Controller {

    /**
     * 新增用户关注分组
     * @param session session 对象
     * @param map 请求体
     * @return 返回增加成功与否提示信息
     */
    String addNewFollowGroup(HttpSession session, Map<String, String> map);

    /**
     * 获取用户关注分组
     * @param httpSession session 对象
     * @return 返回用户关注分组
     */
    List<FollowGroup> getFollowGroup(HttpSession httpSession);

    /**
     * 获取指定用户的关注
     * @param session session 对象
     * @param viewedUid 被访问的用户 uid
     * @param groupId 关注分组 id
     * @param begin 开始分页
     * @param page 查询页数
     * @return 返回关注信息
     */
    Result<?> getUserFollow(HttpSession session, Integer viewedUid, Integer groupId, Integer begin, Integer page);

    /**
     * 获取指定用户的粉丝
     * @param session session 对象
     * @param viewedUid 被访问的用户 uid
     * @param begin 开始分页
     * @param page 每页数据条数
     * @return 返回粉丝信息
     */
    Result<?> getUserFan(HttpSession session, Integer viewedUid, Integer begin, Integer page);

    /**
     * 获取指定 uid 的关注和粉丝信息, 关注数量和粉丝数量
     * @param session session 对象
     * @param uid 用户 uid
     * @return 返回关注和粉丝信息
     */
    Result<?> getUserFollowFanInfo(HttpSession session, Integer uid);

    /**
     * 尝试建立两人的关系
     * @param session session 对象
     * @param uid 用户 uid
     * @param groupId 关注分组 id
     * @return 返回建立关系成功与否提示
     */
    Result<?> buildRelationBetweenBoth(HttpSession session, Integer uid, Integer groupId);

    /**
     * 解除两人的关系
     * @param session session 对象
     * @param uid 用户 uid, 被关注的
     */
    void cancelBuildRelationBetweenBoth(HttpSession session, Integer uid);

    /**
     * 移除粉丝, 删除粉丝是被关注的人做的
     * @param session session 对象
     * @param fanUid 关注者
     */
    void removeFan(HttpSession session, Integer fanUid);

    /**
     * 修改关注的人的分组
     * @param session session 对象
     * @param uid 被关注者
     * @param groupId 分组 id
     */
    void moveFollowGroup(HttpSession session, Integer uid, Integer groupId);

    /**
     * 修改关注分组名称
     * @param session session 对象
     * @param groupName 分组名称
     * @param groupId 分组 id
     */
    void modifyFollowGroupName(HttpSession session, String groupName, Integer groupId);

    /**
     * 删除关注分组
     * @param session session 对象
     * @param groupId 关注分组 id
     */
    void deleteFollowGroup(HttpSession session, Integer groupId);
}
