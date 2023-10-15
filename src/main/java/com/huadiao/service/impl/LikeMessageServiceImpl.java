package com.huadiao.service.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.message.like.LikeMessage;
import com.huadiao.entity.message.like.LikeMessageUser;
import com.huadiao.mapper.LikeMessageMapper;
import com.huadiao.service.AbstractMessageService;
import com.huadiao.service.LikeMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
@Service
@Slf4j
public class LikeMessageServiceImpl extends AbstractMessageService implements LikeMessageService {
    private LikeMessageMapper likeMessageMapper;

    @Autowired
    public LikeMessageServiceImpl(LikeMessageMapper likeMessageMapper) {
        this.likeMessageMapper = likeMessageMapper;
    }

    @Override
    public Result<?> getLikeMessage(Integer uid, String userId, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取点赞消息, offset: {}, row: {}", uid, userId, offset, row);
        Map<String, Object> map = new HashMap<>(4);
        Result<?> result = checkOffsetAndRow(offset, row, (o, r) -> {
            List<LikeMessage> likeNoteMessageList = likeMessageMapper.selectLikeNoteMessageByUid(uid, likeUserLimit, o, r);
            List<LikeMessage> likeCommentMessageList = likeMessageMapper.selectCommentLikeMessageByUid(uid, likeUserLimit, o, r);
            likeNoteMessageList.forEach((item) -> item.setType(likeNoteMessageType));
            likeCommentMessageList.forEach((item) -> item.setType(likeCommentMessageType));
            map.put("likeNoteMessageList", likeNoteMessageList);
            map.put("likeCommentMessageList", likeCommentMessageList);
            return isEmpty(likeNoteMessageList, likeCommentMessageList);
        });
        if(result.succeed()) {
            log.debug("uid, userId 分别为 {}, {} 的用户成功获取点赞消息, offset: {}, row: {}", uid, userId, offset, row);
            return Result.ok(map);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteLikeNoteMessage(Integer uid, String userId, Integer noteId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除点赞笔记的消息, noteId: {}", uid, userId, noteId);
        likeMessageMapper.deleteLikeNoteMessage(uid, noteId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除点赞笔记的消息, noteId: {}", uid, userId, noteId);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteLikeNoteMessage(Integer uid, String userId, Integer noteId, Integer rootCommentId, Integer subCommentId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除点赞笔记评论的消息, noteId: {}, rootCommentId: {}, subCommentId: {}", uid, userId, noteId, rootCommentId, subCommentId);
        likeMessageMapper.deleteLikeCommentMessage(uid, noteId, rootCommentId, subCommentId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除点赞笔记评论的消息, noteId: {}, rootCommentId: {}, subCommentId: {}", uid, userId, noteId, rootCommentId, subCommentId);
        return Result.ok(null);
    }

    @Override
    public Result<?> getLikeNoteUser(Integer uid, String userId, Integer noteId, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取点赞笔记的用户列表, noteId: {}, offset: {}, row: {}", uid, userId, noteId, offset, row);
        Result<?> result = checkOffsetAndRow(offset, row, (o, r) -> {
            List<LikeMessageUser> likeMessageUsers = likeMessageMapper.selectLikeNoteUser(uid, noteId, o, r);
            return isEmpty(likeMessageUsers);
        });
        if (result.succeed()) {
            log.debug("uid, userId 分别为 {}, {} 的用户成功获取点赞笔记的用户列表, noteId: {}, offset: {}, row: {}", uid, userId, noteId, offset, row);
        }
        return result;
    }

    @Override
    public Result<?> getLikeCommentUser(Integer uid, String userId, Integer noteId, Integer rootCommentId, Integer subCommentId, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取点赞笔记评论的用户列表, noteId: {}, rootCommentId: {}, subCommentId: {}, offset: {}, row: {}", uid, userId, noteId, rootCommentId, subCommentId, offset, row);
        Result<?> result = checkOffsetAndRow(offset, row, (o, r) -> {
            List<LikeMessageUser> likeMessageUsers = likeMessageMapper.selectLikeCommentUser(uid, noteId, rootCommentId, subCommentId, o, r);
            return isEmpty(likeMessageUsers);
        });
        if (result.succeed()) {
            log.debug("uid, userId 分别为 {}, {} 的用户成功获取点赞笔记评论的用户列表, noteId: {}, rootCommentId: {}, subCommentId: {}, offset: {}, row: {}", uid, userId, noteId, rootCommentId, subCommentId, offset, row);
        }
        return result;
    }
}
