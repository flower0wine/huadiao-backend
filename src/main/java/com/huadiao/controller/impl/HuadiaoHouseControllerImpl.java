package com.huadiao.controller.impl;

import com.huadiao.controller.HuadiaoHouseController;
import com.huadiao.entity.Result;
import com.huadiao.service.HuadiaoHouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @date 2023 年 09 月 12 日
 * @author flowerwine
 */

@Slf4j
@RestController
@RequestMapping("/huadiaohouse")
public class HuadiaoHouseControllerImpl implements HuadiaoHouseController {
    private HuadiaoHouseService huadiaoHouseService;

    @Autowired
    public HuadiaoHouseControllerImpl(HuadiaoHouseService huadiaoHouseService) {
        this.huadiaoHouseService = huadiaoHouseService;
    }

    @Override
    @GetMapping("/info")
    public Result<?> getHuadiaoHouseInfo(HttpSession session, Integer uid) {
        Integer myUid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return huadiaoHouseService.getHuadiaoHouseInfo(myUid, userId, uid);
    }

    @Override
    @GetMapping("/border")
    public Result<?> getCardBorerImage(HttpSession session) {
        Integer myUid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return huadiaoHouseService.getHuadiaoHouseCardBorderImage(myUid, userId);
    }

    @Override
    @PostMapping("/info/modify")
    public Result<?> updateHuadiaoHouseInfo(HttpSession session, @RequestBody Map<String, String> map) {
        Integer myUid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return huadiaoHouseService.updateHuadiaoHouseInfo(myUid, userId, map);
    }

    @Override
    @PostMapping("/info/picture/modify")
    public Result<?> updateHuadiaoHouseInfo(HttpSession session, String field, MultipartFile background) throws IOException {
        Integer myUid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return huadiaoHouseService.updateHuadiaoHouseInfo(myUid, userId, field, background);
    }

    @Override
    @GetMapping("/anime/delete")
    public Result<?> deleteHuadiaoAnime(HttpSession session, Integer animeId) {
        Integer myUid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return huadiaoHouseService.deleteHuadiaoAnime(myUid, userId, animeId);
    }

    @Override
    @PostMapping("/anime/add")
    public Result<?> addHuadiaoAnime(HttpSession session, String animeTitle, MultipartFile animeCover) throws IOException {
        Integer myUid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");
        return huadiaoHouseService.addHuadiaoAnime(myUid, userId, animeTitle, animeCover);
    }

}
