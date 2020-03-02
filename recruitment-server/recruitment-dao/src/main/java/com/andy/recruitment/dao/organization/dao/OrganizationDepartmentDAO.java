package com.andy.recruitment.dao.organization.dao;

import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.List;

/**
 * 机构科室 dao 接口
 *
 * @author 庞先海 2020-01-31
 */
public interface OrganizationDepartmentDAO {

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

    /**
     * 通过机构id获取所有科室信息
     *
     * @param organizationId 机构id
     * @return 科室信息
     */
    List<OrganizationDepartmentDO> getOrganizationDepartmentByOrganization(Long organizationId);

    /**
     * 通过科室id批量查询科室信息
     *
     * @param departmentIdList 科室id列表
     * @return 科室信息
     */
    List<OrganizationDepartmentDO> getOrganizationDepartment(List<Long> departmentIdList);

    /**
     * 通过科室id查询科室信息
     *
     * @param departmentId 科室id
     * @return 科室信息
     */
    OrganizationDepartmentDO getOrganizationDepartmentById(Long departmentId);
}
