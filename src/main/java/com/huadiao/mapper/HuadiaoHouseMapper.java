package com.huadiao.mapper;

import com.huadiao.entity.anime.AnimeInfo;
import com.huadiao.entity.anime.CardBorderImage;
import com.huadiao.entity.anime.HuadiaoHouseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HuadiaoHouseMapper {

    /**
     * 新增番剧信息
     * @param uid 用户 uid
     */
    void insertHuadiaoHouseInfoByUid(@Param("uid") Integer uid);

    /**
     * 新增封面边框
     * @param borderImageSlice borderImageSlice
     * @param borderImageWidth borderImageWidth
     * @param borderImageSource borderImageSource
     * @param borderImageOutset borderImageOutset
     */
    void insertHuadiaoCardBorderImage(@Param("borderImageSlice") String borderImageSlice, @Param("borderImageWidth") String borderImageWidth,
                                      @Param("borderImageSource") String borderImageSource, @Param("borderImageOutset") String borderImageOutset);

    /**
     * 新增番剧信息
     * @param uid 用户 uid
     * @param animeId 番剧 id
     * @param animeTitle 番剧标题
     * @param animeCover 番剧封面
     */
    void insertHuadiaoAnimeByUid(@Param("uid") Integer uid, @Param("animeTitle") String animeTitle, @Param("animeCover") String animeCover,
                                 @Param("animeId") Integer animeId);

    /**
     * 新增番剧馆访问记录
     * @param uid 访问者 uid
     * @param viewedUid 被访问者 uid
     */
    void insertHuadiaoHouseVisit(@Param("uid") Integer uid, @Param("viewedUid") Integer viewedUid);

    /**
     * 根据用户 uid 获取番剧页面信息
     * @param uid 用户 uid
     * @return 返回番剧页面信息
     */
    HuadiaoHouseInfo selectHuadiaoHouseInfoByUid(@Param("uid") Integer uid);

    /**
     * 获取封面边框
     * @return 返回封面边框
     */
    List<CardBorderImage> selectCardBorderImage();

    /**
     * 获取番剧信息
     * @param uid 用户 uid
     * @return 返回番剧信息
     */
    List<AnimeInfo> selectAnimeInfoByUid(@Param("uid") Integer uid);

    /**
     * 获取用户番剧总数
     * @param uid 用户 uid
     * @return 番剧总数
     */
    Integer selectAnimeCountByUid(@Param("uid") Integer uid);

    /**
     * 修改番剧页面信息
     * @param uid 用户 uid
     * @param huadiaoHouseInfoMap 番剧信息
     */
    void updateHuadiaoHouseInfoByUid(@Param("uid") Integer uid, @Param("huadiaoHouseInfoMap") Map<String, String> huadiaoHouseInfoMap);

    /**
     * 删除番剧
     * @param uid 用户 uid
     * @param animeId 番剧 id
     */
    void deleteAnimeByUid(@Param("uid") Integer uid, @Param("animeId") Integer animeId);

}
