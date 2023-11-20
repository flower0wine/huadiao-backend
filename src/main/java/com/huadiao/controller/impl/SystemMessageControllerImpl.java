package com.huadiao.controller.impl;

import com.huadiao.controller.SystemMessageController;
import com.huadiao.entity.Result;
import com.huadiao.service.SystemMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
@RestController
@RequestMapping("/message/system")
public class SystemMessageControllerImpl implements SystemMessageController {
    private SystemMessageService systemMessageService;

    @Autowired
    public SystemMessageControllerImpl(SystemMessageService systemMessageService) {
        this.systemMessageService = systemMessageService;
    }

    @Override
    @GetMapping("/get")
    public Result<?> getSystemMessage(Integer offset, Integer row) {
        return systemMessageService.getSystemMessage(offset, row);
    }
}
