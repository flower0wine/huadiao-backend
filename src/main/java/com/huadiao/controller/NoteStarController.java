package com.huadiao.controller;

import javax.servlet.http.HttpSession;

/**
 * 笔记收藏控制器接口
 * @author flowerwine
 */
public interface NoteStarController extends Controller {

    /**
     * 新增笔记收藏
     * @param session session 对象
     * @param uid 用户 uid
     * @param noteId 笔记 id
     * @return 返回新增笔记错误或者成功提示
     */
    String addNewNoteStar(HttpSession session, Integer uid, Integer noteId);
}
