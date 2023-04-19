package com.huadiao.entity.dto.accountsettings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author flowerwine
 * @version 1.1
 * @projectName huadiao-user-back
 * @description 账号公开信息设置
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PublicInfoSettingsDto {
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
}
