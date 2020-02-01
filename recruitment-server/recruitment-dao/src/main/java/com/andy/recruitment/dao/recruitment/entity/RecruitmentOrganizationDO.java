package com.andy.recruitment.dao.recruitment.entity;

import com.soyoung.base.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 招募项目的研究机构
 *
 * @author 庞先海 2020-01-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RecruitmentOrganizationDO extends BaseDO {

    private static final long serialVersionUID = - 2189079508521496839L;

    /**
     * 招募ID
     */
    private Long recruitmentId;
    /**
     * 机构ID
     */
    private Long organizationId;
    /**
     * 科室ID
     */
    private Long departmentId;
}
