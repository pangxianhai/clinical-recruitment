package com.andy.recruitment.dao.organization.mapper;

import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationQuery;
import com.andy.spring.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 机构信息 Mapper 接口
 *
 * @author 庞先海 2020-01-27
 */
@Repository
public interface OrganizationInfoMapper {

    /**
     * 机构信息分页查询
     *
     * @param query 查询参数
     * @param page  分页参数
     * @return 机构信息列表
     */
    List<OrganizationDO> select(OrganizationQuery query, Page page);

    /**
     * 机构信息查询
     *
     * @param query 查询参数
     * @return 机构信息列表
     */
    List<OrganizationDO> select(OrganizationQuery query);

    /**
     * 插入机构信息
     *
     * @param organizationDo 机构信息
     * @return 插入数量
     */
    int insert(OrganizationDO organizationDo);

    /**
     * 更新机构信息
     *
     * @param organizationDo 机构信息
     * @return 更新数量
     */
    int update(OrganizationDO organizationDo);
}
