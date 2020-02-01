package com.andy.recruitment.dao.recruitment.dao;

import com.andy.recruitment.dao.recruitment.entity.RecruitmentOrganizationDO;
import java.util.List;

/**
 * 项目研究机构DAO
 *
 * @author 庞先海 2020-01-31
 */
public interface RecruitmentOrganizationDAO {

    /**
     * 查询机构关联的所有项目ID
     *
     * @param departmentId  科室ID
     * @return 招募项目ID列表
     */
    List<Long> listRecruitmentByDepartment(Long departmentId);

    /**
     * 查询项目关联的所有科室ID
     *
     * @param recruitmentId 招募项目ID
     * @return 科室ID列表
     */
    List<Long> listDepartmentByRecruitment(Long recruitmentId);

    /**
     * 查询项目关联的所有机构ID
     *
     * @param recruitmentId 招募项目ID
     * @return 项目机构列表
     */
    List<RecruitmentOrganizationDO> listOrganizationByRecruitment(Long recruitmentId);

    /**
     * 添加招募项目和机构关联关系
     *
     * @param recruitmentId  招募项目ID
     * @param organizationId 机构ID
     * @param departmentId   科室ID
     * @param operator       添加人
     */
    void addRecruitmentOrganization(Long recruitmentId, Long organizationId, Long departmentId, String operator);

    /**
     * 删除招募项目和机构关联关系
     *
     * @param recruitmentId 招募项目ID
     * @param departmentId  科室ID
     */
    void deleteRecruitmentOrganization(Long recruitmentId, Long departmentId);
}
