package com.andy.recruitment.dao.organization.entity;

import com.andy.spring.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 机构信息
 *
 * @author 庞先海 2020-01-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class OrganizationDO extends BaseDO {

    private static final long serialVersionUID = 8246081386706563431L;
    /**
     * 机构名称
     */
    private String name;

    /**
     * 省ID
     */
    private Long provinceId;

    /**
     * 市ID
     */
    private Long cityId;

    /**
     * 区ID
     */
    private Long districtId;
}
