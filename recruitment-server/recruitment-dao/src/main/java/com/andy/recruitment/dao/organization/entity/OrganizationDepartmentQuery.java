package com.andy.recruitment.dao.organization.entity;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 科室查询参数
 *
 * @author 庞先海 2020-01-31
 */
@Data
public class OrganizationDepartmentQuery implements Serializable {

    private static final long serialVersionUID = - 3006009164033875171L;

    /**
     * 机构ID
     */
    private Long organizationId;

    /**
     * 科室名称模糊查询
     */
    private String nameLike;

    /**
     * 科室ID列表
     */
    private List<Long> departmentIdList;
}
