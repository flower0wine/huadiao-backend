package com.huadiao.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author flowerwine
 * @date 2024 年 09 月 16 日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class ForumTest {

    @Autowired
    private ForumNoteMapper forumNoteMapper;

    @Test
    public void selectForumNote() {
        System.out.println(forumNoteMapper.selectForumNote(0, 10, 1));
        System.out.println(forumNoteMapper.selectForumNote(0, 10, 2));

        System.out.println(forumNoteMapper.selectForumNote(0, 10, null));
    }
}
