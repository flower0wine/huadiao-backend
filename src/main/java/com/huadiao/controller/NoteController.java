package com.huadiao.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.huadiao.entity.Result;
import com.huadiao.entity.dto.note.NoteCommentDto;
import com.huadiao.entity.dto.note.SelfNoteDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
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
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记唯一标识
     * @return 返回 map 集合, 包含查找到的笔记, 是否是本人
     */
    Result<?> getSingleNote(HttpSession session, Integer uid, Integer noteId);

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
    Result<?> getAllNotes(HttpSession session, Integer authorUid);

    /**
     * 获取笔记评论
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @param offset 偏移量
     * @param row 行数
     * @return 返回评论
     */
    Result<List<NoteCommentDto>> getNoteComment(HttpSession session, Integer uid, Integer noteId, Integer offset, Integer row);

    /**
     * 添加笔记评论
     * @param session session 对象
     * @param uid 作者 uid
     * @param noteId 笔记 id
     * @param map 请求体
     * @return 返回增加过程中的提示
     */
    Result<Map<String, Object>> addNoteComment(HttpSession session, Integer uid, Integer noteId, Map<String, String> map);
}
