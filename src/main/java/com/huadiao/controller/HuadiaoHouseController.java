package com.huadiao.controller;

import com.huadiao.entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * 用户番剧页面控制器接口
 * @author flowerwine
 */
public interface HuadiaoHouseController extends Controller {
    /**
     * 获取番剧页面信息
     * @param session session 对象
     * @param uid 被浏览者 uid
     * @return 返回番剧页面信息
     */
    Result<?> getHuadiaoHouseInfo(HttpSession session, Integer uid);

    /**
     * 获取番剧封面边框
     * @param session session 对象
     * @return 返回番剧封面边框
     */
    Result<?> getCardBorerImage(HttpSession session);

    /**
     * 更新番剧页面信息, 不支持图片
     * @param session session 对象
     * @param map 请求体
     * @return 返回更新过程的提示
     */
    Result<?> updateHuadiaoHouseInfo(HttpSession session, Map<String, String> map);

    /**
     * 更新番剧页面信息, 仅支持图片
     * @param session session 对象
     * @param field 数据字段
     * @param background 背景文件
     * @return 返回更新过程中的提示
     * @throws IOException 保存图片可能失败
     */
    Result<?> updateHuadiaoHouseInfo(HttpSession session, String field, MultipartFile background) throws IOException;

    /**
     * 删除番剧
     * @param session session 对象
     * @param animeId 番剧 id
     * @return 返回删除过程中的提示
     */
    Result<?> deleteHuadiaoAnime(HttpSession session, Integer animeId);

    /**
     * 添加番剧
     * @param session session 对象
     * @param animeTitle 番剧标题
     * @param animeCover 番剧封面
     * @return 返回添加过程中的提示
     * @throws IOException 保存图片可能失败
     */
    Result<?> addHuadiaoAnime(HttpSession session, String animeTitle, MultipartFile animeCover) throws IOException;
}
