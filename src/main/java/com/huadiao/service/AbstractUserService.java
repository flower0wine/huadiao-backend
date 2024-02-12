package com.huadiao.service;

import lombok.extern.slf4j.Slf4j;

/**
 * 这里主要聚集了用户的配置
 * @author flowerwine
 */
@Slf4j
public abstract class AbstractUserService extends AbstractService implements UserService {

    /**
     * 确认登录状态为已登录
     */
    protected static boolean USER_LOGIN_STATUS = true;


}
