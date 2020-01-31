package com.andy.recruitment.web.controller.organization.request;

import com.soyoung.base.base.BaseQueryReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机构科室查询参数
 *
 * @author 庞先海 2020-01-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrganizationDepartmentQueryReq extends BaseQueryReq {

    private static final long serialVersionUID = - 4509315573238000967L;

    /**
     * 机构ID
     */
    private Long organizationId;

    /**
     * 科室名称模糊查询
     */
    private String nameLike;
}
