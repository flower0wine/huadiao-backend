package com.huadiao.controller.forum;

import com.huadiao.controller.Controller;
import com.huadiao.entity.Result;

import javax.servlet.http.HttpSession;

/**
 * 该类负责首页论坛的笔记, 例如获取最新或者是推荐的, 关注的人的笔记
 * @author flowerwine
 * @date 2023 年 12 月 24 日
 */
public interface ForumNoteController extends Controller {

    /**
     * 随机获取笔记
     * @param session session 对象
     * @param offset 偏移量
     * @param row 条数
     * @return 返回随机获取的笔记
     */
    Result<?> randomGetNote(HttpSession session, Integer offset, Integer row);

    /**
     * 获取笔记排行榜指定范围的笔记
     * @param session session 对象
     * @return 返回排行榜上的笔记
     */
    Result<?> getNoteRank(HttpSession session);

}
