package com.huadiao.entity.anime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 09 月 12 日
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AnimeInfo {
    private String title;
    private String cover;
    private Integer animeId;
    private Date addTime;
}
