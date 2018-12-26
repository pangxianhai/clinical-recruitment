package com.andy.recruitment.doctor.service;

import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.doctor.model.DoctorQueryParam;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;

/**
 * 医生信息服务
 *
 * @author 庞先海 2018-12-26
 */
public class DoctorInfoServiceImpl implements DoctorInfoService {

    @Override
    public void addDoctorInfo(DoctorInfo doctorInfo, String operator) {

    }

    @Override
    public void updateDoctorInfo(DoctorInfo doctorInfo, String operator) {

    }

    @Override
    public DoctorInfo getDoctorInfoById(Long doctorId) {
        return null;
    }

    @Override
    public PageResult<DoctorInfo> getDoctorInfo(DoctorQueryParam queryParam, Paginator paginator) {
        return null;
    }
}
