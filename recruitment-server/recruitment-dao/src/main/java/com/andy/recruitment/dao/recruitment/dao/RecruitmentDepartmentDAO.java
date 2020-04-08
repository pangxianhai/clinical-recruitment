package com.andy.recruitment.dao.recruitment.dao;

import com.andy.recruitment.dao.recruitment.entity.RecruitmentDepartmentDO;
import java.util.List;

/**
 * 项目研究机构DAO
 *
 * @author 庞先海 2020-01-31
 */
public interface RecruitmentDepartmentDAO {

    /**
     * 查询机构关联的所有项目ID
     *
     * @param departmentId  科室ID
     * @return 项目科室列表
     */
    List<RecruitmentDepartmentDO> listRecruitmentByDepartment(Long departmentId);

    /**
     * 查询项目关联的所有机构ID
     *
     * @param recruitmentId 招募项目ID
     * @return 项目科室列表
     */
    List<RecruitmentDepartmentDO> listDepartmentByRecruitment(Long recruitmentId);

    /**
     * 查询机构关联的所有项目
     *
     * @param hospitalId 机构ID
     * @return 项目科室列表
     */
    List<RecruitmentDepartmentDO> listDepartmentByHospitalId(Long hospitalId);

    /**
     * 添加招募项目和机构关联关系
     *
     * @param recruitmentId 招募项目ID
     * @param hospitalId    机构ID
     * @param departmentId  科室ID
     * @param operator      添加人
     */
    void addRecruitmentDepartment(Long recruitmentId, Long hospitalId, Long departmentId, String operator);

    /**
     * 删除招募项目和机构关联关系
     *
     * @param recruitmentId 招募项目ID
     * @param departmentId  科室ID
     */
    void deleteRecruitmentDepartment(Long recruitmentId, Long departmentId);
}
