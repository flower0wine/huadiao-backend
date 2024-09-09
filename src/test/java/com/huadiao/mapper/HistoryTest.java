package com.huadiao.mapper;

import com.huadiao.entity.NoteHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author flowerwine
 * @date 2024 年 09 月 09 日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-ioc.xml"})
public class HistoryTest {

    @Autowired
    HistoryMapper historyMapper;

    @Test
    public void selectNoteHistoryByUidTest() {
        List<NoteHistory> noteHistories = historyMapper.selectNoteHistoryByUid(13, null, 10, 0);
        System.out.println("测试结果 noteHistories 为 " + noteHistories + " 长度为 " + noteHistories.size());

        noteHistories = historyMapper.selectNoteHistoryByUid(13, "人各有志", 10, 0);
        System.out.println("测试结果 noteHistories 为 " + noteHistories + " 长度为 " + noteHistories.size());
    }
}
