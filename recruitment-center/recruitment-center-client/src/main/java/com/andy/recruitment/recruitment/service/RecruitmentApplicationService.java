package com.andy.recruitment.recruitment.service;

import com.andy.recruitment.recruitment.model.RecruitmentApplicationInfo;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationQueryParam;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;

/**
 * 招募信息申请服务
 *
 * @author 庞先海 2019-01-24
 */
public interface RecruitmentApplicationService {

    /**
     * 添加申请记录
     *
     * @param applicationInfo 招募申请信息
     * @param operator        操作人
     */
    void addRecruitmentApplication(RecruitmentApplicationInfo applicationInfo, String operator);

    /**
     * 更新申请记录
     *
     * @param applicationInfo 招募申请信息
     * @param operator        操作人
     */
    void updateRecruitmentApplication(RecruitmentApplicationInfo applicationInfo, String operator);

    /**
     * 查询申请记录信息
     *
     * @param queryParam 查询参数
     * @param paginator  分页参数
     * @return 招募申请信息列表
     */
    PageResult<RecruitmentApplicationInfo> getRecruitmentApplicationInfo(RecruitmentApplicationQueryParam queryParam,
                                                                         Paginator paginator);

    /**
     * 通过申请ID查询申请记录信息
     *
     * @param applicationId 申请ID
     * @return 申请记录信息
     */
    RecruitmentApplicationInfo getRecruitmentApplicationInfo(Long applicationId);

    /**
     * 通过患者和招募查询申请记录
     *
     * @param patientId     患者ID
     * @param recruitmentId 招募ID
     * @return 申请信息
     */
    RecruitmentApplicationInfo getRecruitmentApplicationInfo(Long patientId, Long recruitmentId);
}
