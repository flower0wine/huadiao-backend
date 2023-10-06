package com.huadiao.controller;

import com.huadiao.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * 用户个人主页控制器接口
 * @author flowerwine
 */
public interface HomepageController extends Controller {

    /**
     * 根据 uid 访问个人主页
     * @param session session 对象
     * @param viewedUid 被访问的人的 uid
     * @return 返回个人主页信息
     */
    Result<?> getHomepageInfo(HttpSession session, Integer viewedUid);

    /**
     * 修改用户头像
     * @param session session 头像
     * @param userAvatar 头像文件
     * @return 返回修改过程过程中的提示
     * @throws IOException 保存图片可能失败
     */
    Result<?> updateUserAvatar(HttpSession session, MultipartFile userAvatar) throws IOException;

    /**
     * 修改个人主页背景
     * @param session session 头像
     * @param background 个人主页背景文件
     * @return 返回修改过程过程中的提示
     * @throws IOException 保存图片可能失败
     */
    Result<?> updateHomepageBackground(HttpSession session, MultipartFile background) throws IOException;
}
