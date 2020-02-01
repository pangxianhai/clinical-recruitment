package com.andy.recruitment.biz.recruitment.service;

import com.andy.recruitment.biz.recruitment.entity.DepartmentInfoBO;
import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;
import java.util.List;

/**
 * 招募项目服务接口
 *
 * @author 庞先海 2020-01-30
 */
public interface RecruitmentService {

    /**
     * 分页查询招募信息
     *
     * @param queryParam 查询参数
     * @param paginator  分页参数
     * @return 招募信息
     */
    PageResult<RecruitmentInfoDO> getRecruitmentInfo(RecruitmentQuery queryParam, Paginator paginator);

    /**
     * 通过招募项目ID查询招募信息
     *
     * @param recruitmentId 招募项目ID
     * @return 招募信息
     */
    RecruitmentInfoDO getRecruitmentInfo(Long recruitmentId);

    /**
     * 查询招募项目下所有机构列表
     *
     * @param recruitmentId 招募项目ID
     * @return 机构信息
     */
    List<DepartmentInfoBO> getOrganizationByRecruitment(Long recruitmentId);

    /**
     * 添加招募项目
     *
     * @param recruitmentInfoDo          招募项目信息
     * @param organizationDepartmentList 研究机构及科室列表
     * @param operator                   添加人
     */
    void addRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, List<List<Long>> organizationDepartmentList,
        String operator);

    /**
     * 更新招募项目
     *
     * @param recruitmentInfoDo          招募项目信息
     * @param organizationDepartmentList 研究机构及科室列表
     * @param operator                   更新人
     */
    void updateRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, List<List<Long>> organizationDepartmentList,
        String operator);

    /**
     * 更新项目状态
     *
     * @param recruitmentId 招募项目ID
     * @param status        更新后状态
     * @param operator      操作人
     */
    void updateRecruitmentInfoStatus(Long recruitmentId, RecruitmentStatus status, String operator);
}
