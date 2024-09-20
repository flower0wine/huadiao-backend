package com.huadiao.entity.req.follow;

import lombok.Data;

import java.util.List;

/**
 * @author flowerwine
 * @date 2024 年 09 月 19 日
 */
@Data
public class TransferFollowerParams {
    private Integer srcGroupId;
    private Integer destGroupId;
    private List<Integer> uidList;
}
