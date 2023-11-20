package com.huadiao.service;

import org.springframework.beans.factory.annotation.Value;

import java.util.regex.Pattern;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 业务层: 笔记抽象实现类
 */
public abstract class AbstractNoteService extends AbstractService implements NoteService {
    /**
     * 笔记标题最小长度
     */
    public int MIN_NOTE_TITLE_LENGTH = 5;

    /**
     * 笔记标题最大长度
     */
    public int MAX_NOTE_TITLE_LENGTH = 100;

    /**
     * 单次最大获取行数
     */
    public int MAX_ROW = 40;

    /**
     * 评论 id 的长度
     */
    public int COMMENT_LENGTH = 20;

    @Value("${note.noteSummaryMinLength}")
    protected int noteSummaryMinLength;

    @Value("${note.noteSummaryMaxLength}")
    protected int noteSummaryMaxLength;

    /**
     * 未分配的评论 id, 在笔记评论表中父评论 sub_comment_id 为 null, 在笔记评论喜欢表和不喜欢表中则为 0
     */
    public Long UNDISTRIBUTED_COMMENT_ID = null;

    /**
     * 正则表达式, 评论 id
     */
    public Pattern commentIdPattern = Pattern.compile("[\\d\\w]+");

    /**
     * 不符合规范的笔记标题
     */
    public String WRONG_NOTE_TITLE = "wrongNoteTitle";

    /**
     * 笔记内容为空
     */
    public String NULL_NOTE_CONTENT = "nullNoteContent";

    /**
     * 新增笔记成功
     */
    public String ADD_NEW_NOTE_SUCCEED = "addNewNoteSucceed";

    /**
     * 删除笔记成功
     */
    public String DELETE_NOTE_SUCCEED = "deleteNoteSucceed";

    /**
     * 修改笔记成功
     */
    public String MODIFY_NOTE_SUCCEED = "modifyNoteSucceed";

    /**
     * 添加笔记评论成功
     */
    public String ADD_NOTE_COMMENT_SUCCEED = "addNoteCommentSucceed";

}
