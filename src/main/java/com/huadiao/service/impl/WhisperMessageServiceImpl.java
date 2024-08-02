package com.huadiao.service.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.message.whisper.LatestMessageUser;
import com.huadiao.entity.message.whisper.MessageType;
import com.huadiao.entity.message.whisper.WhisperMessage;
import com.huadiao.mapper.UserInfoMapper;
import com.huadiao.mapper.UserMapper;
import com.huadiao.mapper.WhisperMessageMapper;
import com.huadiao.service.AbstractMessageService;
import com.huadiao.service.WhisperMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author flowerwine
 * @date 2023 年 10 月 10 日
 */
@Slf4j
@Service
public class WhisperMessageServiceImpl extends AbstractMessageService implements WhisperMessageService {
    private WhisperMessageMapper whisperMessageMapper;
    private UserMapper userMapper;
    private UserInfoMapper userInfoMapper;

    @Autowired
    public WhisperMessageServiceImpl(WhisperMessageMapper whisperMessageMapper, UserMapper userMapper, UserInfoMapper userInfoMapper) {
        this.whisperMessageMapper = whisperMessageMapper;
        this.userMapper = userMapper;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public Result<?> getLatestUser(Integer uid, String userId, Integer offset, Integer row) {
        return checkOffsetAndRow(offset, row, (o, r) -> {
            List<Integer> list = whisperMessageMapper.selectLatestUserByUid(uid, o, r);
            List<LatestMessageUser> latestMessageUserList = list.stream()
                    .map((item) ->
                            whisperMessageMapper.selectSingleLatestUserByUid(uid, item))
                    .collect(Collectors.toList());

            latestMessageUserList = new ArrayList<>(new HashSet<>(latestMessageUserList));
            latestMessageUserList.forEach((item) -> {
                if(item.getLatestMessage() == null) {
                    item.setLatestMessage(defaultWhisperMessage);
                }
            });
            return isEmpty(latestMessageUserList);
        });
    }

    @Override
    public Result<?> getSingleLatestUser(Integer uid, String userId, Integer chatUid) {
        if (chatUid == null) {
            log.debug("uid: {} 的用户未提供 chatUid", uid);
            return Result.blankParam();
        }
        LatestMessageUser latestMessageUser = whisperMessageMapper.selectSingleLatestUserByUid(uid, chatUid);
        if (latestMessageUser == null) {
            log.debug("uid: {} 的用户提供的 chatUid: {} 不存在", uid, chatUid);
            return Result.notExist();
        }
        if(latestMessageUser.getLatestMessage() == null) {
            latestMessageUser.setLatestMessage("很高兴认识你！来聊会天吧！");
        }
        return Result.ok(latestMessageUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteLatestUser(Integer uid, String userId, Integer latestUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除最近消息中的用户, latestUid: {}", uid, userId, latestUid);
        whisperMessageMapper.deleteLatestUserByUid(uid, latestUid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除最近消息中的用户, latestUid: {}", uid, userId, latestUid);
        return Result.ok(null);
    }

    @Override
    public Result<?> getWhisperMessage(Integer uid, String userId, Integer whisperUid, Integer offset, Integer row) {
        return checkOffsetAndRow(offset, row, (o, r) -> {
            List<WhisperMessage> whisperMessageList = whisperMessageMapper.selectWhisperMessage(uid, whisperUid, o, r);

            // 获取最新消息将设置全部已读
            if (o == 0 && !whisperMessageList.isEmpty()) {
                WhisperMessage whisperMessage = whisperMessageMapper.selectSingleWhisperMessage(whisperUid, uid);

                // 当 uid 未曾和 whisperUid 进行过聊天时，whisperMessage 为 null
                if (whisperMessage != null) {
                    whisperMessageMapper.deleteLatestMessage(whisperUid, uid);
                    whisperMessageMapper.insertLatestWhisperMessage(whisperMessage);
                }
            }

            return isEmpty(whisperMessageList);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteWhisperMessage(Integer uid, String userId, Integer messageId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除私信消息, messageId: {}", uid, userId, messageId);
        if (messageId == null || messageId < defaultRedisInitialId) {
            return Result.errorParam();
        }
        whisperMessageMapper.deleteWhisperMessage(uid, messageId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除私信消息, messageId: {}", uid, userId, messageId);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> addWhisperMessage(Integer uid, String userId, Integer receiveUid, String messageContent) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试添加私信消息, receiveUid: {}, messageContent: {}", uid, userId, receiveUid, messageContent);
        String exist = userMapper.selectUserIdByUid(receiveUid);
        if (exist == null) {
            log.trace("uid, userId 分别为 {}, {} 的用户提供的接收者用户不存在, receiveUid: {}", uid, userId, receiveUid);
            return Result.notExist();
        }
        if (messageContent == null || messageContent.length() > whisperMessageMaxLength || messageContent.length() < whisperMessageMinLength) {
            log.trace("uid, userId 分别为 {}, {} 的用户提供的私信内容不符合要求, messageContent: {}", uid, userId, messageContent);
            return Result.errorParam();
        }
        int whisperMessageId = messageJedisUtil.generateWhisperMessageId();
        whisperMessageMapper.insertWhisperMessage(uid, receiveUid, messageContent, whisperMessageId, MessageType.NORMAL.type);
        log.debug("uid, userId 分别为 {}, {} 的用户成功添加私信消息, receiveUid: {}, messageContent: {}", uid, userId, receiveUid, messageContent);
        return Result.ok(whisperMessageId);
    }

    @Override
    public Result<Integer> countUnreadMessage(Integer uid, String userId) {
        Integer count = whisperMessageMapper.countUnreadMessage(uid);
        return Result.ok(count);
    }
}
