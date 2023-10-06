package com.huadiao.service;

import com.huadiao.entity.HuadiaoHouseInfo;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author flowerwine
 * @date 2023 年 09 月 12 日
 */
public abstract class AbstractHuadiaoHouseService extends AbstractService implements HuadiaoHouseService {

    @Value("${huadiaoHouse.imageRealPath}")
    protected String imageRealPath;

    @Value("${huadiaoHouse.animeTitleMinLength}")
    protected int animeTitleMinLength;

    @Value("${huadiaoHouse.animeTitleMaxLength}")
    protected int animeTitleMaxLength;

    @Value("${huadiaoHouse.animeCoverMaxSize}")
    protected int animeCoverMaxSize;

    @Value("${huadiaoHouse.backgroundPictureMaxSize}")
    protected int backgroundPictureMaxSize;
}
