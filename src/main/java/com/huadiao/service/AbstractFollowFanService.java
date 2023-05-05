package com.huadiao.service;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 业务层: 关注与粉丝处理抽象实现类
 */
public abstract class AbstractFollowFanService implements FollowFanService {

    /**
     * 关注分组添加成功
     */
    public static String FOLLOW_GROUP_ADD_SUCCEED = "followGroupAddSucceed";

    /**
     * 最大关注名称长度
     */
    public static int MAX_FOLLOW_GROUP_NAME_LENGTH = 16;

    /**
     * 分组名称不能为空
     */
    public static String NULL_FOLLOW_GROUP_NAME = "nullFollowGroupName";

    /**
     * 关注分组名称长度最长不超过 16 字符
     */
    public static String WRONG_FOLLOW_GROUP_NAME_LENGTH = "wrongFollowGroupNameLength";

    /**
     * 错误的被访问者 uid
     */
    public static String WRONG_VIEWED_UID = "wrongViewedUid";

    /**
     * 错误的 uid, 该 uid 不存在
     */
    public static String WRONG_UID = "wrongUid";

    /**
     * 错误的关注分组 id
     */
    public static String WRONG_GROUP_ID = "wrongGroupId";

    /**
     * 建立关系成功
     */
    public static String BUILD_RELATION_SUCCEED = "buildRelationSucceed";

    /**
     * 最大关注数量
     */
    public static int MAX_FOLLOW_AMOUNT = 200;

    /**
     * 达到最大关注数量
     */
    public static String REACH_MAX_FOLLOW_AMOUNT = "reachMaxFollowAmount";

    /**
     * 关注分组最多可建立的数量
     */
    public static int MAX_FOLLOW_GROUP_AMOUNT = 20;

    /**
     * 达到关注分组最大数量
     */
    public static String REACH_MAX_FOLLOW_GROUP_AMOUNT = "reachMaxFollowGroupAmount";

}
