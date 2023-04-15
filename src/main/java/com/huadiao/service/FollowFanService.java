package com.huadiao.service;

/**
 * 业务层: 关注与粉丝处理接口
 * @author flowerwine
 */
public interface FollowFanService {
    /**
     * 从数据库获取关注数量时, list 集合索引
     */
    int FOLLOW_INDEX = 0;
    /**
     * 从数据库获取粉丝数量时, list 集合索引
     */
    int FAN_INDEX = 1;
}
