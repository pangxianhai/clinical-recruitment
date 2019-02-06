package com.andy.recruitment.recruitment.service;

import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.recruitment.model.RecruitmentQueryParam;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;

/**
 * 招募信息服务
 *
 * @author 庞先海 2018-12-29
 */
public interface RecruitmentService {

    /**
     * 添加招募信息
     *
     * @param recruitmentInfo 招募信息
     * @param operator        添加人
     * @return 招募ID
     */
    Long addRecruitmentInfo(RecruitmentInfo recruitmentInfo, String operator);

    /**
     * 更新招募信息
     *
     * @param recruitmentInfo 招募信息
     * @param operator        修改人
     */
    void updateRecruitmentInfo(RecruitmentInfo recruitmentInfo, String operator);

    /**
     * 分页查询招募信息
     *
     * @param queryParam 查询参数
     * @param paginator  分页参数
     * @return 招募信息
     */
    PageResult<RecruitmentInfo> getRecruitmentInfo(RecruitmentQueryParam queryParam, Paginator paginator);

    /**
     * 通过招募ID查询招募信息
     *
     * @param recruitmentId 招募ID
     * @return 招募信息
     */
    RecruitmentInfo getRecruitmentInfoById(Long recruitmentId);
}
