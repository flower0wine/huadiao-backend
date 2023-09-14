package com.huadiao.service;

import com.huadiao.entity.Result;

/**
 * 业务层: 处理个人主页的接口
 * @author flowerwine
 */
public interface HomepageService {

    /**
     * 根据 uid 获取个人主页信息
     * @param uid 访问者 uid
     * @param userId 用户 id
     * @param viewedUid 被访问者
     * @return 返回查询过程中的提示
     */
    Result<?> getHomepageInfo(Integer uid, String userId, Integer viewedUid);

    /**
     * 新增个人主页访问记录
     * @param uid 访问者
     * @param userId 访问者 id
     * @param viewedUid 被访问者
     */
    void insertVisitRecord(Integer uid, String userId, Integer viewedUid);
}
