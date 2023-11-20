package com.huadiao.entity.history;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 09 月 18 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class AnimeHistory {
    private Integer uid;
    private String userId;
    private String nickname;
    private String userAvatar;
    private String animeCover;
    private String animeTitle;
    private Date visitTime;

    @Override
    public int hashCode() {
        return this.getUid().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        } else if(obj instanceof AnimeHistory) {
            AnimeHistory animeHistory = (AnimeHistory) obj;
            return this.getUid().equals(animeHistory.getUid());
        }
        return false;
    }
}
