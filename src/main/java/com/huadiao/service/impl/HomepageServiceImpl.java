package com.huadiao.service.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.account.AccountSettings;
import com.huadiao.entity.dto.userinfo.UserInfoDto;
import com.huadiao.entity.homepage.HomepageInfo;
import com.huadiao.mapper.*;
import com.huadiao.service.AbstractFollowFanService;
import com.huadiao.service.AbstractHomepageService;
import com.huadiao.service.FollowFanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Service
@Slf4j
public class HomepageServiceImpl extends AbstractHomepageService {

    private UserInfoMapper userInfoMapper;
    private UserMapper userMapper;
    private FollowFanMapper followFanMapper;
    private UserSettingsMapper userSettingsMapper;
    private HomepageMapper homepageMapper;
    private NoteMapper noteMapper;
    private HuadiaoHouseMapper huadiaoHouseMapper;

    @Autowired
    public HomepageServiceImpl(UserInfoMapper userInfoMapper, UserMapper userMapper, FollowFanMapper followFanMapper, UserSettingsMapper userSettingsMapper, HomepageMapper homepageMapper, NoteMapper noteMapper, HuadiaoHouseMapper huadiaoHouseMapper) {
        this.userInfoMapper = userInfoMapper;
        this.userMapper = userMapper;
        this.followFanMapper = followFanMapper;
        this.userSettingsMapper = userSettingsMapper;
        this.homepageMapper = homepageMapper;
        this.noteMapper = noteMapper;
        this.huadiaoHouseMapper = huadiaoHouseMapper;
    }

    private void viewedUidNotExist(Integer uid, String userId, Integer viewedUid) {
        log.debug("uid, userId 分别为 {}, {} 提供的 uid 为 {} 的个人主页不存在", uid, userId, viewedUid);
    }

    @Override
    public Result<?> getHomepageInfo(Integer uid, String userId, Integer viewedUid) {
        if(viewedUid == null) {
            viewedUidNotExist(uid, userId, viewedUid);
            return Result.pageNotExist();
        }

        // 判断被访问者是否存在
        String viewerUserId = userMapper.selectUserIdByUid(viewedUid);
        if (viewerUserId == null) {
            viewedUidNotExist(uid, userId, viewedUid);
            return Result.pageNotExist();
        }

        boolean me = uid != null && uid.equals(viewedUid);
        UserInfoDto userInfoDto = userInfoMapper.selectUserInfoByUid(viewedUid);
        // 如果不是本人
        if (!me) {
            AccountSettings accountSettings = userSettingJedisUtil.getAccountSettings(viewedUid);
            // 如果用户选择不公开个人主页信息
            if (!accountSettings.getPublicHomepageStatus()) {
                log.debug("uid 为 {} 的用户不公开个人主页信息", viewedUid);
                return Result.notAllowed();
            }
            // 如果用户选择不公开出生日期
            if(!accountSettings.getPublicBornStatus()) {
                log.debug("uid 为 {} 的用户不公开出生日期", viewedUid);
                userInfoDto.setBornDate(null);
            }
            // 如果用户选择不公开学校信息
            if(!accountSettings.getPublicSchoolStatus()) {
                log.debug("uid 为 {} 的用户不公开学校信息", viewedUid);
                userInfoDto.setSchool(null);
            }
            // 如果用户选择不公开个人介绍
            if(!accountSettings.getPublicCanvasesStatus()) {
                log.debug("uid 为 {} 的用户不公开个人介绍", viewedUid);
                userInfoDto.setCanvases(null);
            }
            else {
                if(userInfoDto.getCanvases() == null) {
                    userInfoDto.setCanvases(defaultCanvases);
                }
            }
        }
        else {
            if(userInfoDto.getCanvases() == null) {
                userInfoDto.setCanvases(defaultCanvases);
            }
        }

        // 获取个人主页信息
        List<Integer> followFan = followFanMapper.countFollowAndFansByUid(viewedUid);
        List<Integer> relation = followFanMapper.selectRelationByBothUid(uid, viewedUid);
        HomepageInfo homepageInfo = homepageMapper.selectHomepageInfoByUid(viewedUid);
        Integer noteStarCount = noteMapper.selectAllNoteStarCountByUid(viewedUid);
        Integer noteLikeCount = noteMapper.selectAllNoteLikeCountByUid(viewedUid);
        Integer noteCount = noteMapper.countNoteByUid(viewedUid);
        Integer animeCount = huadiaoHouseMapper.selectAnimeCountByUid(viewedUid);

        // 装填参数
        Map<String, Object> map = new HashMap<>(10);
        map.put("followCount", followFan.get(FollowFanService.FOLLOW_INDEX));
        map.put("fanCount", followFan.get(FollowFanService.FAN_INDEX));
        map.put("noteStarCount", noteStarCount);
        map.put("noteLikeCount", noteLikeCount);
        map.put("noteCount", noteCount);
        map.put("animeCount", animeCount);
        map.put("userInfo", userInfoDto);
        map.put("homepageInfo", homepageInfo);
        map.put("relation", AbstractFollowFanService.judgeRelationBetweenBoth(relation));
        map.put("uid", uid);
        map.put("me", me);

        // 不是本人添加访问记录
        if (uid != null && !me) {
            insertVisitRecord(uid, userId, viewedUid);
        }
        return Result.ok(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertVisitRecord(Integer uid, String userId, Integer viewedUid) {
        log.debug("尝试为 uid, userId 分别为 {}, {} 的用户的个人主页新增访问记录, 访问者 uid: {}", uid, userId, viewedUid);
        homepageMapper.insertVisitRecordByUid(uid, viewedUid);
        log.debug("为 uid, userId 分别为 {}, {} 的用户的个人主页新增访问记录成功, 访问者 uid: {}", uid, userId, viewedUid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> updateUserAvatar(Integer uid, String userId, MultipartFile userAvatar) throws IOException {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试修改自己的头像", uid, userId);
        if(userAvatar == null || userAvatar.isEmpty()) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 userAvatar 参数为 null", uid, userId);
            return Result.blankParam();
        }
        long size = userAvatar.getSize();
        if(size > userAvatarMaxSize) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的头像文件过大", uid, userId);
            return Result.exceedLimit();
        }
        String originalFilename = userAvatar.getOriginalFilename();
        if(originalFilename == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的图片名称无法获取, 为 null", uid, userId);
            return Result.errorParam();
        }
        String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // 保存文件
        userAvatar.transferTo(new File(userAvatarRealPath + File.separator + filename));
        userInfoJedisUtil.modifyUserAvatarByUid(uid, filename);
        // 修改 es 的用户头像
        /*UserEs sourceUserEs;
        Optional<UserEs> optionalUserEs = userRepository.findById(uid);
        UserEs userEs = new UserEs();
        userEs.setUid(uid);
        userEs.setAvatar(filename);
        if (optionalUserEs.isPresent()) {
            sourceUserEs = optionalUserEs.get();
            BeanUtil.moveProperties(userEs, sourceUserEs);
        }
        userRepository.save(userEs);*/
        log.debug("uid, userId 分别为 {}, {} 的用户成功修改自己的头像", uid, userId);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> updateHomepageBackground(Integer uid, String userId, MultipartFile background) throws IOException {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试修改个人主页背景", uid, userId);
        if(background == null || background.isEmpty()) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 background 参数为 null", uid, userId);
            return Result.blankParam();
        }
        long size = background.getSize();
        if(size > backgroundMaxSize) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的个人主页背景文件过大", uid, userId);
            return Result.exceedLimit();
        }
        String originalFilename = background.getOriginalFilename();
        if(originalFilename == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的图片名称无法获取, 为 null", uid, userId);
            return Result.errorParam();
        }
        String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // 保存文件
        background.transferTo(new File(homepageBackgroundRealPath + File.separator + filename));
        homepageMapper.updateHomepageBackgroundByUid(uid, filename);
        log.debug("uid, userId 分别为 {}, {} 的用户成功修改个人主页背景", uid, userId);
        return Result.ok(null);
    }
}
