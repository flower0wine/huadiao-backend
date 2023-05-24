package com.huadiao.controller;

import javax.servlet.http.HttpSession;
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
    Map<String, Object> getHomepageInfo(HttpSession session, Integer viewedUid);
}
