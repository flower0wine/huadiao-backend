package com.huadiao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 花凋首页诗句
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Poem {
    private String poet;
    private String poem;
    /**
     * 朝代
     */
    private String dynasty;
    /**
     * 生卒年
     */
    private String birthAndDeath;
}
