package com.andy.recruitment.doctor.ao;

import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.doctor.model.DoctorQueryParam;
import com.andy.recruitment.doctor.service.DoctorInfoService;
import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.user.service.UserInfoService;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 医生AO
 *
 * @author 庞先海 2019-01-20
 */
@Component
public class DoctorAOImpl implements DoctorAO {

    private final DoctorInfoService doctorInfoService;

    private final UserInfoService userInfoService;

    @Autowired
    public DoctorAOImpl(DoctorInfoService doctorInfoService, UserInfoService userInfoService) {
        this.doctorInfoService = doctorInfoService;
        this.userInfoService = userInfoService;
    }

    @Override
    public DoctorInfo registerDoctor(DoctorInfo doctorInfo, String operator) {
        if (null == doctorInfo.getUserInfo()) {
            throw new BusinessException(BusinessErrorCode.USER_NOT_EMPTY);
        }
        Long userId = this.userInfoService.registerUser(doctorInfo.getUserInfo(), operator);
        doctorInfo.setUserId(userId);
        DoctorInfo existDoctorInfo = this.doctorInfoService.getDoctorInfoByUserId(userId);
        if (null == existDoctorInfo) {
            Long doctorId = this.doctorInfoService.addDoctorInfo(doctorInfo, operator);
            doctorInfo.setDoctorId(doctorId);
        } else {
            doctorInfo.setDoctorId(existDoctorInfo.getDoctorId());
            this.doctorInfoService.updateDoctorInfo(doctorInfo, operator);
        }
        return doctorInfo;
    }

    @Override
    public void updateDoctorInfo(DoctorInfo doctorInfo, String operator) {
        this.doctorInfoService.updateDoctorInfo(doctorInfo, operator);
    }

    @Override
    public DoctorInfo getDoctorInfoById(Long doctorId) {
        return this.doctorInfoService.getDoctorInfoById(doctorId);
    }

    @Override
    public DoctorInfo getDoctorInfoByUserId(Long userId) {
        return this.doctorInfoService.getDoctorInfoByUserId(userId);
    }

    @Override
    public PageResult<DoctorInfo> getDoctorInfo(DoctorQueryParam queryParam, Paginator paginator) {
        return this.doctorInfoService.getDoctorInfo(queryParam, paginator);
    }
}
