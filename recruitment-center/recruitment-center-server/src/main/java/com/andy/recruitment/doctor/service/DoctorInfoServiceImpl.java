package com.andy.recruitment.doctor.service;

import com.andy.recruitment.doctor.mapper.DoctorInfoMapper;
import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.doctor.model.DoctorInfoDO;
import com.andy.recruitment.doctor.model.DoctorQueryParam;
import com.andy.recruitment.doctor.util.DoctorInfoUtil;
import com.andy.recruitment.exception.RecruitmentErrorCode;
import com.andy.recruitment.exception.RecruitmentException;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.DateUtil;
import com.xgimi.commons.util.asserts.AssertUtil;
import com.xgimi.mybatis.paginator.Page;
import com.xgimi.util.PageUtil;
import java.sql.Timestamp;
import java.util.List;
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
    public Long addDoctorInfo(DoctorInfo doctorInfo, String operator) {
        DoctorInfoDO doctorInfoDO = DoctorInfoUtil.transformDoctorInfoDO(doctorInfo);
        doctorInfoDO.setCreatedBy(operator);
        doctorInfoDO.setCreatedTime(new Timestamp(DateUtil.currentMilliseconds()));
        int count = this.doctorInfoMapper.insert(doctorInfoDO);
        AssertUtil.assertBoolean(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.DOCTOR_ADD_FAILED);
        });
        return doctorInfoDO.getId();
    }

    @Override
    public void updateDoctorInfo(DoctorInfo doctorInfo, String operator) {
        AssertUtil.assertNull(doctorInfo.getDoctorId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.DOCTOR_ID_EMPTY);
        });
        DoctorInfoDO doctorInfoDO = DoctorInfoUtil.transformDoctorInfoDO(doctorInfo);
        doctorInfoDO.setUpdatedBy(operator);
        doctorInfoDO.setUpdatedTime(new Timestamp(DateUtil.currentMilliseconds()));
        int count = this.doctorInfoMapper.update(doctorInfoDO);
        AssertUtil.assertBoolean(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.DOCTOR_UPDATE_FAILED);
        });
    }

    @Override
    public DoctorInfo getDoctorInfoById(Long doctorId) {
        if (null == doctorId) {
            return null;
        }
        DoctorQueryParam queryParam = new DoctorQueryParam();
        queryParam.setDoctorId(doctorId);
        List<DoctorInfoDO> doctorInfoDOList = this.doctorInfoMapper.select(queryParam);
        return CollectionUtil.parseOne(doctorInfoDOList, DoctorInfoUtil::transformDoctorInfo);
    }

    @Override
    public PageResult<DoctorInfo> getDoctorInfo(DoctorQueryParam queryParam, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<DoctorInfoDO> doctorInfoDOList = this.doctorInfoMapper.select(queryParam, page);
        List<DoctorInfo> doctorInfoList = DoctorInfoUtil.transformDoctorInfo(doctorInfoDOList);
        return new PageResult<>(doctorInfoList, PageUtil.transformToPaginator(page));
    }

    @Override
    public DoctorInfo getDoctorInfoByUserId(Long userId) {
        if (null == userId) {
            return null;
        }
        DoctorQueryParam queryParam = new DoctorQueryParam();
        queryParam.setUserId(userId);
        List<DoctorInfoDO> doctorInfoDOList = this.doctorInfoMapper.select(queryParam);
        return CollectionUtil.parseOne(doctorInfoDOList, DoctorInfoUtil::transformDoctorInfo);
    }
}
