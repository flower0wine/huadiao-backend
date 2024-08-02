package com.huadiao.controller.impl;

import com.huadiao.controller.AbstractController;
import com.huadiao.controller.SystemMessageController;
import com.huadiao.entity.Result;
import com.huadiao.service.SystemMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
@RestController
@RequestMapping("/message/system")
public class SystemMessageControllerImpl extends AbstractController implements SystemMessageController {
    private SystemMessageService systemMessageService;

    @Autowired
    public SystemMessageControllerImpl(SystemMessageService systemMessageService) {
        this.systemMessageService = systemMessageService;
    }

    @Override
    @GetMapping("/get")
    public Result<?> getSystemMessage(HttpSession session, Integer offset, Integer row) {
        Integer uid = (Integer) session.getAttribute(uidKey);
        return systemMessageService.getSystemMessage(uid, offset, row);
    }
}
