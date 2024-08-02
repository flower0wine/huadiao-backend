package com.huadiao.service.upload.fragment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author flowerwine
 * @date 2024 年 04 月 20 日
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class PreloadReturnValue {
    private List<Integer> uploadedChunk;
    private Integer chunkSize;
    private FileInfo fileInfo;
}
