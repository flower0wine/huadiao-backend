package com.huadiao.mapper;

import com.huadiao.service.upload.video.UploadInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author flowerwine
 * @date 2024 年 04 月 22 日
 */
public interface VideoUploadMapper {

    /**
     * 保存上传信息, 依据 uid 和 filename 判断是否已存在, 已存在则覆盖 uploadSucceed
     * @param animeId 番剧 ID
     * @param uid 用户 uid
     * @param filename 服务端生成的文件名
     * @param size 文件大小
     * @param uploadSucceed 是否上传成功
     * @return 返回影响条数
     */
    int insertOrUpdateUploadInfo(@Param("id") Integer animeId, @Param("uid") Integer uid, @Param("filename") String filename,
                                 @Param("size") Long size, @Param("uploadSucceed") Boolean uploadSucceed);

    /**
     * 删除上传信息
     * @param uid 用户 uid
     * @param filename 服务端生成的文件名
     * @return 返回影响条数
     */
    int deleteUploadInfo(@Param("uid") Integer uid, @Param("filename") String filename);

    /**
     * 获取用户上传的文件信息
     * @param uid 用户 uid
     * @param offset 偏移量
     * @param row 行数
     * @return 返回上传信息
     */
    List<UploadInfo> selectUploadInfo(@Param("uid") Integer uid, @Param("offset") Integer offset, @Param("row") Integer row);
}
