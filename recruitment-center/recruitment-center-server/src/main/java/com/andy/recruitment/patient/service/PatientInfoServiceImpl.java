package com.andy.recruitment.patient.service;

import com.andy.recruitment.exception.RecruitmentErrorCode;
import com.andy.recruitment.exception.RecruitmentException;
import com.andy.recruitment.patient.mapper.PatientInfoMapper;
import com.andy.recruitment.patient.model.PatientInfo;
import com.andy.recruitment.patient.model.PatientInfoDO;
import com.andy.recruitment.patient.model.PatientQueryParam;
import com.andy.recruitment.patient.util.PatientUtil;
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
 * 患者信息服务
 *
 * @author 庞先海 2018-12-27
 */
@Service
public class PatientInfoServiceImpl implements PatientInfoService {

    private final PatientInfoMapper patientInfoMapper;

    @Autowired
    public PatientInfoServiceImpl(PatientInfoMapper patientInfoMapper) {
        this.patientInfoMapper = patientInfoMapper;
    }

    @Override
    public void addPatientInfo(PatientInfo patientInfo, String operator) {
        PatientInfoDO patientInfoDO = PatientUtil.transformPatientInfoDO(patientInfo);
        patientInfoDO.setCreatedBy(operator);
        patientInfoDO.setCreatedTime(new Timestamp(DateUtil.currentMilliseconds()));
        int count = this.patientInfoMapper.insert(patientInfoDO);
        AssertUtil.assertBoolean(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.PATIENT_ADD_FAILED);
        });
    }

    @Override
    public void updatePatientInfo(PatientInfo patientInfo, String operator) {
        AssertUtil.assertNull(patientInfo.getPatientId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.PATIENT_ID_EMPTY);
        });
        PatientInfoDO patientInfoDO = PatientUtil.transformPatientInfoDO(patientInfo);
        patientInfoDO.setUpdatedBy(operator);
        patientInfoDO.setUpdatedTime(new Timestamp(DateUtil.currentMilliseconds()));
        int count = this.patientInfoMapper.update(patientInfoDO);
        AssertUtil.assertBoolean(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.PATIENT_UPDATE_FAILED);
        });
    }

    @Override
    public PatientInfo getPatientInfoById(Long patientId) {
        if (null == patientId) {
            return null;
        }
        PatientQueryParam queryParam = new PatientQueryParam();
        queryParam.setPatientId(patientId);
        List<PatientInfoDO> patientInfoDOList = this.patientInfoMapper.select(queryParam);
        return CollectionUtil.parseOne(patientInfoDOList, PatientUtil::transformPatientInfo);
    }

    @Override
    public PatientInfo getPatientInfoByUserId(Long userId) {
        if (null == userId) {
            return null;
        }
        PatientQueryParam queryParam = new PatientQueryParam();
        queryParam.setUserId(userId);
        List<PatientInfoDO> patientInfoDOList = this.patientInfoMapper.select(queryParam);
        return CollectionUtil.parseOne(patientInfoDOList, PatientUtil::transformPatientInfo);
    }

    @Override
    public PageResult<PatientInfo> getPatientInfo(PatientQueryParam queryParam, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<PatientInfoDO> patientInfoDOList = this.patientInfoMapper.select(queryParam, page);
        List<PatientInfo> patientInfoList = PatientUtil.transformPatientInfo(patientInfoDOList);
        return new PageResult<>(patientInfoList, paginator);
    }
}
