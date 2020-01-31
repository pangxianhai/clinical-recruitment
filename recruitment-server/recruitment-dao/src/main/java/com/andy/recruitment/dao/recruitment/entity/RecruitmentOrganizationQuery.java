package com.andy.recruitment.dao.recruitment.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 招募项目的研究机构查询参数
 *
 * @author 庞先海 2020-01-31
 */
@Data
public class RecruitmentOrganizationQuery implements Serializable {

    private static final long serialVersionUID = 6096110044257437868L;

    /**
     * 招募ID
     */
    private Long recruitmentId;
    /**
     * 机构ID
     */
    private Long organizationId;
}
