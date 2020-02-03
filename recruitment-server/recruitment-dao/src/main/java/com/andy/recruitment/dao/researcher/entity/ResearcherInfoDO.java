package com.andy.recruitment.dao.researcher.entity;

import com.andy.recruitment.dao.researcher.constant.ResearcherStatus;
import com.soyoung.base.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 研究员信息
 *
 * @author 庞先海 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ResearcherInfoDO extends BaseDO {

    private static final long serialVersionUID = - 1171395858993872274L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 所属机构id
     */
    private Long organizationId;
    /**
     * 所属科室ID
     */
    private Long departmentId;
    /**
     * 执业机构
     */
    private String medicalInstitution;
    /**
     * 执业类别
     */
    private String medicalCategory;
    /**
     * 备注
     */
    private String remark;
    /**
     * 研究员状态
     */
    private ResearcherStatus status;
}
