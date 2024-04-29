package com.huadiao.entity.upload.video;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2024 年 04 月 22 日
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UploadInfo {
    private Integer animeId;
    private Integer uid;
    private String filename;
    private Date uploadTime;
    private Long size;
    private Boolean uploadSucceed;
}
