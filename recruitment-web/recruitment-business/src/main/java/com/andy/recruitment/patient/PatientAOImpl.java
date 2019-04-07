package com.andy.recruitment.patient;

import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.patient.model.PatientInfo;
import com.andy.recruitment.patient.model.PatientQueryParam;
import com.andy.recruitment.patient.service.PatientInfoService;
import com.andy.recruitment.user.service.UserInfoService;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 患者业务信息
 *
 * @author 庞先海 2019-01-22
 */
@Component
public class PatientAOImpl implements PatientAO {

    private final PatientInfoService patientInfoService;

    private final UserInfoService userInfoService;

    @Autowired
    public PatientAOImpl(PatientInfoService patientInfoService, UserInfoService userInfoService) {
        this.patientInfoService = patientInfoService;
        this.userInfoService = userInfoService;
    }

    @Override
    public Long addPatientInfo(PatientInfo patientInfo, String operator) {
        if (null == patientInfo.getUserInfo()) {
            throw new BusinessException(BusinessErrorCode.USER_NOT_EMPTY);
        }
        Long userId = this.userInfoService.addUserInfo(patientInfo.getUserInfo(), operator);
        patientInfo.setUserId(userId);
        PatientInfo existPatientInfo = this.patientInfoService.getPatientInfoByUserId(userId);
        if (null == existPatientInfo) {
            return this.patientInfoService.addPatientInfo(patientInfo, operator);
        } else {
            patientInfo.setPatientId(existPatientInfo.getPatientId());
            this.patientInfoService.updatePatientInfo(patientInfo, operator);
            return existPatientInfo.getPatientId();
        }
    }

    @Override
    public void updatePatientInfo(PatientInfo patientInfo, String operator) {
        this.patientInfoService.updatePatientInfo(patientInfo, operator);
    }

    @Override
    public PatientInfo getPatientInfoById(Long patientId) {
        return this.patientInfoService.getPatientInfoById(patientId);
    }

    @Override
    public PatientInfo getPatientInfoByUserId(Long userId) {
        return this.patientInfoService.getPatientInfoByUserId(userId);
    }

    @Override
    public PageResult<PatientInfo> getPatientInfo(PatientQueryParam queryParam, Paginator paginator) {
        return this.patientInfoService.getPatientInfo(queryParam, paginator);
    }
}
