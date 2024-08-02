package com.huadiao.service.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.message.reply.ReplyComment;
import com.huadiao.mapper.ReplyMessageMapper;
import com.huadiao.service.AbstractMessageService;
import com.huadiao.service.ReplyMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2023 年 10 月 09 日
 */
@Slf4j
@Service
public class ReplyMessageServiceImpl extends AbstractMessageService implements ReplyMessageService {
    private ReplyMessageMapper replyMessageMapper;

    @Autowired
    public ReplyMessageServiceImpl(ReplyMessageMapper replyMessageMapper) {
        this.replyMessageMapper = replyMessageMapper;
    }

    @Override
    public Result<?> getReplyMessage(Integer uid, String userId, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取回复消息, offset: {}, row: {}", uid, userId, offset, row);
        Map<String, Object> map = new HashMap<>(2);
        Result<?> result = checkOffsetAndRow(offset, row, (o, r) -> {
            List<ReplyComment> replyMessageList = replyMessageMapper.selectReplyCommentMessage(uid, o, r);
            map.put("replyMessageList", replyMessageList);

            if (o == 0 && !replyMessageList.isEmpty()) {
                replyMessageMapper.deleteLatestReplyMessage(uid);

                ReplyComment replyComment = replyMessageList.get(0);
                replyMessageMapper.insertLatestReplyMessage(replyComment);
            }
            return isEmpty(replyMessageList);
        });
        if(result.succeed()) {
            log.debug("uid, userId 分别为 {}, {} 的用户成功获取回复消息, offset: {}, row: {}", uid, userId, offset, row);
            log.trace("获取的结果为 map: {}", map);
            return Result.ok(map);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteReplyMessage(Integer uid, String userId, Integer noteId, Integer replyUid, Integer rootCommentId, Integer subCommentId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除回复消息, noteId: {}, replyUid: {}, rootCommentId: {}, subCommentId: {}", uid, userId, noteId, replyUid, rootCommentId, subCommentId);
        if(noteId == null || replyUid == null || rootCommentId == null || subCommentId == null) {
            return Result.errorParam();
        }
        replyMessageMapper.deleteReplyNoteMessage(replyUid, noteId, uid, rootCommentId, subCommentId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除回复消息, noteId: {}, replyUid: {}, rootCommentId: {}, subCommentId: {}", uid, userId, noteId, replyUid, rootCommentId, subCommentId);
        return Result.ok(null);
    }

    @Override
    public Result<Integer> countUnreadMessage(Integer uid, String userId) {
        return Result.ok(replyMessageMapper.countUnreadMessage(uid));
    }
}
