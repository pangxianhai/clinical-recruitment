package com.andy.recruitment.dao.recruitment.mapper;

import com.andy.recruitment.dao.recruitment.entity.RecruitmentDepartmentDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentDepartmentQuery;
import com.andy.spring.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 招募项目的研究机构 mapper 接口
 *
 * @author 庞先海 2020-01-31
 */
@Repository
public interface RecruitmentDepartmentMapper {

    /**
     * 招募项目研究机构查询
     *
     * @param query 查询参数
     * @return 招募项目研究机构信息
     */
    List<RecruitmentDepartmentDO> select(RecruitmentDepartmentQuery query);

    /**
     * 招募项目研究机构分页查询
     *
     * @param query 查询出参数
     * @param page  分页参数
     * @return 招募项目研究机构信息
     */
    List<RecruitmentDepartmentDO> select(RecruitmentDepartmentQuery query, Page page);

    /**
     * 添加招募项目的研究机构
     *
     * @param recruitmentDepartmentDo 招募项目研究机构信息
     * @return 插入数量
     */
    int insert(RecruitmentDepartmentDO recruitmentDepartmentDo);

    /**
     * 更新招募项目的研究机构
     *
     * @param recruitmentDepartmentDo 招募项目研究机构信息
     * @return 更新数量
     */
    int update(RecruitmentDepartmentDO recruitmentDepartmentDo);

    /**
     * 删除项目的研究机构
     *
     * @param query 删除参数
     * @return 删除数量
     */
    int delete(RecruitmentDepartmentQuery query);
}
