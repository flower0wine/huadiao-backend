package com.huadiao.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

/**
 * @author flowerwine
 * @date 2024 年 09 月 19 日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class FollowFanTest {

    @Autowired
    private FollowFanMapper followFanMapper;

    @Test
    public void selectUserFanByUid() {
        System.out.println(followFanMapper.selectUserFanByUid(13, 0, 10));
    }

    @Test
    public void selectUserFollowByUid() {
        System.out.println(followFanMapper.selectUserFollowByUid(13, -2, 0, 10));
    }

    @Test
    public void deleteRelation() {
        followFanMapper.deleteRelation(13, 16, new ArrayList<>());
    }
}
