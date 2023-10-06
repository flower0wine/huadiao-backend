package com.huadiao.controller;

import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2023 年 09 月 24 日
 */
public interface StarController extends Controller {
    /**
     * 新增笔记收藏分组
     * @param session session 对象
     * @param groupName        分组名称
     * @param groupDescription 分组描述
     * @param open             是否公开
     * @return 返回新增提示
     */
    Result<?> addNoteStarGroup(HttpSession session, String groupName, String groupDescription, Integer open);

    /**
     * 删除笔记收藏分组
     *
     * @param session session 对象
     * @param groupId 分组 ID
     * @return 返回删除提示
     */
    Result<?> deleteNoteStarGroup(HttpSession session, Integer groupId);

    /**
     * 修改笔记收藏分组
     *
     * @param session session 对象
     * @param groupName        分组名称
     * @param groupId          分组 ID
     * @param groupDescription 分组描述
     * @param open             是否公开
     * @return 返回更新提示
     */
    Result<?> modifyNoteStarGroup(HttpSession session, String groupName, String groupDescription, Integer groupId, Integer open);

    /**
     * 获取笔记收藏分组的所有笔记
     *
     * @param session session 对象
     * @param uid 被访问者 uid
     * @param groupId 分组 id
     * @param offset 偏移量
     * @param row 行数
     * @return 返回查询到的笔记收藏
     */
    Result<?> selectNoteStar(HttpSession session, Integer uid, Integer groupId, Integer offset, Integer row);

    /**
     * 获取笔记收藏分组信息
     * @param session session 对象
     * @param uid 被访问者 uid
     * @return 返回笔记收藏分组信息
     */
    Result<?> getNoteStarGroup(HttpSession session, Integer uid);

    /**
     * 删除笔记收藏
     * @param session session 对象
     * @param groupId 分组 id
     * @param uid 用户 uid
     * @param noteId 笔记 id
     * @return 返回删除过程中的提示
     */
    Result<?> deleteNoteStar(HttpSession session, Integer groupId, List<Integer> uid, List<Integer> noteId);

    /**
     *  复制笔记收藏分组
     * @param session session 对象
     * @param srcGroupId 源分组 id
     * @param destGroupId 目标分组 id
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @return 返回复制过程中的提示
     */
    Result<?> copyNoteStar(HttpSession session, Integer srcGroupId, Integer destGroupId, List<Integer> uid, List<Integer> noteId);

    /**
     *  移动笔记收藏分组
     * @param session session 对象
     * @param srcGroupId 源分组 id
     * @param destGroupId 目标分组 id
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @return 返回移动过程中的提示
     */
    Result<?> modifyNoteStar(HttpSession session, Integer srcGroupId, Integer destGroupId, List<Integer> uid, List<Integer> noteId);
}
