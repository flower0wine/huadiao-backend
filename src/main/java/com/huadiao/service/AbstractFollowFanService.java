package com.huadiao.service;

import com.huadiao.entity.dto.followfan.BothRelationDto;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 业务层: 关注与粉丝处理抽象实现类
 */
public abstract class AbstractFollowFanService extends AbstractService implements FollowFanService {

    /**
     * 关注分组添加成功
     */
    public String FOLLOW_GROUP_ADD_SUCCEED = "followGroupAddSucceed";

    /**
     * 最大关注名称长度
     */
    @Value("${followFan.followGroupNameLength}")
    public int MAX_FOLLOW_GROUP_NAME_LENGTH = 16;

    /**
     * 分组名称不能为空
     */
    public String NULL_FOLLOW_GROUP_NAME = "nullFollowGroupName";

    /**
     * 关注分组名称长度最长不超过 16 字符
     */
    public String WRONG_FOLLOW_GROUP_NAME_LENGTH = "wrongFollowGroupNameLength";

    /**
     * 不存在的关注分组 id
     */
    public String NO_EXIST_GROUP_ID = "noExistGroupId";

    /**
     * 建立关系成功
     */
    public String BUILD_RELATION_SUCCEED = "buildRelationSucceed";

    /**
     * 最大关注数量
     */
    @Value("${followFan.maxFollowAmount}")
    public int MAX_FOLLOW_AMOUNT = 200;

    /**
     * 达到最大关注数量
     */
    public String REACH_MAX_FOLLOW_AMOUNT = "reachMaxFollowAmount";

    /**
     * 关注分组最多可建立的数量
     */
    @Value("${followFan.maxFollowGroupAmount}")
    public int MAX_FOLLOW_GROUP_AMOUNT = 20;

    /**
     * 达到关注分组最大数量
     */
    public String REACH_MAX_FOLLOW_GROUP_AMOUNT = "reachMaxFollowGroupAmount";

    /**
     * 从集合中的数据判断两人的关系
     * @param relation 装载有两人的关系的集合
     * @return 如果是互关返回 friend, 是关注返回 follow, 是粉丝返回 fan, 是陌生人返回 stranger
     */
    public static BothRelationDto judgeRelationBetweenBoth(List<Integer> relation) {
        // 判断两人的关系, 陌生人为 stranger, 朋友为 friend
        int friend = 2, stranger = 0, fan = 1;
        BothRelationDto relationDto = new BothRelationDto();
        // 我与他互粉
        if (relation.size() == friend) {
            relationDto.setFollowing(true);
            relationDto.setFollowed(true);
        }
        // 我与他无关系
        else if (relation.size() == stranger) {
            relationDto.setFollowing(false);
            relationDto.setFollowed(false);
        }
        // 我是他的关注, 即他是我的粉丝
        else if (relation.size() == 1 && relation.get(0) == fan) {
            relationDto.setFollowing(false);
            relationDto.setFollowed(true);
        }
        // 我是他的粉丝
        else {
            relationDto.setFollowing(true);
            relationDto.setFollowed(false);
        }
        return relationDto;
    }

}
