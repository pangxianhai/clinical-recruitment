package com.andy.recruitment.recruitment.ao;

import com.andy.recruitment.recruitment.model.RecruitmentApplicationInfo;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationQueryParam;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;

/**
 * 招募信息申请记录AO
 *
 * @author 庞先海 2019-01-24
 */
public interface RecruitmentApplicationAO {

    /**
     * 添加申请记录
     *
     * @param applicationInfo 招募申请信息
     * @param userId          患者用户ID
     * @param operator        操作人
     */
    void addRecruitmentApplication(Long userId, RecruitmentApplicationInfo applicationInfo, String operator);

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
}
