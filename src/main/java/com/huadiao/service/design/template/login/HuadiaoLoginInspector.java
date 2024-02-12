package com.huadiao.service.design.template.login;

import com.huadiao.entity.Result;
import com.huadiao.entity.dto.user.UserBaseDto;
import com.huadiao.service.design.template.AbstractInspector;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * @author flowerwine
 * @date 2024 年 02 月 11 日
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@Component
public class HuadiaoLoginInspector extends AbstractInspector implements LoginInspector {

    private ThreadLocal<Map<String, String>> requestBodyThreadLocal = new ThreadLocal<>();;

    private ThreadLocal<Integer> uidThreadLocal = new ThreadLocal<>();

    private ThreadLocal<String> userIdThreadLocal = new ThreadLocal<>();

    @Override
    public void flushThreadLocal() {
        requestBodyThreadLocal.remove();
        uidThreadLocal.remove();
        userIdThreadLocal.remove();
    }

    @Override
    public Result<?> check() {
        Map<String, String> map = requestBodyThreadLocal.get();
        String username = map.get("username");
        String password = map.get("password");

        UserBaseDto userBaseDto = userMapper.selectUserByUsernameAndPassword(username, password);
        log.debug("用户使用用户名为 {} 和密码为 {} 的账号进行登录", username, password);
        // 不存在该用户
        if (userBaseDto == null) {
            log.debug("用户名为 {} 和密码为 {} 的账号不存在", username, password);
            return Result.notExist();
        }
        uidThreadLocal.set(userBaseDto.getUid());
        userIdThreadLocal.set(userBaseDto.getUserId());

        // 存在该用户
        log.info("用户名为 {} 的用户登录成功!", username);
        return Result.ok(null);
    }

    @Override
    public Integer getUid() {
        return uidThreadLocal.get();
    }

    @Override
    public String getUserId() {
        return userIdThreadLocal.get();
    }
}
