package com.huadiao.service;

import com.huadiao.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    /**
     * 修改用户头像
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param userAvatar 用户头像文件
     * @return 返回修改过程中的提示
     * @throws IOException 保存图片可能失败
     */
    Result<?> updateUserAvatar(Integer uid, String userId, MultipartFile userAvatar) throws IOException;

    /**
     * 修改个人主页背景
     * @param uid 用户 uid
     * @param userId 用户 id
     * @param background 个人主页背景文件
     * @return 返回修改过程中的提示
     * @throws IOException 保存图片可能失败
     */
    Result<?> updateHomepageBackground(Integer uid, String userId, MultipartFile background) throws IOException;
}
