package com.andy.recruitment.web.controller.organization.response;

import java.io.Serializable;
import lombok.Data;

/**
 * 机构科室信息
 *
 * @author 庞先海 2020-01-31
 */
@Data
public class OrganizationDepartmentRes implements Serializable {

    private static final long serialVersionUID = 2570286136312970975L;

    /**
     * 科室ID
     */
    private Long departmentId;

    /**
     * 机构ID
     */
    private Long organizationId;

    /**
     * 机构名称
     */
    private String organizationName;

    /**
     * 科室名称
     */
    private String name;
}
