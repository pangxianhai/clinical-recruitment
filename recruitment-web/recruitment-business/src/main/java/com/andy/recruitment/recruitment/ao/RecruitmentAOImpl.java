package com.andy.recruitment.recruitment.ao;

import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.recruitment.model.RecruitmentQueryParam;
import com.andy.recruitment.recruitment.service.RecruitmentService;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 招募信息AO实现
 *
 * @author 庞先海 2019-01-06
 */
@Component
public class RecruitmentAOImpl implements RecruitmentAO {

    private final RecruitmentService recruitmentService;

    @Autowired
    public RecruitmentAOImpl(RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }

    @Override
    public void addRecruitmentInfo(RecruitmentInfo recruitmentInfo, String operator) {
        this.recruitmentService.addRecruitmentInfo(recruitmentInfo, operator);
    }

    @Override
    public void updateRecruitmentInfo(RecruitmentInfo recruitmentInfo, String operator) {
        this.recruitmentService.updateRecruitmentInfo(recruitmentInfo, operator);
    }

    @Override
    public PageResult<RecruitmentInfo> getRecruitmentInfo(RecruitmentQueryParam queryParam, Paginator paginator) {
        return this.recruitmentService.getRecruitmentInfo(queryParam, paginator);
    }

    @Override
    public RecruitmentInfo getRecruitmentInfoById(Long recruitmentId) {
        return this.recruitmentService.getRecruitmentInfoById(recruitmentId);
    }
}
