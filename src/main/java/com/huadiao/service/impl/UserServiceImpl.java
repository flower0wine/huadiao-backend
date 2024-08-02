package com.huadiao.service.impl;

import com.huadiao.entity.Result;
import com.huadiao.entity.dto.user.*;
import com.huadiao.mapper.*;
import com.huadiao.service.AbstractUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.Cookie;
import java.util.List;

/**
 * 业务层: 与账号相关的操作的实现类
 * @author flowerwine
 * @version 1.1
 */
@Slf4j
@Service
public class UserServiceImpl extends AbstractUserService {
    private UserMapper userMapper;
    private FollowFanMapper followFanMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, FollowFanMapper followFanMapper) {
        this.userMapper = userMapper;
        this.followFanMapper = followFanMapper;
    }

    @Override
    public Result<?> getHuadiaoHeaderUserInfo(Integer uid, String userId) {
        UserShareDto userShareDto = userMapper.selectUserShareDtoByUid(uid);
        List<Integer> followFans = followFanMapper.countFollowAndFansByUid(uid);
        UserAbstractDto userAbstractDto = UserAbstractDto.loadUserAbstractInfo(userShareDto, followFans);
        // 用户已登录
        userAbstractDto.setLogin(true);
        log.debug("uid, userId 为 {}, {} 的用户已登录, 并获取了导航栏信息", uid, userId);
        return Result.ok(userAbstractDto);
    }


    @Override
    public void logoutHuadiao(Cookie cookie, Integer uid, String userId, String nickname) {
        log.info("uid 为 {}, nickname 为 {}, userId 为 {} 的用户尝试退出登录", uid, nickname, userId);
        // 删除 cookie
        cookie.setMaxAge(0);
        log.info("uid 为 {}, nickname 为 {}, userId 为 {} 的用户退出登录成功", uid, nickname, userId);
    }

}
