package com.huadiao.service.forum;

import com.huadiao.service.AbstractService;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author flowerwine
 * @date 2023 年 12 月 24 日
 */
public abstract class AbstractForumService extends AbstractService implements ForumService {

    @Value("${huadiaoForum.randomNoteLength}")
    protected int randomNoteLength;

    @Value("${huadiaoForum.noteRankMaxLength}")
    protected int noteRankMaxLength;

    @Value("${huadiaoForum.noteRankDefaultRow}")
    protected int noteRankDefaultRow;
}
