package com.huadiao.mapper;

import com.huadiao.entity.NoteStarCatalogue;
import com.huadiao.entity.NoteStarInfo;
import com.huadiao.entity.dao.NoteStarIdDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 09 月 24 日
 */
public interface StarMapper {
    /**
     * 新增笔记收藏分组
     *
     * @param uid              用户 uid
     * @param groupName        分组名称
     * @param groupDescription 分组描述
     * @param groupId          分组 id
     * @param open             是否公开
     */
    void insertNoteStarGroup(@Param("uid") Integer uid, @Param("groupName") String groupName, @Param("groupDescription") String groupDescription,
                             @Param("groupId") Integer groupId, @Param("open") Integer open);

    /**
     * 删除笔记收藏分组
     *
     * @param uid     用户 uid
     * @param groupId 分组 ID
     */
    void deleteNoteStarGroup(@Param("uid") Integer uid, @Param("groupId") Integer groupId);

    /**
     * 修改笔记收藏分组
     *
     * @param uid              用户 uid
     * @param groupName        分组名称
     * @param groupId          分组 ID
     * @param groupDescription 分组描述
     * @param open             是否公开
     */
    void updateNoteStarGroup(@Param("uid") Integer uid, @Param("groupName") String groupName, @Param("groupDescription") String groupDescription,
                             @Param("groupId") Integer groupId, @Param("open") Integer open);

    /**
     * 获取笔记收藏分组的所有笔记
     *
     * @param uid     用户 uid
     * @param groupId 分组 id
     * @param offset  偏移量
     * @param row     行数
     * @return 返回查询到的笔记
     */
    List<NoteStarInfo> selectNoteStarByGroupId(@Param("uid") Integer uid, @Param("groupId") Integer groupId, @Param("offset") Integer offset, @Param("row") Integer row);

    /**
     * 获取用户的笔记收藏分组
     *
     * @param uid 用户 uid
     * @return 返回笔记收藏分组
     */
    List<NoteStarCatalogue> selectNoteStarCatalogueByUid(@Param("uid") Integer uid);

    /**
     * 根据分组 id 获取笔记收藏分组的数量, 当 groupId 为 null 时查询所有笔记收藏的数量
     *
     * @param uid     用户 uid
     * @param groupId 分组 id
     * @return 返回某个分组的笔记收藏数量
     */
    Integer selectNoteStarCatalogueByGroupId(@Param("uid") Integer uid, @Param("groupId") Integer groupId);

    /**
     * 删除笔记收藏, 若只提供 uid 和 groupId 时删除该分组下的所有收藏, 删除单个收藏需要提供 noteId 和 authorId
     * @param uid 用户 uid
     * @param groupId 分组 id
     * @param list 里面存有 authorUid 和 noteId
     */
    void deleteNoteStar(@Param("uid") Integer uid, @Param("groupId") Integer groupId, @Param("list") List<NoteStarIdDao> list);

    /**
     * 根据 groupId 删除笔记收藏
     * @param uid 用户 uid
     * @param groupId 分组 id
     * @return 返回删除条数
     */
    Integer deleteNoteStarByGroupId(@Param("uid") Integer uid, @Param("groupId") Integer groupId);

    /**
     * 复制到其他分组
     * @param uid 用户 uid
     * @param groupId 目标分组 id
     * @param list 要复制的笔记收藏信息
     */
    void insertNoteStarToOtherGroup(@Param("uid") Integer uid, @Param("groupId") Integer groupId, @Param("list") List<NoteStarIdDao> list);

    /**
     * 删除某个分组下的笔记收藏, (真实删除)
     * @param uid 用户 uid
     * @param groupId 分组 id
     * @param list 要删除的笔记收藏信息
     * @return 返回删除条数
     */
    Integer deleteNoteStarReal(@Param("uid") Integer uid, @Param("groupId") Integer groupId, @Param("list") List<NoteStarIdDao> list);

}
