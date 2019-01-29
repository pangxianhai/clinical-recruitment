package com.andy.recruitment.recruitment.ao;

import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationInfo;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationQueryParam;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.recruitment.service.RecruitmentApplicationService;
import com.andy.recruitment.recruitment.service.RecruitmentService;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
import com.xgimi.commons.util.asserts.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 招募信息申请记录AO实现
 *
 * @author 庞先海 2019-01-24
 */
@Component
public class RecruitmentApplicationAOImpl implements RecruitmentApplicationAO {

    private final RecruitmentApplicationService recruitmentApplicationService;

    private final RecruitmentService recruitmentService;

    @Autowired
    public RecruitmentApplicationAOImpl(RecruitmentApplicationService recruitmentApplicationService,
                                        RecruitmentService recruitmentService) {
        this.recruitmentApplicationService = recruitmentApplicationService;
        this.recruitmentService = recruitmentService;
    }

    @Override
    public void addRecruitmentApplication(RecruitmentApplicationInfo applicationInfo, String operator) {
        RecruitmentInfo recruitmentInfo = this.recruitmentService.getRecruitmentInfoById(
            applicationInfo.getRecruitmentId());
        AssertUtil.assertNull(recruitmentInfo, () -> {
            throw new BusinessException(BusinessErrorCode.RECRUITMENT_ADD_FAILE);
        });

        applicationInfo.setRecruitmentRegisterCode(recruitmentInfo.getRegisterCode());
        this.recruitmentApplicationService.addRecruitmentApplication(applicationInfo, operator);
    }

    @Override
    public void updateRecruitmentApplication(RecruitmentApplicationInfo applicationInfo, String operator) {
        this.recruitmentApplicationService.updateRecruitmentApplication(applicationInfo, operator);
    }

    @Override
    public PageResult<RecruitmentApplicationInfo> getRecruitmentApplicationInfo(
        RecruitmentApplicationQueryParam queryParam, Paginator paginator) {
        return this.recruitmentApplicationService.getRecruitmentApplicationInfo(queryParam, paginator);
    }
}
