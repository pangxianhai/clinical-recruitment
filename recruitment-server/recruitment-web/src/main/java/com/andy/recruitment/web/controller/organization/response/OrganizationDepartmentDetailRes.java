package com.andy.recruitment.web.controller.organization.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机构科室详细信息
 *
 * @author 庞先海 2020-02-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrganizationDepartmentDetailRes extends OrganizationDepartmentRes {

    private static final long serialVersionUID = 7491334212583301894L;

    /**
     * 机构信息
     */
    private OrganizationRes organizationRes;
}
