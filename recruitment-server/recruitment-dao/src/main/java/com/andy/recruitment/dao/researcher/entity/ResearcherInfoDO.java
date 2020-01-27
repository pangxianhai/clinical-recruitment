package com.andy.recruitment.dao.researcher.entity;

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
     * 执业机构
     */
    private String medicalInstitution;
    /**
     * 执业类别
     */
    private String medicalCategory;
}
