package com.huadiao.controller;

import com.huadiao.entity.Result;
import com.huadiao.entity.req.follow.TransferFollowerParams;

import javax.servlet.http.HttpSession;

/**
 * 用户关注与粉丝控制器接口
 * @author flowerwine
 */
public interface FollowFanController extends Controller {

    /**
     * 新增用户关注分组
     * @param session session 对象
     * @param groupName 组名
     * @return 返回增加成功与否提示信息
     */
    Result<?> addNewFollowGroup(HttpSession session, String groupName);

    /**
     * 获取用户关注分组
     * @param httpSession session 对象
     * @return 返回用户关注分组
     */
    Result<?> getFollowGroup(HttpSession httpSession);

    /**
     * 获取指定用户的关注
     * @param session session 对象
     * @param viewedUid 被访问的用户 uid
     * @param groupId 关注分组 id
     * @param page 页码
     * @param size 每页数量
     * @return 返回关注信息
     */
    Result<?> getUserFollow(HttpSession session, Integer viewedUid, Integer groupId, Integer page, Integer size);

    /**
     * 获取指定用户的粉丝
     * @param session session 对象
     * @param uid 被访问的用户 uid
     * @param page 页码
     * @param size 每页数量
     * @return 返回粉丝信息
     */
    Result<?> getUserFan(HttpSession session, Integer uid, Integer page, Integer size);

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
     * @return 返回建立关系成功与否提示
     */
    Result<?> buildRelationBetweenBoth(HttpSession session, Integer uid);

    /**
     * 解除两人的关系
     * @param session session 对象
     * @param uid 用户 uid, 被关注的
     * @return 返回解除关系成功与否提示
     */
    Result<?> cancelBuildRelationBetweenBoth(HttpSession session, Integer uid);

    /**
     * 移除粉丝, 删除粉丝是被关注的人做的
     * @param session session 对象
     * @param uid 关注者
     * @return 返回移除粉丝成功与否提示
     */
    Result<?> removeFan(HttpSession session, Integer uid);

    /**
     * 修改关注分组名称
     * @param session session 对象
     * @param groupName 分组名称
     * @param groupId 分组 id
     * @return 返回修改分组名称成功与否提示
     */
    Result<?> modifyFollowGroupName(HttpSession session, String groupName, Integer groupId);

    /**
     * 删除关注分组
     * @param session session 对象
     * @param groupId 关注分组 id
     * @return 返回删除分组成功与否提示
     */
    Result<?> deleteFollowGroup(HttpSession session, Integer groupId);

    /**
     * 复制关注到其他分组
     * @param session session 对象
     * @param transferFollowerParams 复制所需参数
     * @return 返回复制过程中的提示
     */
    Result<?> copyFollow(HttpSession session, TransferFollowerParams transferFollowerParams);

    /**
     * 复制关注到其他分组
     * @param session session 对象
     * @param transferFollowerParams 移动所需参数
     * @return 返回移动过程中的提示
     */
    Result<?> moveFollow(HttpSession session, TransferFollowerParams transferFollowerParams);
}
