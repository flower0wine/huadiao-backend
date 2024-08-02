package com.huadiao.entity.message.whisper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 10 月 10 日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WhisperMessage {
    private Integer sendUid;
    private Integer receiveUid;
    private Integer messageType;
    private String messageContent;
    private Integer messageId;
    private Date sendTime;
}
