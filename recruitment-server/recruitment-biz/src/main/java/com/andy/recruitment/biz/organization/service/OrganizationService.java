package com.andy.recruitment.biz.organization.service;

import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;

/**
 * 机构服务
 *
 * @author 庞先海 2020-01-30
 */
public interface OrganizationService {

    /**
     * 添加机构信息
     *
     * @param organizationDo 机构信息
     * @param operator       操作人
     */
    void addOrganization(OrganizationDO organizationDo, String operator);

    /**
     * 更新机构信息
     *
     * @param organizationDo 机构信息
     * @param operator       操作人
     */
    void updateOrganization(OrganizationDO organizationDo, String operator);

    /**
     * 分页查询机构信息
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 机构信息分页查询结果
     */
    PageResult<OrganizationDO> getOrganization(OrganizationQuery query, Paginator paginator);
}
