package com.huadiao.service.impl;

import com.huadiao.entity.*;
import com.huadiao.entity.account.AccountSettings;
import com.huadiao.entity.anime.AnimeInfo;
import com.huadiao.entity.anime.CardBorderImage;
import com.huadiao.entity.anime.HuadiaoHouseInfo;
import com.huadiao.mapper.HuadiaoHouseMapper;
import com.huadiao.service.AbstractHuadiaoHouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author flowerwine
 * @date 2023 年 09 月 12 日
 */
@Slf4j
@Service
public class HuadiaoHouseServiceImpl extends AbstractHuadiaoHouseService {
    private HuadiaoHouseMapper huadiaoHouseMapper;

    @Autowired
    public HuadiaoHouseServiceImpl(HuadiaoHouseMapper huadiaoHouseMapper) {
        this.huadiaoHouseMapper = huadiaoHouseMapper;
    }

    @Override
    public Result<?> getHuadiaoHouseInfo(Integer uid, String userId, Integer viewedUid) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取 uid 为 {} 的用户的番剧信息", uid, userId, viewedUid);
        boolean me = uid.equals(viewedUid);
        if(!me) {
            AccountSettings accountSettings = userSettingJedisUtil.getAccountSettings(viewedUid);
            // 如果用户选择不公开个人主页信息
            if (!accountSettings.getPublicFanjuStatus()) {
                log.debug("uid 为 {} 的用户不公开番剧信息", viewedUid);
                return Result.notAllowed();
            }
        }
        HuadiaoHouseInfo huadiaoHouseInfo = huadiaoHouseMapper.selectHuadiaoHouseInfoByUid(viewedUid);
        if(huadiaoHouseInfo == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的 viewedUid: {} 不存在", uid, userId, viewedUid);
            return Result.pageNotExist();
        }
        List<AnimeInfo> animeList = huadiaoHouseMapper.selectAnimeInfoByUid(viewedUid);
        huadiaoHouseInfo.setAnimeList(animeList);
        // 新增番剧馆访问记录
        if(!uid.equals(viewedUid)) {
            huadiaoHouseMapper.insertHuadiaoHouseVisit(uid, viewedUid);
        }
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取 uid 为 {} 的用户的番剧信息", uid, userId, viewedUid);
        return Result.ok(huadiaoHouseInfo);
    }

    @Override
    public Result<?> getHuadiaoHouseCardBorderImage(Integer uid, String userId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试获取番剧封面边框", uid, userId);
        List<CardBorderImage> cardBorderImageList = huadiaoHouseJedisUtil.getHuadiaoCardBorderImage();
        log.debug("uid, userId 分别为 {}, {} 的用户成功获取番剧封面边框", uid, userId);
        return Result.ok(cardBorderImageList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> updateHuadiaoHouseInfo(Integer uid, String userId, Map<String, String> map) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试更新番剧信息, 非图片, map: {}", uid, userId, map);
        Set<String> infoFields = map.keySet();
        Map<String, String> transformedMap = new HashMap<>(2);
        for (String field : infoFields) {
            if(!pattern.matcher(field).find()) {
                log.debug("uid, userId 分别为 {}, {} 的用户提供的参数键名存在错误, field: {}", uid, userId, field);
                return Result.errorParam();
            }
            String key = fieldFormat(field);
            transformedMap.put(key, map.get(field));
        }
        huadiaoHouseMapper.updateHuadiaoHouseInfoByUid(uid, transformedMap);
        log.debug("uid, userId 分别为 {}, {} 的用户成功更新番剧信息, map: {}", uid, userId, map);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> updateHuadiaoHouseInfo(Integer uid, String userId, String field, MultipartFile background) throws IOException {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试更新番剧信息, 上传图片作为背景, field: {}", uid, userId, field);
        if(!pattern.matcher(field).find()) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的参数键名存在错误, field: {}", uid, userId, field);
            return Result.errorParam();
        }
        String key = fieldFormat(field);
        if(background.isEmpty()) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的参数为空, background: {}", uid, userId, background);
            return Result.blankParam();
        }
        long backgroundSize = background.getSize();
        if(backgroundSize > backgroundPictureMaxSize) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的背景过大, animeCoverSize: {}", uid, userId, backgroundSize);
            return Result.exceedLimit();
        }
        String originalFilename = background.getOriginalFilename();
        if(originalFilename == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的图片名称无法获取, 为 null", uid, userId);
            return Result.errorParam();
        }
        String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // 保存文件
        background.transferTo(new File(imageRealPath + File.separator + filename));
        Map<String, String> map = new HashMap<>(2);
        map.put(key, filename);
        huadiaoHouseMapper.updateHuadiaoHouseInfoByUid(uid, map);
        log.debug("uid, userId 分别为 {}, {} 的用户成功更新番剧信息, 上传图片作为背景, field: {}", uid, userId, field);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteHuadiaoAnime(Integer uid, String userId, Integer animeId) {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试删除番剧, animeId: {}", uid, userId, animeId);
        huadiaoHouseMapper.deleteAnimeByUid(uid, animeId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功删除番剧, animeId: {}", uid, userId, animeId);
        return Result.ok(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> addHuadiaoAnime(Integer uid, String userId, String animeTitle, MultipartFile animeCover) throws IOException {
        log.debug("uid, userId 分别为 {}, {} 的用户尝试新增番剧, animeTitle: {}", uid, userId, animeTitle);
        if(animeTitle == null || animeCover == null) {
            return Result.blankParam();
        }
        if(!(animeTitleMinLength <= animeTitle.length() && animeTitle.length() <= animeTitleMaxLength)) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的番剧名称字符长度不符合要求, animeTitle: {}", uid, userId, animeTitle);
            return Result.errorParam();
        }
        if(animeCover.isEmpty()) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的参数为空, animeCover: {}", uid, userId, animeCover);
            return Result.blankParam();
        }
        long animeCoverSize = animeCover.getSize();
        if(animeCoverSize > animeCoverMaxSize) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的番剧封面过大, animeCoverSize: {}", uid, userId, animeCoverSize);
            return Result.exceedLimit();
        }
        String originalFilename = animeCover.getOriginalFilename();
        if(originalFilename == null) {
            log.debug("uid, userId 分别为 {}, {} 的用户提供的图片名称无法获取, 为 null", uid, userId);
            return Result.errorParam();
        }
        String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // 保存文件
        animeCover.transferTo(new File(imageRealPath + File.separator + filename));
        // 强制转型
        int animeId = huadiaoHouseJedisUtil.generateAnimeId();
        Map<String, Object> map = new HashMap<>(2);
        map.put("animeId", animeId);
        huadiaoHouseMapper.insertHuadiaoAnimeByUid(uid, animeTitle, filename, animeId);
        log.debug("uid, userId 分别为 {}, {} 的用户成功新增番剧, animeTitle: {}", uid, userId, animeTitle);
        return Result.ok(map);
    }
}
