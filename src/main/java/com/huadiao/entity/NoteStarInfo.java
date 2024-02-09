package com.huadiao.entity;

import com.huadiao.entity.followfan.FollowFan;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 09 月 24 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class NoteStarInfo {
    private Integer uid;
    private Integer noteId;
    private Integer groupId;
    private String userId;
    private String nickname;
    private String userAvatar;
    private String noteTitle;
    private String noteContent;
    private Date starTime;
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
            return this.uid.equals(followFan.getUid());
        }
        return false;
    }
}
