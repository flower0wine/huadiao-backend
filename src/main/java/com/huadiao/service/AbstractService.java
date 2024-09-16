package com.huadiao.service;

import com.huadiao.elasticsearch.repository.NoteHistoryRepository;
import com.huadiao.elasticsearch.repository.NoteRepository;
import com.huadiao.elasticsearch.repository.UserRepository;
import com.huadiao.entity.Result;
import com.huadiao.redis.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Slf4j
public abstract class AbstractService implements Service {

    @Autowired
    protected UserBaseJedisUtil userBaseJedisUtil;

    @Autowired
    protected UserInfoJedisUtil userInfoJedisUtil;

    @Autowired
    protected UserSettingJedisUtil userSettingJedisUtil;

    @Autowired
    protected StarJedisUtil starJedisUtil;

    @Autowired
    protected FollowFanJedisUtil followFanJedisUtil;

    @Autowired
    protected HuadiaoHouseJedisUtil huadiaoHouseJedisUtil;

    @Autowired
    protected NoteJedisUtil noteJedisUtil;

    @Autowired
    protected MessageJedisUtil messageJedisUtil;

    @Autowired(required = false)
    protected UserRepository userRepository;

    @Autowired(required = false)
    protected NoteRepository noteRepository;

    @Autowired(required = false)
    protected NoteHistoryRepository noteHistoryRepository;

    @Autowired
    protected ForumJedisUtil forumJedisUtil;

    @Autowired
    protected AnimeJedisUtil animeJedisUtil;

    @Value("${huadiao.defaultRow}")
    protected int defaultRow;

    @Value("${userInfo.defaultCanvases}")
    protected String defaultCanvases;

    @Value("${message.defaultWhisperMessage}")
    protected String defaultWhisperMessage;

    @Value("${jedis.defaultRedisInitialId}")
    protected int defaultRedisInitialId;

    /**
     * 匹配设置字段, 要求全部为英文字母
     */
    public static Pattern pattern = Pattern.compile("^\\w+$");

    public String imageHost = "/images/";

    /**
     * 字段格式化, 例如: exampleExample --> example_example
     *
     * @param field 字段
     * @return 转化后的字段
     */
    protected String fieldFormat(String field) {
        String settingReplaceRegex = "([a-z])([A-Z])";
        String replace = "$1_$2";
        return field.replaceAll(settingReplaceRegex, replace).toLowerCase();
    }

    /**
     * 检查 offset 和 row 参数
     * @param function 符合规范后执行的函数
     * @return Result 对象
     */
    protected Result<?> checkOffsetAndRow(Integer offset, Integer row, BiFunction<Integer, Integer, Result<?>> function) {
        if (offset == null || offset < 0 || row == null || row < 0) {
            return Result.errorParam();
        }
        if (row > defaultRow) {
            row = defaultRow;
        }
        return function.apply(offset, row);
    }

    protected Result<?> checkPageAndSize(Integer page, Integer size, BiFunction<Integer, Integer, Result<?>> function) {
        if (page == null || page <= 0 || size == null || size < 0) {
            return Result.errorParam();
        }
        if (size > defaultRow) {
            size = defaultRow;
        }

        page = (page - 1) * size;

        // page 虽然大于 0，但 page == Integer.MAX_VALUE 时，(page - 1) * size 会溢出
        if (page < 0) {
            page = 0;
        }
        return function.apply(page, size);
    }

    /**
     * 检查 list 是否为 null, 或者为空集合
     * @param ll 多参集合
     * @return Result 对象
     */
    protected Result<?> isEmpty(List<?> ...ll) {
        boolean notExist = true;
        for(List<?> list : ll) {
            notExist &= list.isEmpty();
        }
        if(notExist) {
            return Result.emptyData();
        }
        if(ll.length == 1) {
            return Result.ok(ll[0]);
        }
        return Result.ok(null);
    }

}
