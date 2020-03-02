package com.andy.recruitment.dao.organization.mapper;

import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentQuery;
import com.andy.spring.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 机构科室 mapper 接口
 *
 * @author 庞先海 2020-01-31
 */
@Repository
public interface OrganizationDepartmentMapper {

    /**
     * 科室查询
     *
     * @param query 查询参数
     * @return 科室信息
     */
    List<OrganizationDepartmentDO> select(OrganizationDepartmentQuery query);

    /**
     * 科室分页查询
     *
     * @param query 查询参数
     * @param page  分页参数
     * @return 科室信息
     */
    List<OrganizationDepartmentDO> select(OrganizationDepartmentQuery query, Page page);

    /**
     * 添加科室信息
     *
     * @param organizationDepartmentDo 科室信息
     * @return 添加数量
     */
    int insert(OrganizationDepartmentDO organizationDepartmentDo);

    /**
     * 更新科室信息
     *
     * @param organizationDepartmentDo 科室信息
     * @return 更新数量
     */
    int update(OrganizationDepartmentDO organizationDepartmentDo);
}