package com.huadiao.entity.message.whisper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 10 月 10 日
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class WhisperMessage {
    private Integer receiveUid;
    private Integer messageType;
    private String messageContent;
    private Integer messageId;
    private Date sendTime;
}
