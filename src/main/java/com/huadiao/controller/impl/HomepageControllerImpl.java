package com.huadiao.controller.impl;

import com.huadiao.controller.HomepageController;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户个人主页控制器实现类
 */
@Controller
public class HomepageControllerImpl implements HomepageController {


    @Override
    public Map<String, Object> getHomepageInfo(HttpSession session, Integer viewedUid) {
        Integer uid = (Integer) session.getAttribute("uid");
        String userId = (String) session.getAttribute("userId");

        return null;
    }
}
