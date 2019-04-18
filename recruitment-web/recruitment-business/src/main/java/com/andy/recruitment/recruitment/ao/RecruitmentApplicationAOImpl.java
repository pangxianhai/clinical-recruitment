package com.andy.recruitment.recruitment.ao;

import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.patient.service.PatientInfoService;
import com.andy.recruitment.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationInfo;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationQueryParam;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.recruitment.service.RecruitmentApplicationService;
import com.andy.recruitment.recruitment.service.RecruitmentService;
import com.andy.recruitment.user.service.UserInfoService;
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

    private final UserInfoService userInfoService;

    private final PatientInfoService patientInfoService;

    @Autowired
    public RecruitmentApplicationAOImpl(RecruitmentApplicationService recruitmentApplicationService,
                                        RecruitmentService recruitmentService, UserInfoService userInfoService,
                                        PatientInfoService patientInfoService) {
        this.recruitmentApplicationService = recruitmentApplicationService;
        this.recruitmentService = recruitmentService;
        this.userInfoService = userInfoService;
        this.patientInfoService = patientInfoService;
    }

    @Override
    public void addRecruitmentApplication(RecruitmentApplicationInfo applicationInfo, String operator) {
        if (null != applicationInfo.getRecruitmentId()) {
            RecruitmentInfo recruitmentInfo = this.recruitmentService.getRecruitmentInfoById(
                applicationInfo.getRecruitmentId());
            AssertUtil.assertNull(recruitmentInfo, () -> {
                throw new BusinessException(BusinessErrorCode.RECRUITMENT_ADD_FAILED);
            });

            AssertUtil.assertBoolean(! RecruitmentStatus.NOT_BEGIN.equals(recruitmentInfo.getStatus()), () -> {
                throw new BusinessException(BusinessErrorCode.RECRUITMENT_NOT_BEGIN);
            });
            AssertUtil.assertBoolean(! RecruitmentStatus.FINISHED.equals(recruitmentInfo.getStatus()), () -> {
                throw new BusinessException(BusinessErrorCode.RECRUITMENT_HAS_FINISHED);
            });
            applicationInfo.setRecruitmentRegisterCode(recruitmentInfo.getRegisterCode());
        }
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

    @Override
    public RecruitmentApplicationInfo getRecruitmentApplicationInfo(Long applicationId) {
        return this.recruitmentApplicationService.getRecruitmentApplicationInfo(applicationId);
    }
}
