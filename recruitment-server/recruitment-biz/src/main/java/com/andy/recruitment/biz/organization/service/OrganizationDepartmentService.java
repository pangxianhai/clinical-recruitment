package com.andy.recruitment.biz.organization.service;

import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;

/**
 * 机构科室服务接口
 *
 * @author 庞先海 2020-01-31
 */
public interface OrganizationDepartmentService {

    /**
     * 添加机构科室
     *
     * @param organizationDepartmentDo 科室信息
     * @param operator                 操作人
     */
    void addOrganizationDepartment(OrganizationDepartmentDO organizationDepartmentDo, String operator);

    /**
     * 更新科室信息
     *
     * @param organizationDepartmentDo 科室信息
     * @param operator                 操作人
     */
    void updateOrganizationDepartment(OrganizationDepartmentDO organizationDepartmentDo, String operator);

    /**
     * 分页查询机构科室信息
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 科室信息
     */
    PageResult<OrganizationDepartmentDO> getOrganizationDepartment(OrganizationDepartmentQuery query,
        Paginator paginator);
}
