package com.huadiao.entity.message.whisper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

/**
 * @author flowerwine
 * @date 2023 年 10 月 10 日
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LatestUser {
    private Integer uid;
    private String nickname;
    private String userId;
    private String avatar;
    private String latestMessage;

    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        // null instanceof Object 为 false
        if(obj instanceof LatestUser) {
            LatestUser latestUser = (LatestUser) obj;
            return latestUser.uid.equals(uid);
        }
        return false;
    }
}
