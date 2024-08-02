package com.huadiao.mapper;

import com.huadiao.entity.message.system.SystemMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
public interface SystemMessageMapper {

    /**
     * 新增系统消息
     * @param messageId 消息 id
     * @param adminId 管理员 id
     * @param messageTitle 消息标题
     * @param messageContent 消息内容
     * @param form 消息展示形式
     * @return 返回修改条数
     */
    Integer insertSystemMessage(@Param("messageId") Integer messageId, @Param("adminId") Integer adminId,
                                @Param("messageTitle") String messageTitle, @Param("messageContent") String messageContent,
                                @Param("form") Integer form);

    /**
     * 新增用户最新的已读消息
     * @param messageId 消息 id
     * @param uid 用户 uid
     * @return 返回新增条数
     */
    Integer insertLatestSystemMessage(@Param("messageId") Integer messageId,
                                      @Param("uid") Integer uid);

    /**
     * 删除系统消息
     * @param messageId 消息 id
     * @return 返回修改条数
     */
    Integer deleteSystemMessage(@Param("messageId") Integer messageId);

    /**
     * 删除用户已读的最近一条消息
     * @param uid 用户 uid
     * @return 返回删除条数
     */
    Integer deleteLatestSystemMessage(@Param("uid") Integer uid);

    /**
     * 获取系统消息
     * @param offset 偏移量
     * @param row 行数
     * @return 返回系统消息
     */
    List<SystemMessage> selectSystemMessage(@Param("offset") Integer offset, @Param("row")  Integer row);

    /**
     * 获取未读消息数量
     * @param uid 用户 uid
     * @return 返回未读消息数量
     */
    Integer countUnreadMessage(Integer uid);
}
