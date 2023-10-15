package com.huadiao.entity.message.setting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class MessageSetting {
    private Boolean messageRemindStatus;
    private Boolean messageReplyStatus;
    private Boolean messageLikeStatus;
    private Boolean messageFoldStatus;
}
