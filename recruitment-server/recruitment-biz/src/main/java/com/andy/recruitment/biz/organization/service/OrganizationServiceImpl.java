package com.andy.recruitment.biz.organization.service;

import com.andy.recruitment.dao.organization.dao.OrganizationDAO;
import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构服务实现
 *
 * @author 庞先海 2020-01-30
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationDAO organizationDAO;

    @Autowired
    public OrganizationServiceImpl(OrganizationDAO organizationDAO) {
        this.organizationDAO = organizationDAO;
    }

    @Override
    public void addOrganization(OrganizationDO organizationDo, String operator) {
        this.organizationDAO.addOrganization(organizationDo, operator);
    }

    @Override
    public void updateOrganization(OrganizationDO organizationDo, String operator) {
        this.organizationDAO.updateOrganization(organizationDo, operator);
    }

    @Override
    public PageResult<OrganizationDO> getOrganization(OrganizationQuery query, Paginator paginator) {
        return this.organizationDAO.getOrganization(query, paginator);
    }
}
