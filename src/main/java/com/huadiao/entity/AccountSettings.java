package com.huadiao.entity;

import com.huadiao.entity.dto.accountsettings.MessageSettingsDto;
import com.huadiao.entity.dto.accountsettings.PublicInfoSettingsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 用户信息
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccountSettings {

    /**
     * 是否公开
     */
    private Boolean publicStarStatus;
    private Boolean publicBornStatus;
    private Boolean publicFanjuStatus;
    private Boolean publicNoteStatus;
    private Boolean publicSchoolStatus;
    private Boolean publicFollowStatus;
    private Boolean publicFanStatus;
    private Boolean publicCanvasesStatus;
    private Boolean publicHomepageStatus;

    /**
     * 消息设置
     */
    private Boolean messageRemindStatus;
    private Boolean messageReplyStatus;
    private Boolean messageLikeStatus;

}
