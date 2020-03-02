package com.andy.recruitment.dao.recruitment.dao;

import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;

/**
 * 招募信息dao
 *
 * @author 庞先海 2020-01-26
 */
public interface RecruitmentDAO {

    /**
     * 添加招募信息
     *
     * @param recruitmentInfoDo 招募信息
     * @param operator          添加人
     * @return 招募ID
     */
    Long addRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, String operator);

    /**
     * 更新招募信息
     *
     * @param recruitmentInfoDo 招募信息
     * @param operator          修改人
     */
    void updateRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, String operator);

    /**
     * 分页查询招募信息
     *
     * @param queryParam 查询参数
     * @param paginator  分页参数
     * @return 招募信息
     */
    PageResult<RecruitmentInfoDO> getRecruitmentInfo(RecruitmentQuery queryParam, Paginator paginator);

    /**
     * 通过招募ID查询招募信息
     *
     * @param recruitmentId 招募ID
     * @return 招募信息
     */
    RecruitmentInfoDO getRecruitmentInfoById(Long recruitmentId);
}
