package com.andy.recruitment.doctor.ao;

import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.doctor.model.DoctorQueryParam;
import com.andy.recruitment.doctor.service.DoctorInfoService;
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

    @Autowired
    public DoctorAOImpl(DoctorInfoService doctorInfoService) {
        this.doctorInfoService = doctorInfoService;
    }

    @Override
    public void addDoctorInfo(DoctorInfo doctorInfo, String operator) {
        this.doctorInfoService.addDoctorInfo(doctorInfo, operator);
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
