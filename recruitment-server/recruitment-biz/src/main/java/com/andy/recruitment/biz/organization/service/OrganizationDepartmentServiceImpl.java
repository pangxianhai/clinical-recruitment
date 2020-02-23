package com.andy.recruitment.biz.organization.service;

import com.andy.recruitment.dao.organization.dao.OrganizationDepartmentDAO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构科室服务接口实现
 *
 * @author 庞先海 2020-01-31
 */
@Service
public class OrganizationDepartmentServiceImpl implements OrganizationDepartmentService {

    private final OrganizationDepartmentDAO organizationDepartmentDAO;

    @Autowired
    public OrganizationDepartmentServiceImpl(OrganizationDepartmentDAO organizationDepartmentDAO) {
        this.organizationDepartmentDAO = organizationDepartmentDAO;
    }

    @Override
    public void addOrganizationDepartment(OrganizationDepartmentDO organizationDepartmentDo, String operator) {
        this.organizationDepartmentDAO.addOrganizationDepartment(organizationDepartmentDo, operator);
    }

    @Override
    public void updateOrganizationDepartment(OrganizationDepartmentDO organizationDepartmentDo, String operator) {
        this.organizationDepartmentDAO.updateOrganizationDepartment(organizationDepartmentDo, operator);
    }

    @Override
    public PageResult<OrganizationDepartmentDO> getOrganizationDepartment(OrganizationDepartmentQuery query,
        Paginator paginator) {
        return this.organizationDepartmentDAO.getOrganizationDepartment(query, paginator);
    }

    @Override
    public List<OrganizationDepartmentDO> getOrganizationDepartment(List<Long> departmentIdList) {
        return this.organizationDepartmentDAO.getOrganizationDepartment(departmentIdList);
    }

    @Override
    public OrganizationDepartmentDO getOrganizationDepartmentById(Long departmentId) {
        return this.organizationDepartmentDAO.getOrganizationDepartmentById(departmentId);
    }
}
