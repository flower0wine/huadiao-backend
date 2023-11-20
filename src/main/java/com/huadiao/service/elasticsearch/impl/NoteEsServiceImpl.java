package com.huadiao.service.elasticsearch.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.elasticsearch.NoteEs;
import com.huadiao.entity.elasticsearch.UserEs;
import com.huadiao.service.elasticsearch.AbstractNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * @author flowerwine
 * @date 2023 年 11 月 11 日
 */
@Slf4j
@Service
public class NoteEsServiceImpl extends AbstractNoteService {

    @Override
    public Result<?> findNoteByNoteTitle(Integer uid, String userId, String searchNoteTitle, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试查询笔记, searchNoteTitle 为 {}, offset: {}, row: {}", uid, userId, searchNoteTitle, offset, row);
        if (searchNoteTitle == null || searchNoteTitle.isEmpty()) {
            log.debug("uid, userId 分别为 {}, {} 的用户尝试查询笔记, searchNoteTitle 为空", uid, userId);
        }
        Result<?> result = checkOffsetAndRow(offset, row, (o, r) -> {
            Pageable pageable = PageRequest.of(o, r);
            List<NoteEs> noteEsList = noteRepository.findByTitle(searchNoteTitle, pageable);
            noteEsList.forEach((item) -> {
                Optional<UserEs> optionalUserEs = userRepository.findById(item.getAuthorUid());
                item.setAuthor(optionalUserEs.orElse(null));
            });
            return Result.ok(noteEsList);
        });
        if (result.succeed()) {
            log.debug("uid, userId 分别为 {}, {} 的用户成功查询笔记, searchNoteTitle 为 {}, offset: {}, row: {}", uid, userId, searchNoteTitle, offset, row);
        }
        return result;
    }

    @Override
    public Result<?> findNoteByNoteSummary(Integer uid, String userId, String searchNoteSummary, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试查询笔记, searchNoteSummary 为 {}, offset: {}, row: {}", uid, userId, searchNoteSummary, offset, row);
        if (searchNoteSummary == null || searchNoteSummary.isEmpty()) {
            log.debug("uid, userId 分别为 {}, {} 的用户尝试查询笔记, searchNoteTitle 为空", uid, userId);
        }
        Result<?> result = checkOffsetAndRow(offset, row, (o, r) -> {
            Pageable pageable = PageRequest.of(o, r);
            List<NoteEs> noteEsList = noteRepository.findBySummary(searchNoteSummary, pageable);
            noteEsList.forEach((item) -> {
                Optional<UserEs> optionalUserEs = userRepository.findById(item.getAuthorUid());
                item.setAuthor(optionalUserEs.orElse(null));
            });
            return Result.ok(noteEsList);
        });
        if (result.succeed()) {
            log.debug("uid, userId 分别为 {}, {} 的用户成功查询笔记, searchNoteSummary 为 {}, offset: {}, row: {}", uid, userId, searchNoteSummary, offset, row);
        }
        return result;
    }
}
