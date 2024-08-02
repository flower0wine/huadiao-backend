package com.huadiao.service.impl;

import com.huadiao.entity.NoteStarCatalogue;
import com.huadiao.entity.NoteStarInfo;
import com.huadiao.entity.Result;
import com.huadiao.entity.dao.NoteStarIdDao;
import com.huadiao.mapper.StarMapper;
import com.huadiao.service.AbstractStarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author flowerwine
 * @date 2023 年 09 月 24 日
 */
@Service
@Slf4j
public class StarServiceImpl extends AbstractStarService {
    private StarMapper starMapper;

    @Autowired
    public StarServiceImpl(StarMapper starMapper) {
        this.starMapper = starMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> addNoteStarGroup(Integer uid, String userId, String groupName, String groupDescription, Integer open) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增笔记收藏分组, groupName: {}, groupDescription: {}, open: {}", uid, userId, groupName, groupDescription, open);
        Result<?> result = checkNoteStarGroupParam(uid, userId, groupName, groupDescription, open);
        if(!result.succeed()) {
            return result;
        }
        int groupId = starJedisUtil.generateNoteStarGroupId();
        starMapper.insertNoteStarGroup(uid, groupName, groupDescription, groupId, open);
        Map<String, Integer> map = new HashMap<>(2);
        map.put("groupId", groupId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功新增笔记收藏分组, groupName: {}, groupDescription: {}, open: {}", uid, userId, groupName, groupDescription, open);
        return Result.ok(map);
    }

    private Result<?> checkNoteStarGroupParam(Integer uid, String userId, String groupName, String groupDescription, Integer open) {
        if (!checkGroupNameResult(uid, userId, groupName)) {
            return Result.errorParam();
        }
        if (!checkGroupDescriptionResult(uid, userId, groupDescription)) {
            return Result.errorParam();
        }
        if (!checkOpenResult(uid, userId, open)) {
            return Result.errorParam();
        }
        return Result.ok(null);
    }

    private boolean checkGroupNameResult(Integer uid, String userId, String groupName) {
        boolean checkGroupName = groupName != null && noteStarGroupNameMinLength <= groupName.length()
                && groupName.length() <= noteStarGroupNameMaxLength;
        if (!checkGroupName) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 groupName 存在问题, groupName: {}", uid, userId, groupName);
        }
        return checkGroupName;
    }

    private boolean checkGroupDescriptionResult(Integer uid, String userId, String groupDescription) {
        boolean checkGroupDescription = groupDescription != null &&
                (noteStarGroupDescriptionMinLength <= groupDescription.length() && groupDescription.length() <= noteStarGroupDescriptionMaxLength);
        if (!checkGroupDescription) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供了笔记收藏分组介绍, 但是字数不符合要求, 字符长度为: {}, 范围为 {} ~ {}", uid, userId, groupDescription.length(), noteStarGroupDescriptionMinLength, noteStarGroupDescriptionMaxLength);
        }
        return checkGroupDescription;
    }

    private boolean checkOpenResult(Integer uid, String userId, Integer open) {
        boolean checkOpen = open != null && (open == 0 || open == 1);
        if (!checkOpen) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供了是否公开笔记收藏分组, 但是不符合要求, open: {}, 应为 0 或 1", uid, userId, open);
        }
        return checkOpen;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteNoteStarGroup(Integer uid, String userId, Integer groupId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除笔记收藏分组, groupId: {}", uid, userId, groupId);
        if(groupId == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户尝试删除笔记收藏分组, 但是 groupId 为 null", uid, userId);
            return Result.blankParam();
        }
        if(groupId == defaultNoteStarGroupId) {
            log.debug("uid, userId 分别为 {}, {} 的用户尝试删除笔记收藏分组, 但是 groupId: {} 为默认分组", uid, userId, groupId);
            return Result.errorParam();
        }
        // 先删分组, 再删笔记收藏
        starMapper.deleteNoteStarGroup(uid, groupId);
        starMapper.deleteNoteStarByGroupId(uid, groupId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除笔记收藏分组, groupId: {}", uid, userId, groupId);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> modifyNoteStarGroup(Integer uid, String userId, String groupName, String groupDescription, Integer groupId, Integer open) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试修改笔记收藏分组, groupName: {}, groupDescription: {}, open: {}", uid, userId, groupName, groupDescription, open);
        Result<?> result = checkNoteStarGroupParam(uid, userId, groupName, groupDescription, open);
        if(!result.succeed()) {
            return result;
        }
        starMapper.updateNoteStarGroup(uid, groupName, groupDescription, groupId, open);
        log.debug("uid, userId 分别为 {}, {} 的用户成功修改笔记收藏分组, groupName: {}, groupDescription: {}, open: {}", uid, userId, groupName, groupDescription, open);
        return Result.ok(null);
    }

    @Override
    public Result<?> getNoteStar(Integer uid, String userId, Integer viewedUid, Integer groupId, Integer offset, Integer row) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取用户 uid 为 {} 的笔记收藏分组 groupId: {} 的笔记, offset: {}, row: {}", uid, userId, viewedUid, groupId, offset, row);
        if (offset == null || row == null) {
            offset = 0;
            row = 10;
        }
        if (viewedUid == null || groupId == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的参数为 null, viewedUid: null, groupId: null", uid, userId);
            return Result.blankParam();
        }
        List<NoteStarInfo> noteStarInfoList = starMapper.selectNoteStarByGroupId(viewedUid, groupId, offset, row);
        if (noteStarInfoList.size() == 0) {
            return Result.emptyData();
        }
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取用户 uid 为 {} 的笔记收藏分组 groupId: {} 的笔记, offset: {}, row: {}", uid, userId, viewedUid, groupId, offset, row);
        return Result.ok(noteStarInfoList);
    }

    @Override
    public Result<?> getNoteStarGroup(Integer uid, String userId, Integer viewedUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取用户 uid 为 {} 的笔记收藏分组", uid, userId, viewedUid);
        if (viewedUid == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 viewedUid 为 null", uid, userId);
            return Result.blankParam();
        }
        boolean me = uid.equals(viewedUid);
        // 获取笔记收藏分组, 不包含默认分组
        List<NoteStarCatalogue> noteStarCatalogues = starMapper.selectNoteStarCatalogueByUid(viewedUid);
        List<NoteStarCatalogue> noteStarCatalogueList = new ArrayList<>();
        // 获取默认分组笔记数量
        Integer defaultStarCount = starMapper.selectNoteStarCatalogueByGroupId(viewedUid, defaultNoteStarGroupId);
        noteStarCatalogueList.addAll(noteStarCatalogues);
        if (!me) {
            log.debug("非本人获取 uid: {} 的笔记收藏目录, viewedUid: {}", uid, viewedUid);
            // 对不进行公开的收藏夹进行剔除, 并且由于非本人, 不允许对文件夹进行操作
            noteStarCatalogueList.stream().filter(NoteStarCatalogue::getOpen).forEach((item) -> item.setAllowOperate(false));
        } else {
            noteStarCatalogueList.forEach((item) -> item.setAllowOperate(true));
        }
        NoteStarCatalogue defaultNoteStarCatalogue = getDefaultNoteStarCatalogue(defaultStarCount);
        noteStarCatalogueList.add(0, defaultNoteStarCatalogue);
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取用户 uid 为 {} 的笔记收藏分组", uid, userId, viewedUid);
        return Result.ok(noteStarCatalogueList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteNoteStar(Integer uid, String userId, Integer groupId, List<Integer> authorUidList, List<Integer> noteIdList) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除笔记收藏, 参数为 authorUidList: {}, noteIdList: {}", uid, userId, authorUidList, noteIdList);
        List<NoteStarIdDao> list = null;
        if (authorUidList != null && noteIdList != null) {
            if (authorUidList.size() != noteIdList.size()) {
                log.debug("uid, userId 分别为 {}, {} 的用户删除笔记收藏时, 参数 authorUidList 与 noteIdList 数量不一致, 分别为 {}, {}", uid, userId, authorUidList.size(), noteIdList.size());
                return Result.errorParam();
            }
            if (authorUidList.isEmpty()) {
                log.debug("uid, userId 分别为 {}, {} 的用户删除笔记收藏时, 参数 authorUidList, noteIdList 为空", uid, userId);
                authorUidList = null;
                noteIdList = null;
            } else {
                list = new ArrayList<>();
                for (int i = 0; i < authorUidList.size(); i++) {
                    NoteStarIdDao noteStarIdDao = new NoteStarIdDao();
                    noteStarIdDao.setAuthorUid(authorUidList.get(i));
                    noteStarIdDao.setNoteId(noteIdList.get(i));
                    list.add(noteStarIdDao);
                }
            }
        }
        starMapper.deleteNoteStar(uid, groupId, list);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除笔记收藏, 参数为 authorUidList: {}, noteIdList: {}", uid, userId, authorUidList, noteIdList);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> copyNoteStarToOtherGroup(Integer uid, String userId, Integer srcGroupId, Integer destGroupId, List<Integer> noteIdList, List<Integer> authorUidList) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试复制笔记收藏, 从分组 srcGroupId: {}, 到分组 destGroupId: {}, 其他参数 noteIdList: {}, authorUidList: {}", uid, userId, srcGroupId, destGroupId, noteIdList, authorUidList);
        Result<?> result = this.noteStarParamCheck(uid, userId, srcGroupId, destGroupId, noteIdList, authorUidList);
        if(!result.succeed()) {
            return result;
        }
        List<NoteStarIdDao> list = this.handleNoteStarInfo(noteIdList, authorUidList);
        starMapper.insertNoteStarToOtherGroup(uid, destGroupId, list);
        log.debug("uid, userId 分别为 {}, {} 的用户成功复制笔记收藏, 参数为 srcGroupId: {}, destGroupId: {}, noteIdList: {}, authorUidList: {}", uid, userId, srcGroupId, destGroupId, noteIdList, authorUidList);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> moveNoteStarToOtherGroup(Integer uid, String userId, Integer srcGroupId, Integer destGroupId, List<Integer> noteIdList, List<Integer> authorUidList) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试移动笔记收藏, 从分组 srcGroupId: {}, 到分组 destGroupId: {}, 其他参数 noteIdList: {}, authorUidList: {}", uid, userId, srcGroupId, destGroupId, noteIdList, authorUidList);
        Result<?> result = this.noteStarParamCheck(uid, userId, srcGroupId, destGroupId, noteIdList, authorUidList);
        if(!result.succeed()) {
            return result;
        }
        List<NoteStarIdDao> list = this.handleNoteStarInfo(noteIdList, authorUidList);
        // 先删除再新增, 避免主键冲突
        starMapper.deleteNoteStarReal(uid, srcGroupId, list);
        starMapper.insertNoteStarToOtherGroup(uid, destGroupId, list);
        log.debug("uid, userId 分别为 {}, {} 的用户成功移动笔记收藏, 参数为 srcGroupId: {}, destGroupId: {}, noteIdList: {}, authorUidList: {}", uid, userId, srcGroupId, destGroupId, noteIdList, authorUidList);
        return Result.ok(null);
    }

    private List<NoteStarIdDao> handleNoteStarInfo(List<Integer> noteIdList, List<Integer> authorUidList) {
        List<NoteStarIdDao> list = new ArrayList<>();
        for(int index = 0, length = noteIdList.size(); index < length; index++) {
            NoteStarIdDao noteStarIdDao = new NoteStarIdDao();
            noteStarIdDao.setNoteId(noteIdList.get(index));
            noteStarIdDao.setAuthorUid(authorUidList.get(index));
            list.add(noteStarIdDao);
        }
        return list;
    }

    private Result<?> noteStarParamCheck(Integer uid, String userId, Integer srcGroupId, Integer destGroupId, List<Integer> noteIdList, List<Integer> authorUidList) {
        if(noteIdList == null || noteIdList.size() == 0 || authorUidList == null || authorUidList.size() == 0) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 noteIdList 或 authorUidList 参数为 null", uid, userId);
            return Result.errorParam();
        }
        if(srcGroupId == null || destGroupId == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 srcGroupId 或 destGroupId 为 null", uid, userId);
            return Result.blankParam();
        }
        if(srcGroupId.equals(destGroupId)) {
            log.debug("uid, userId 分别为 {}, {} 提供的 srcGroupId 和 destGroupId 相同", uid, userId);
            return Result.existed();
        }
        if(noteIdList.size() != authorUidList.size()) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 noteIdList 与 authorUidList 参数数量不一致", uid, userId);
            return Result.errorParam();
        }
        return Result.ok(null);
    }
}
