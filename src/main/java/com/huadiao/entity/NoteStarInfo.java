package com.huadiao.entity;

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
<<<<<<<< HEAD:src/main/java/com/huadiao/entity/NoteStarInfo.java
    private String noteTitle;
    private String noteContent;
    private Date starTime;
========
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
>>>>>>>> 0e6634b (花凋后端修改时间: 2023-10-06):src/main/java/com/huadiao/entity/FollowFan.java
}
