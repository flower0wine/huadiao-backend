package com.huadiao.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.huadiao.entity.dto.note.SelfNoteDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author flowerwine
 * 笔记控制器接口
 */

public interface NoteController extends Controller {

    /**
     * 发布笔记
     * @param session session 对象
     * @param map 请求体
     * @return 返回是否发布成功
     */
    String publishNote(HttpSession session, Map<String, String> map);

    /**
     * 删除笔记
     * @param session session 对象
     * @param noteId 笔记唯一标识
     * @return 返回删除成功与否提示
     */
    String deleteNote(HttpSession session, Integer noteId);

    /**
     * 修改笔记
     * @param session session 对象
     * @param noteId 笔记唯一标识
     * @param map 请求体
     * @return 返回修改成功与否
     */
    String modifyNote(HttpSession session, Integer noteId, Map<String, String> map);

    /**
     * 获取单个笔记
     * @param request 请求对象
     * @param response 响应对象
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记唯一标识
     * @return 返回 map 集合, 包含查找到的笔记, 是否是本人
     * @throws Exception 可能抛出异常
     */
    Map<String, Object> getSingleNote(HttpServletRequest request, HttpServletResponse response, HttpSession session, Integer uid, Integer noteId) throws Exception;

    /**
     * 获取单个笔记, 在编辑笔记时使用该接口
     * @param session session 对象
     * @param noteId 笔记 id
     * @return 返回用户自己的笔记
     */
    SelfNoteDto getSingleNote(HttpSession session, Integer noteId);

    /**
     * 获取所有笔记
     * @param session session 对象
     * @param authorUid 作者 uid
     * @return 返回所有笔记
     */
    Map<String, Object> getAllNotes(HttpSession session, Integer authorUid);
}
