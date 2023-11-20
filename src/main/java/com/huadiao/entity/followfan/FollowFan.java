package com.huadiao.entity.followfan;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 装载有关注的用户或粉丝的信息
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FollowFan {
    private Integer uid;
    private String userId;
    private String nickname;
    private String userAvatar;
    private String canvases;
    private Boolean friend;
    private Date followTime;

    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj instanceof FollowFan) {
            FollowFan followFan = (FollowFan) obj;
            return this.uid.equals(followFan.uid);
        }
        return false;
    }
}
