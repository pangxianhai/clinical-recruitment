package com.andy.recruitment.biz.recruitment.service;

import com.andy.recruitment.dao.hospital.entity.DepartmentDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentDepartmentDO;
import java.util.List;

/**
 * 项目关联的科室
 *
 * @author 庞先海 2020-04-08
 */
public interface RecruitmentDepartmentService {

    /**
     * 查询项目关联的所有科室
     *
     * @param recruitmentId 招募项目ID
     * @return 项目科室列表
     */
    List<RecruitmentDepartmentDO> listDepartmentByRecruitment(Long recruitmentId);

    /**
     * 查询项目关联的所有科室ID
     *
     * @param recruitmentId 招募项目ID
     * @return 项目科室ID列表
     */
    List<Long> listDepartmentIdByRecruitment(Long recruitmentId);

    /**
     * 更新项目的研究科室
     *
     * @param recruitmentId    项目ID
     * @param departmentDoList 研究科室列表
     * @param operator         操作人
     */
    void updateRecruitmentDepartment(Long recruitmentId, List<DepartmentDO> departmentDoList, String operator);
}
