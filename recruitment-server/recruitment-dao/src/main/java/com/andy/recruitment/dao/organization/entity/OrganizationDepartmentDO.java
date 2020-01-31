package com.andy.recruitment.dao.organization.entity;

import com.soyoung.base.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 机构科室信息
 *
 * @author 庞先海 2020-01-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class OrganizationDepartmentDO extends BaseDO {

    private static final long serialVersionUID = 1425012185188584306L;

    /**
     * 机构ID
     */
    private Long organizationId;

    /**
     * 科室名称
     */
    private String name;
}
