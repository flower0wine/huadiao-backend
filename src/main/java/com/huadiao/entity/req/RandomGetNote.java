package com.huadiao.entity.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author flowerwine
 * @date 2024 年 09 月 16 日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RandomGetNote extends Pagination {
    private Integer tagId;
}
