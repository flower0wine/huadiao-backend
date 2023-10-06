package com.huadiao.service;

import com.huadiao.entity.NoteStarCatalogue;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author flowerwine
 * @date 2023 年 09 月 24 日
 */
public abstract class AbstractStarService extends AbstractService implements StarService {

    @Value("${star.noteStarGroupNameMaxLength}")
    protected int noteStarGroupNameMaxLength;

    @Value("${star.noteStarGroupNameMinLength}")
    protected int noteStarGroupNameMinLength;

    @Value("${star.noteStarGroupDescriptionMinLength}")
    protected int noteStarGroupDescriptionMinLength;

    @Value("${star.noteStarGroupDescriptionMaxLength}")
    protected int noteStarGroupDescriptionMaxLength;

    @Value("${star.defaultNoteStarGroupId}")
    protected int defaultNoteStarGroupId;

    @Value("${star.defaultNoteStarGroupName}")
    protected String defaultNoteStarGroupName;

    @Value("${star.defaultNoteStarGroupDescription}")
    protected String defaultNoteStarGroupDescription;

    @Value("${star.defaultAnimeStarGroupId}")
    protected int defaultAnimeStarGroupId;

    /**
     * 获取默认分组的信息
     * @return 返回默认分组
     */
    protected NoteStarCatalogue getDefaultNoteStarCatalogue(Integer count) {
        NoteStarCatalogue noteStarCatalogue = new NoteStarCatalogue();
        noteStarCatalogue.setGroupId(defaultNoteStarGroupId);
        noteStarCatalogue.setCount(count);
        noteStarCatalogue.setOpen(true);
        noteStarCatalogue.setGroupName(defaultNoteStarGroupName);
        noteStarCatalogue.setGroupDescription(defaultNoteStarGroupDescription);
        return noteStarCatalogue;
    }

}
