package com.huadiao.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author flowerwine
 * @date 2024 年 09 月 17 日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class NoteTest {

    @Autowired
    private NoteMapper noteMapper;

    @Test
    public void selectNoteByUidAndNoteId() {
        System.out.println(noteMapper.selectNoteByUidAndNoteId(22, 2));
    }
}
