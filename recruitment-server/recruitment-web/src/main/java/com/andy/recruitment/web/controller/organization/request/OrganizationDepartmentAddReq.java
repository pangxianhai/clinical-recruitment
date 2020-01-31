package com.andy.recruitment.web.controller.organization.request;

import java.io.Serializable;
import lombok.Data;

/**
 * 机构科室添加参数
 *
 * @author 庞先海 2020-01-31
 */
@Data
public class OrganizationDepartmentAddReq implements Serializable {

    private static final long serialVersionUID = - 4505137003248670053L;

    /**
     * 机构ID
     */
    private Long organizationId;

    /**
     * 科室名称
     */
    private String name;
}
