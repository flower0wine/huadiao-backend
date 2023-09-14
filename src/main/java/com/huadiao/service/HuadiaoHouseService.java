package com.huadiao.service;

import com.huadiao.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Map;

/**
 * @date 2023年09月12日
 * @author flowerwine
 */
public interface HuadiaoHouseService {
    /**
     * 获取番剧页面信息
     * @param uid 浏览者 uid
     * @param userId 用户 id
     * @param viewedUid 被浏览者 uid
     * @return 返回番剧信息
     */
    Result<?> getHuadiaoHouseInfo(Integer uid, String userId, Integer viewedUid);

    /**
     * 获取番剧封面边框
     * @param uid 获取者 uid
     * @param userId 用户 id
     * @return 返回番剧封面边框
     */
    Result<?> getHuadiaoHouseCardBorderImage(Integer uid, String userId);

    /**
     * 更新番剧页面信息, 不支持图片
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param map 请求体
     * @return 返回番剧信息
     */
    Result<?> updateHuadiaoHouseInfo(Integer uid, String userId, Map<String, String> map);

    /**
     * 更新番剧页面信息, 仅支持图片
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param field 数据字段
     * @param background 背景文件
     * @return 返回更新过程中的提示
     * @throws IOException 保存图片可能失败
     */
    Result<?> updateHuadiaoHouseInfo(Integer uid, String userId, String field, MultipartFile background) throws IOException;

    /**
     * 删除番剧
     * @param uid 用户 id
     * @param userId 用户 id
     * @param animeId 番剧 id
     * @return 返回删除过程提示
     */
    Result<?> deleteHuadiaoAnime(Integer uid, String userId, Integer animeId);

    /**
     * 新增番剧
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param animeTitle 番剧标题
     * @param animeCover 番剧封面
     * @return 返回添加番剧过程中的提示
     * @throws IOException 保存过程中可能失败
     */
    Result<?> addHuadiaoAnime(Integer uid, String userId, String animeTitle, MultipartFile animeCover) throws IOException;
}
