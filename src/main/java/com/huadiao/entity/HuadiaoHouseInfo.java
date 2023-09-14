package com.huadiao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 09 月 12 日
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class HuadiaoHouseInfo {
    private Integer uid;
    private String titleColor;
    private String titleBackground;
    private String pageBackground;
    private String pageForeground;
    private Integer cardBorderImage;
    private String cardBackground;
    private List<AnimeInfo> animeList;
}
