package com.huadiao.service.elasticsearch.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.elasticsearch.NoteEs;
import com.huadiao.entity.elasticsearch.NoteHistoryEs;
import com.huadiao.entity.elasticsearch.UserEs;
import com.huadiao.mapper.NoteOperateMapper;
import com.huadiao.service.elasticsearch.AbstractNoteHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
@Slf4j
@Service
public class NoteHistoryEsServiceImpl extends AbstractNoteHistoryService {
    private NoteOperateMapper noteOperateMapper;

    @Autowired
    public NoteHistoryEsServiceImpl(NoteOperateMapper noteOperateMapper) {
        this.noteOperateMapper = noteOperateMapper;
    }

    @Override
    public Result<?> findNoteHistoryByNoteTitle(Integer uid, String userId, String searchNoteTitle, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试查询笔记访问历史, searchNoteTitle 为 {}, offset 为 {}, row 为 {}", uid, userId, searchNoteTitle, offset, row);
        Result<?> result = checkOffsetAndRow(offset, row, (o, r) -> {
            Pageable pageable = PageRequest.of(o, r);
            List<NoteHistoryEs> noteHistoryEsList;
            // 先获取笔记的 id
            if(searchNoteTitle == null || searchNoteTitle.isEmpty()) {
                noteHistoryEsList = noteHistoryRepository.findAllByUid(uid, pageable);
            } else {
                noteHistoryEsList = noteHistoryRepository.findByNoteTitleAndUid(searchNoteTitle, uid, pageable);
            }
            List<Integer> list = noteHistoryEsList.stream().map(NoteHistoryEs::getNoteId).collect(Collectors.toList());
            // 再根据笔记 id 获取笔记信息, 为了减少信息冗余
            Iterable<NoteEs> noteIterable = noteRepository.findAllById(list);
            List<NoteEs> noteEsList = new ArrayList<>();
            noteIterable.forEach(noteEsList::add);
            noteEsList.forEach((item) -> {
                Optional<UserEs> userEsOptional = userRepository.findById(item.getAuthorUid());
                userEsOptional.ifPresent(item::setAuthor);
            });
            return Result.ok(noteEsList);
        });
        if (result.succeed()) {
            log.debug("uid, userId 分别为 {}, {} 的用户成功查询笔记访问历史, searchNoteTitle 为 {}, offset 为 {}, row 为 {}", uid, userId, searchNoteTitle, offset, row);
        }
        return result;
    }

    @Override
    public Result<?> deleteAllByUid(Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除笔记访问历史", uid, userId);
        // 删除 mysql 中的数据
        noteOperateMapper.deleteAllNoteHistoryByUid(uid);
        // 删除 es 中的数据
        noteHistoryRepository.deleteAllByUid(uid);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除笔记访问历史", uid, userId);
        return Result.ok(null);
    }

    @Override
    public Result<?> deleteSpecificNoteHistory(Integer uid, String userId, Integer noteId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除指定的笔记访问历史, noteId 为 {}", uid, userId, noteId);
        if (noteId == null || noteId < 0) {
            return Result.errorParam();
        }
        // 删除 mysql 中的数据
        noteOperateMapper.deleteSpecificNoteHistoryByUid(uid, noteId);
        // 删除 es 中的数据
        noteHistoryRepository.deleteByCompositionId(NoteHistoryEs.generateCompositionId(uid, noteId));
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除指定的笔记访问历史, noteId 为 {}", uid, userId, noteId);
        return Result.ok(null);
    }
}
