package com.andy.recruitment.dao.organization.dao;

import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;
import java.util.List;

/**
 * 机构信息 dao 接口
 *
 * @author 庞先海 2020-01-27
 */
public interface OrganizationDAO {

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
}
