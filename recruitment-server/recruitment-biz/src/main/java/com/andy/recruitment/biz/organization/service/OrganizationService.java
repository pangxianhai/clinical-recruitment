package com.andy.recruitment.biz.organization.service;

import com.andy.recruitment.api.organization.response.OrganizationRes;
import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.List;
import java.util.Map;

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

    /**
     * 通过机构ID列表批量查询机构信息
     *
     * @param organizationIdList 机构ID列表
     * @return 机构信息
     */
    List<OrganizationDO> getOrganization(List<Long> organizationIdList);

    /**
     * 通过机构ID列表批量查询机构信息
     *
     * @param organizationIdList 机构ID列表
     * @return 机构信息
     */
    Map<Long, OrganizationRes> getOrganizationRes(List<Long> organizationIdList);

    /**
     * 通过机构ID查询机构信息
     *
     * @param organizationId 机构ID
     * @return 机构信息
     */
    OrganizationDO getOrganizationById(Long organizationId);
}
