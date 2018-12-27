package com.andy.recruitment.doctor.service;

import com.andy.recruitment.doctor.mapper.DoctorInfoMapper;
import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.doctor.model.DoctorQueryParam;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 医生信息服务
 *
 * @author 庞先海 2018-12-26
 */
@Service
public class DoctorInfoServiceImpl implements DoctorInfoService {

    private final DoctorInfoMapper doctorInfoMapper;

    @Autowired
    public DoctorInfoServiceImpl(DoctorInfoMapper doctorInfoMapper) {
        this.doctorInfoMapper = doctorInfoMapper;
    }

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
