package com.huadiao.service;

import com.huadiao.entity.FollowGroup;
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
     * 关注分组最多可建立的数量
     */
    @Value("${followFan.followGroupMaxCount}")
    protected int followGroupMaxCount;

    @Value("${followFan.followGroupNameMaxLength}")
    protected int followGroupNameMaxLength;

    @Value("${followFan.followGroupNameMinLength}")
    protected int followGroupNameMinLength;

    @Value("${followFan.defaultAllGroupId}")
    protected int defaultAllGroupId;

    @Value("${followFan.defaultFollowGroupId}")
    protected int defaultFollowGroupId;

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

    protected FollowGroup getDefaultFollowGroup() {
        FollowGroup followGroup = new FollowGroup();
        followGroup.setGroupId(defaultFollowGroupId);
        followGroup.setGroupName("默认分组");
        return followGroup;
    }

}
