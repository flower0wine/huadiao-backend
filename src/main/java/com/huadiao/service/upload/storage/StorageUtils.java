package com.huadiao.service.upload.storage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerwine
 * @date 2024 年 04 月 22 日
 */
public class StorageUtils {

    /**
     * 保存上传信息
     * @param storageList 存储器集合
     * @param uid 用户 uid
     * @param filename 服务端生成的文件名
     * @param size 文件大小
     * @param uploadSucceed 是否上传成功
     */
    public static <R> boolean save(List<? extends UploadStorage<R>> storageList, Integer animeId, Integer uid, String filename, Long size, Boolean uploadSucceed) {
        boolean res = true;
        for (UploadStorage<R> storage : storageList) {
            res &= storage.saveUploadInfo(animeId, uid,  filename, size, uploadSucceed);
        }
        return res;
    }

    /**
     * 修改上传信息
     * @param storageList 存储器集合
     * @param uid 用户 uid
     * @param filename 服务端生成的文件名
     * @param uploadSucceed 是否上传成功
     */
    public static <R> boolean update(List<? extends UploadStorage<R>> storageList, Integer animeId, Integer uid, String filename, Boolean uploadSucceed) {
        boolean res = true;
        for (UploadStorage<R> storage : storageList) {
            res &= storage.saveUploadInfo(animeId, uid,  filename, null, uploadSucceed);
        }
        return res;
    }

    /**
     * 删除上传信息
     * @param storageList 存储器集合
     * @param uid 用户 uid
     * @param filename 服务端生成的文件名
     */
    public static <R> boolean delete(List<? extends UploadStorage<R>> storageList, Integer uid, String filename) {
        boolean res = true;
        for (UploadStorage<R> storage : storageList) {
            res &= storage.deleteUploadInfo(uid, filename);
        }
        return res;
    }

    /**
     * 获取用户的所有文件上传信息
     * @param storageList 存储器集合
     * @param storageEngineEnum 存储引擎枚举
     * @param uid 用户 uid
     * @param offset 偏移量
     * @param row 行数
     * @return 返回查询到的上传信息
     */
    public static <R> List<R> query(List<? extends UploadStorage<R>> storageList, StorageEngineEnum storageEngineEnum, Integer uid, Integer offset, Integer row) {
        for(UploadStorage<R> storage : storageList) {
            if(storage.match(storageEngineEnum)) {
                return storage.getUploadInfo(uid, offset, row);
            }
        }
        return new ArrayList<>();
    }
}
