package com.huadiao.service.impl;

import com.huadiao.dto.note.NoteCommentDTO;
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

import java.util.List;

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
        return checkOffsetAndRow(offset, row, (o, r) -> {
            List<LikeMessage> likeMessageList = likeMessageMapper.selectNoteLikeMessageByUid(uid, o, r);

            if (o == 0 && likeMessageList.size() > 0) {
                LikeMessage likeMessage = likeMessageList.get(0);
                likeMessageMapper.deleteLatestReadMessage(uid);

                NoteCommentDTO noteCommentDTO = new NoteCommentDTO();
                noteCommentDTO.setUid(uid);
                noteCommentDTO.setNoteId(likeMessage.getNoteId());
                noteCommentDTO.setAuthorUid(likeMessage.getAuthorUid());
                noteCommentDTO.setReplyUid(likeMessage.getReplyUid());
                noteCommentDTO.setRepliedUid(likeMessage.getRepliedUid());
                noteCommentDTO.setRootCommentId(likeMessage.getRootCommentId());
                noteCommentDTO.setSubCommentId(likeMessage.getSubCommentId());

                likeMessageMapper.insertLatestMessage(noteCommentDTO);
            }
            return isEmpty(likeMessageList);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteLikeNoteMessage(Integer uid, NoteCommentDTO noteCommentDTO) {
        // 如果是评论
        if (noteCommentDTO.getRootCommentId() != null) {
            noteCommentDTO.setReplyUid(uid);
        }

        // 如果是笔记
        if (noteCommentDTO.getRootCommentId() == null && noteCommentDTO.getSubCommentId() == null) {
            noteCommentDTO.setAuthorUid(uid);
        }
        likeMessageMapper.deleteLikeNoteMessage(noteCommentDTO);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteLikeNoteMessage(Integer uid, String userId, Integer noteId, Integer rootCommentId, Integer subCommentId) {
        likeMessageMapper.deleteLikeCommentMessage(uid, noteId, rootCommentId, subCommentId);
        return Result.ok(null);
    }

    @Override
    public Result<?> getLikeNoteUser(Integer uid, String userId, Integer noteId, Integer offset, Integer row) {
        return checkOffsetAndRow(offset, row, (o, r) -> {
            List<LikeMessageUser> likeMessageUsers = likeMessageMapper.selectLikeNoteUser(uid, noteId, o, r);
            return isEmpty(likeMessageUsers);
        });
    }

    @Override
    public Result<?> getLikeCommentUser(Integer uid, String userId, Integer noteId, Integer rootCommentId, Integer subCommentId, Integer offset, Integer row) {
        return checkOffsetAndRow(offset, row, (o, r) -> {
            List<LikeMessageUser> likeMessageUsers = likeMessageMapper.selectLikeCommentUser(uid, noteId, rootCommentId, subCommentId, o, r);
            return isEmpty(likeMessageUsers);
        });
    }

    @Override
    public Result<Integer> countUnreadMessage(Integer uid, String userId) {
        return Result.ok(likeMessageMapper.countUnreadMessage(uid));
    }
}
