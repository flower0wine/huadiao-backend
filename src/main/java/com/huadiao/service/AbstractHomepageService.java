package com.huadiao.service;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
public abstract class AbstractHomepageService extends AbstractService implements HomepageService {

    @Value("${homepage.userAvatarRealPath}")
    protected String userAvatarRealPath;

    @Value("${homepage.homepageBackgroundRealPath}")
    protected String homepageBackgroundRealPath;

    @Value("${homepage.userAvatarMaxSize}")
    protected long userAvatarMaxSize;

    @Value("${homepage.backgroundMaxSize}")
    protected long backgroundMaxSize;

}
