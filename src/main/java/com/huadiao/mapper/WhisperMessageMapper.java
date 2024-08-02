package com.huadiao.mapper;

import com.huadiao.entity.message.whisper.LatestMessageUser;
import com.huadiao.entity.message.whisper.WhisperMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 10 月 10 日
 */
public interface WhisperMessageMapper {

    /**
     * 插入已读的最近一条消息
     * @param whisperMessage 消息
     * @return 返回新增条数
     */
    Integer insertLatestWhisperMessage(WhisperMessage whisperMessage);

    /**
     * 获取最近消息中的已关注用户
     *
     * @param uid 用户 uid
     * @param offset 偏移量
     * @param row 行数
     * @return 返回最近消息的用户
     */
    List<Integer> selectLatestUserByUid(@Param("uid") Integer uid, @Param("offset") Integer offset, @Param("row") Integer row);

    /**
     * 单独获取最近消息中的某个用户
     * @param uid 用户 uid
     * @param latestUid 最近消息中的用户 uid
     * @return 返回最近消息中的某个用户
     */
    LatestMessageUser selectSingleLatestUserByUid(@Param("uid") Integer uid, @Param("latestUid") Integer latestUid);

    /**
     * 删除最近消息中的用户
     * @param uid 用户 uid
     * @param latestUid 要删除的用户 uid
     * @return 返回删除条数
     */
    Integer deleteLatestUserByUid(@Param("uid") Integer uid, @Param("latestUid") Integer latestUid);

    /**
     * 获取与某个用户的私信
     * @param uid 用户 uid
     * @param receiveUid 私信对象
     * @param offset 偏移量
     * @param row 行数
     * @return 返回私信
     */
    List<WhisperMessage> selectWhisperMessage(@Param("uid") Integer uid, @Param("receiveUid") Integer receiveUid, @Param("offset") Integer offset,
                                              @Param("row") Integer row);

    /**
     * 获取用户 uid 发送 receiveUid 用户的最近一条消息
     * @param uid 发送者 uid
     * @param receiveUid 接收者 uid
     * @return 消息
     */
    WhisperMessage selectSingleWhisperMessage(@Param("uid") Integer uid,
                                              @Param("receiveUid") Integer receiveUid);

    /**
     * 删除私信消息, 只有收信者或者发信者可以删除
     * @param uid 用户 uid
     * @param messageId 消息 id
     * @return 返回删除条数
     */
    Integer deleteWhisperMessage(@Param("uid") Integer uid, @Param("messageId") Integer messageId);

    /**
     * 新增私信消息
     * @param uid 用户 uid
     * @param receiveUid 接收者 uid
     * @param messageContent 消息内容
     * @param messageId 消息 id
     * @param messageType 消息类型
     * @return 返回新增条数
     */
    Integer insertWhisperMessage(@Param("uid") Integer uid, @Param("receiveUid") Integer receiveUid, @Param("messageContent") String messageContent,
                                 @Param("messageId") Integer messageId, @Param("messageType") Integer messageType);

    /**
     * 获取未读私信数量
     * @param uid 接收者 uid
     * @return 返回未私信数
     */
    Integer countUnreadMessage(@Param("uid") Integer uid);

    /**
     * 删除已读的最近一条消息
     * @param uid 发送者 uid
     * @param receiveUid 接收者 uid
     * @return 返回删除条数
     */
    Integer deleteLatestMessage(@Param("uid") Integer uid, @Param("receiveUid") Integer receiveUid);
}
