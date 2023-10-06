package com.huadiao.service.impl;

import com.huadiao.entity.AnimeHistory;
import com.huadiao.entity.NoteHistory;
import com.huadiao.entity.Result;
import com.huadiao.mapper.HistoryMapper;
import com.huadiao.service.AbstractHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author flowerwine
 * @date 2023 年 09 月 17 日
 */
@Slf4j
@Service
public class HistoryServiceImpl extends AbstractHistoryService {

    private HistoryMapper historyMapper;

    @Autowired
    public HistoryServiceImpl(HistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }

    @Override
    public Result<?> getNoteHistory(Integer uid, String userId, Integer row, Integer offset) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取笔记访问历史记录, row: {}, offset: {}", uid, userId, row, offset);
        if(row == null || offset == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的参数 row 或 offset 为 null, row: {}, offset: {}, 将设为默认值", uid, userId, row, offset);
            row = 10;
            offset = 0;
        }
        row = row > requestMaxRow ? requestMaxRow : row;
        List<NoteHistory> noteHistoryList = historyMapper.selectNoteHistoryByUid(uid, row, offset);
        if(noteHistoryList.size() == 0) {
            log.debug("笔记访问历史记录获取数据条数为 0");
            return Result.notExist();
        }
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取笔记访问历史记录, row: {}, offset: {}", uid, userId, row, offset);
        return Result.ok(noteHistoryList);
    }

    @Override
    public Result<?> getAnimeHistory(Integer uid, String userId, Integer row, Integer offset) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取番剧馆访问历史记录, row: {}, offset: {}", uid, userId, row, offset);
        if(row == null || offset == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的参数 row 或 offset 为 null, row: {}, offset: {}, 将设为默认值", uid, userId, row, offset);
            row = 10;
            offset = 0;
        }
        row = row > requestMaxRow ? requestMaxRow : row;
        List<AnimeHistory> animeHistoryList = historyMapper.selectAnimeHistoryByUid(uid, row, offset);
        animeHistoryList = animeHistoryList.stream().distinct().collect(Collectors.toList());
        if(animeHistoryList.size() == 0) {
            log.debug("番剧馆访问历史记录获取数据条数为 0");
            return Result.notExist();
        }
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取番剧馆访问历史记录, row: {}, offset: {}", uid, userId, row, offset);
        return Result.ok(animeHistoryList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteNoteHistory(Integer uid, String userId, Integer authorUid, Integer noteId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除特定的笔记访问记录, viewedUid: {}, noteId: {}", uid, userId, authorUid, noteId);
        if(authorUid != null && noteId != null) {
            log.debug("uid, userId 分别为 {}, {} 的用户删除特定的笔记访问记录, authorUid: {}, noteId: {}", uid, userId, authorUid, noteId);
            historyMapper.deleteSpecificNoteHistory(uid, authorUid, noteId);
        } else {
            log.debug("uid, userId 分别为 {}, {} 的用户删除所有的笔记访问记录, authorUid: {}, noteId: {}", uid, userId, authorUid, noteId);
            historyMapper.deleteAllNoteHistory(uid);
        }
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除特定的笔记访问记录, viewedUid: {}, noteId: {}", uid, userId, authorUid, noteId);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteAnimeHistory(Integer uid, String userId, Integer viewedUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除番剧馆访问记录, viewedUid: {}", uid, userId, viewedUid);
        if(viewedUid != null) {
            log.debug("uid, userId 分别为 {}, {} 的用户选择删除特定的番剧馆访问记录, viewedUid: {}", uid, userId, viewedUid);
            historyMapper.deleteSpecificAnimeHistory(uid, viewedUid);
        } else {
            log.debug("uid, userId 分别为 {}, {} 的用户选择删除所有的番剧馆访问记录, viewedUid: {}", uid, userId, viewedUid);
            historyMapper.deleteAllAnimeHistory(uid);
        }
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除特定的番剧馆访问记录, viewedUid: {}", uid, userId, viewedUid);
        return Result.ok(null);
    }
}
