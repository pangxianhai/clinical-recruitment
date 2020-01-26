package com.andy.recruitment.dao.recruitment.dao;

import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;

/**
 * 招募申请记录DAO
 *
 * @author 庞先海 2020-01-26
 */
public interface RecruitmentApplicationDAO {

    /**
     * 添加申请记录
     *
     * @param applicationDo 招募申请信息
     * @param operator      操作人
     */
    void addRecruitmentApplication(RecruitmentApplicationDO applicationDo, String operator);

    /**
     * 更新申请记录
     *
     * @param applicationDo 招募申请信息
     * @param operator      操作人
     */
    void updateRecruitmentApplication(RecruitmentApplicationDO applicationDo, String operator);

    /**
     * 查询申请记录信息
     *
     * @param queryParam 查询参数
     * @param paginator  分页参数
     * @return 招募申请信息列表
     */
    PageResult<RecruitmentApplicationDO> getRecruitmentApplicationInfo(RecruitmentApplicationQuery queryParam,
        Paginator paginator);

    /**
     * 通过申请ID查询申请记录信息
     *
     * @param applicationId 申请ID
     * @return 申请记录信息
     */
    RecruitmentApplicationDO getRecruitmentApplicationInfo(Long applicationId);

    /**
     * 通过患者和招募查询申请记录
     *
     * @param patientUserId 患者用户ID
     * @param recruitmentId 招募ID
     * @return 申请信息
     */
    RecruitmentApplicationDO getRecruitmentApplicationInfo(Long patientUserId, Long recruitmentId);

}
