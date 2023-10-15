package com.huadiao.entity.message.system;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * @author flowerwine
 * @date 2023 年 10 月 07 日
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class SystemMessage {
    private Integer messageId;
    private String messageTitle;
    private String messageContent;
    private Date sendTime;
    private Integer form;
}
