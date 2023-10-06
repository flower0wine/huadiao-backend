package com.huadiao.service;

import com.huadiao.entity.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 09 月 24 日
 */
public interface StarService extends Service {

    /**
     * 新增笔记收藏分组
     *
     * @param uid              用户 uid
     * @param userId           用户 id
     * @param groupName        分组名称
     * @param groupDescription 分组描述
     * @param open             是否公开
     * @return 返回新增提示
     */
    Result<?> addNoteStarGroup(Integer uid, String userId, String groupName, String groupDescription, Integer open);

    /**
     * 删除笔记收藏分组
     *
     * @param uid     用户 uid
     * @param userId  用户 id
     * @param groupId 分组 ID
     * @return 返回删除提示
     */
    Result<?> deleteNoteStarGroup(Integer uid, String userId, Integer groupId);

    /**
     * 修改笔记收藏分组
     *
     * @param uid              用户 uid
     * @param userId           用户 id
     * @param groupName        分组名称
     * @param groupId          分组 ID
     * @param groupDescription 分组描述
     * @param open             是否公开
     * @return 返回更新提示
     */
    Result<?> modifyNoteStarGroup(Integer uid, String userId, String groupName, String groupDescription, Integer groupId, Integer open);

    /**
     * 获取笔记收藏分组的所有笔记
     *
     * @param uid     用户 uid
     * @param userId  用户 id
     * @param viewedUid 被访问者 uid
     * @param groupId 分组 id
     * @param offset 偏移量
     * @param row 行数
     * @return 返回查询到的笔记收藏
     */
    Result<?> getNoteStar(Integer uid, String userId, Integer viewedUid, Integer groupId, Integer offset, Integer row);

    /**
     * 获取笔记收藏分组信息
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param viewedUid 被访问者 uid
     * @return 返回笔记收藏分组信息
     */
    Result<?> getNoteStarGroup(Integer uid, String userId, Integer viewedUid);

    /**
     * 删除笔记收藏
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param groupId 分组 id
     * @param authorUidList 作者 uid
     * @param noteIdList 笔记 id
     * @return 返回删除过程中的提示
     */
    Result<?> deleteNoteStar(Integer uid, String userId, Integer groupId, List<Integer> authorUidList, List<Integer> noteIdList);

    /**
     * 复制笔记收藏到其他收藏夹
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param srcGroupId 源分组 id
     * @param destGroupId 目标分组 id
     * @param noteIdList 笔记 id
     * @param authorUidList 作者 uid
     * @return 返回复制过程中的提示
     */
    Result<?> copyNoteStarToOtherGroup(Integer uid, String userId, Integer srcGroupId, Integer destGroupId,
                                       List<Integer> noteIdList, List<Integer> authorUidList);

    /**
     * 移动笔记收藏到其他收藏夹
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param srcGroupId 源分组 id
     * @param destGroupId 目标分组 id
     * @param noteIdList 笔记 id
     * @param authorUidList 作者 uid
     * @return 返回移动过程中的提示
     */
    Result<?> moveNoteStarToOtherGroup(Integer uid, String userId, Integer srcGroupId, Integer destGroupId,
                                         List<Integer> noteIdList, List<Integer> authorUidList);
}
