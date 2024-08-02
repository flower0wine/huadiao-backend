package com.huadiao.service;

import org.springframework.beans.factory.annotation.Value;

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

    @Value("${note.noteSummaryMinLength}")
    protected int noteSummaryMinLength;

    @Value("${note.noteSummaryMaxLength}")
    protected int noteSummaryMaxLength;

}
