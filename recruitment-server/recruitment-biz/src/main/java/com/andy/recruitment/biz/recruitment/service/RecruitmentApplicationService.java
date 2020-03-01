package com.andy.recruitment.biz.recruitment.service;

import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;

/**
 * 招募申请记录服务
 *
 * @author 庞先海 2020-03-01
 */
public interface RecruitmentApplicationService {

    /**
     * 添加申请记录
     *
     * @param applicationDo 招募申请信息
     * @param operator      操作人
     */
    void addRecruitmentApplication(RecruitmentApplicationDO applicationDo, String operator);

    /**
     * 查询申请记录信息
     *
     * @param queryParam 查询参数
     * @param paginator  分页参数
     * @return 招募申请信息列表
     */
    PageResult<RecruitmentApplicationDO> getRecruitmentApplicationInfo(RecruitmentApplicationQuery queryParam,
        Paginator paginator);
}
