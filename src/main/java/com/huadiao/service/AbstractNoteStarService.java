package com.huadiao.service;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public abstract class AbstractNoteStarService extends AbstractService implements NoteStarService {

    /**
     * 最大收藏数量
     */
    public int MAX_NOTE_STAR_VALUE = 600;

    /**
     * 达到笔记收藏最大数量
     */
    public String ATTACH_MAX_NOTE_STAR = "attachMaxNoteStar";

    /**
     * 笔记不存在, 可能是用户不存在, 也可能是用户笔记不存在
     */
    public String NOTE_NOT_EXIST = "noteNotExist";

    /**
     * 新增笔记收藏成功
     */
    public String ADD_NOTE_STAR_SUCCEED = "addNoteStarSucceed";

    /**
     * 删除笔记收藏成功
     */
    public String DELETE_NOTE_STAR_SUCCEED = "deleteNoteStarSucceed";

}
