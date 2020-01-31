package com.andy.recruitment.dao.recruitment.mapper;

import com.andy.recruitment.dao.recruitment.entity.RecruitmentOrganizationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentOrganizationQuery;
import com.soyoung.base.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 招募项目的研究机构 mapper 接口
 *
 * @author 庞先海 2020-01-31
 */
@Repository
public interface RecruitmentOrganizationMapper {

    /**
     * 招募项目研究机构查询
     *
     * @param query 查询参数
     * @return 招募项目研究机构信息
     */
    List<RecruitmentOrganizationDO> select(RecruitmentOrganizationQuery query);

    /**
     * 招募项目研究机构分页查询
     *
     * @param query 查询出参数
     * @param page  分页参数
     * @return 招募项目研究机构信息
     */
    List<RecruitmentOrganizationDO> select(RecruitmentOrganizationQuery query, Page page);

    /**
     * 添加招募项目的研究机构
     *
     * @param recruitmentOrganizationDo 招募项目研究机构信息
     * @return 插入数量
     */
    int insert(RecruitmentOrganizationDO recruitmentOrganizationDo);

    /**
     * 更新招募项目的研究机构
     *
     * @param recruitmentOrganizationDo 招募项目研究机构信息
     * @return 更新数量
     */
    int update(RecruitmentOrganizationDO recruitmentOrganizationDo);

    /**
     * 删除项目的研究机构
     *
     * @param query 删除参数
     * @return 删除数量
     */
    int delete(RecruitmentOrganizationQuery query);
}
