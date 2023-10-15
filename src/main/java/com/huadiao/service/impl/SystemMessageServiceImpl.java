package com.huadiao.service.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.message.system.SystemMessage;
import com.huadiao.mapper.SystemMessageMapper;
import com.huadiao.service.AbstractMessageService;
import com.huadiao.service.SystemMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
@Slf4j
@Service
public class SystemMessageServiceImpl extends AbstractMessageService implements SystemMessageService {
    private SystemMessageMapper systemMessageMapper;

    @Autowired
    public SystemMessageServiceImpl(SystemMessageMapper systemMessageMapper) {
        this.systemMessageMapper = systemMessageMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> addSystemMessage(Integer adminId, String messageTitle, String messageContent, Integer form) {
        log.debug("管理员 {} 尝试添加系统消息", adminId);
        int messageId = messageJedisUtil.generateSystemMessageId();
        log.debug("管理员 {} 成功生成消息ID {}", adminId, messageId);
        systemMessageMapper.insertSystemMessage(messageId, adminId, messageTitle, messageContent, form);
        log.debug("管理员 {} 成功添加系统消息", adminId);
        Map<String, Object> map = new HashMap<>(2);
        map.put("messageId", messageId);
        return Result.ok(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteSystemMessage(Integer adminId, Integer messageId) {
        log.debug("管理员 {} 尝试删除系统消息, messageId: {}", adminId, messageId);
        // 鉴权
        if(false) {
            systemMessageMapper.deleteSystemMessage(messageId);
        }
        log.debug("管理员 {} 成功删除系统消息, messageId: {}", adminId, messageId);
        return Result.ok(null);
    }

    @Override
    public Result<?> getSystemMessage(Integer offset, Integer row) {
        log.debug("尝试获取系统消息, offset: {}, row: {}", offset, row);
        Result<?> result = checkOffsetAndRow(offset, row, (o, r) -> {
            List<SystemMessage> systemMessage = messageJedisUtil.getSystemMessage(o, r);
            if(systemMessage.isEmpty()) {
                return Result.notExist();
            }
            return Result.ok(systemMessage);
        });
        if(result.succeed()) {
            log.debug("成功获取系统消息, offset: {}, row: {}", offset, row);
        }
        return result;
    }
}
