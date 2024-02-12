package com.huadiao.entity.dto.user;

import com.huadiao.service.FollowFanService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户简要信息
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserAbstractDto {
    /**
     * 是否登录
     */
    private Boolean login;

    private Integer uid;
    private String userId;
    private String nickname;
    private String userAvatar;
    /**
     * 关注和粉丝数量
     */
    private Integer follow;
    private Integer fan;

    /**
     * 加载用户信息
     * @param userShareDto 可共享用户信息
     * @param relation 用户关注与粉丝数量
     * @return 用户简略信息
     */
    public static UserAbstractDto loadUserAbstractInfo(UserShareDto userShareDto, List<Integer> relation) {
        UserAbstractDto userAbstractDto = new UserAbstractDto();
        userAbstractDto.follow = relation.get(FollowFanService.FOLLOW_INDEX);
        userAbstractDto.fan = relation.get(FollowFanService.FAN_INDEX);
        userAbstractDto.uid = userShareDto.getUid();
        userAbstractDto.userId = userShareDto.getUserId();
        userAbstractDto.nickname = userShareDto.getNickname();
        userAbstractDto.userAvatar = userShareDto.getUserAvatar();
        return userAbstractDto;
    }
}
