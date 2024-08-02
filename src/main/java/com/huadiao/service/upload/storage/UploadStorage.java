package com.huadiao.service.upload.storage;

import java.util.List;

/**
 * @author flowerwine
 * @date 2024 年 04 月 22 日
 */
public interface UploadStorage<R> {

    /**
     * 保存上传信息, 依据 uid 和 filename 判断是否已存在, 已存在则覆盖 uploadSucceed
     * @param animeId 番剧 id
     * @param uid 用户 uid
     * @param filename 服务端生成的文件名
     * @param size 文件大小
     * @param uploadSucceed 是否上传成功
     * @return 返回是否保存成功
     */
    boolean saveUploadInfo(Integer animeId, Integer uid, String filename, Long size, Boolean uploadSucceed);

    /**
     * 获取上传信息
     * @param uid 用户 uid
     * @param offset 偏移量
     * @param row 行数
     * @return 返回上传信息
     */
    List<R> getUploadInfo(Integer uid, Integer offset, Integer row);

    /**
     * 删除上传信息
     * @param uid 用户 uid
     * @param filename 服务端生成的文件名
     * @return 返回是否删除成功
     */
    boolean deleteUploadInfo(Integer uid, String filename);

    /**
     * 匹配存储引擎
     * @param storageEngineEnum 存储引擎枚举
     * @return 返回是否匹配成功
     */
    boolean match(StorageEngineEnum storageEngineEnum);
}
