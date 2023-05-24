package com.huadiao.service;

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
    public static int MIN_NOTE_TITLE_LENGTH = 5;

    /**
     * 笔记标题最大长度
     */
    public static int MAX_NOTE_TITLE_LENGTH = 100;

    /**
     * 不符合规范的笔记标题
     */
    public static String WRONG_NOTE_TITLE = "wrongNoteTitle";

    /**
     * 笔记内容为空
     */
    public static String NULL_NOTE_CONTENT = "nullNoteContent";

    /**
     * 新增笔记成功
     */
    public static String ADD_NEW_NOTE_SUCCEED = "addNewNoteSucceed";

    /**
     * 删除笔记成功
     */
    public static String DELETE_NOTE_SUCCEED = "deleteNoteSucceed";

    /**
     * 修改笔记成功
     */
    public static String MODIFY_NOTE_SUCCEED = "modifyNoteSucceed";

}
