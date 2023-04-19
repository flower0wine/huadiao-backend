package com.huadiao.entity.dto.accountsettings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MessageSettingsDto {
    /**
     * 消息设置
     */
    private Boolean messageRemindStatus;
    private Boolean messageReplyStatus;
    private Boolean messageLikeStatus;
}
