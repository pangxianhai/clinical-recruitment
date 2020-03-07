package com.andy.recruitment.dao.patient.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.patient.entity.PatientQuery;
import com.andy.recruitment.dao.patient.mapper.PatientInfoMapper;
import com.andy.spring.mybatis.paginator.Page;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.PageUtil;
import com.andy.spring.page.Paginator;
import com.andy.spring.util.CollectionUtil;
import com.andy.spring.util.asserts.AssertUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 患者信息服务
 *
 * @author 庞先海 2018-12-27
 */
@Service
public class PatientInfoDAOImpl implements PatientInfoDAO {

    private final PatientInfoMapper patientInfoMapper;

    @Autowired
    public PatientInfoDAOImpl(PatientInfoMapper patientInfoMapper) {
        this.patientInfoMapper = patientInfoMapper;
    }

    @Override
    public Long addPatientInfo(PatientInfoDO patientInfoDo, String operator) {
        patientInfoDo.setCreatedBy(operator);
        patientInfoDo.setCreatedTime(LocalDateTime.now());
        int count = this.patientInfoMapper.insert(patientInfoDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.PATIENT_ADD_FAILED);
        });
        return patientInfoDo.getId();
    }

    @Override
    public void updatePatientInfo(PatientInfoDO patientInfoDo, String operator) {
        AssertUtil.assertNull(patientInfoDo.getId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.PATIENT_ID_EMPTY);
        });
        patientInfoDo.setUpdatedBy(operator);
        patientInfoDo.setUpdatedTime(LocalDateTime.now());
        int count = this.patientInfoMapper.update(patientInfoDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.PATIENT_UPDATE_FAILED);
        });
    }

    @Override
    public PatientInfoDO getPatientInfoById(Long patientId) {
        if (null == patientId) {
            return null;
        }
        PatientQuery queryParam = new PatientQuery();
        queryParam.setPatientId(patientId);
        List<PatientInfoDO> patientInfoDoList = this.patientInfoMapper.select(queryParam);
        return CollectionUtil.parseOne(patientInfoDoList, t -> t);
    }

    @Override
    public PatientInfoDO getPatientInfoByUserId(Long userId) {
        if (null == userId) {
            return null;
        }
        PatientQuery queryParam = new PatientQuery();
        queryParam.setUserId(userId);
        List<PatientInfoDO> patientInfoDoList = this.patientInfoMapper.select(queryParam);
        return CollectionUtil.parseOne(patientInfoDoList, t -> t);
    }

    @Override
    public PageResult<PatientInfoDO> getPatientInfo(PatientQuery queryParam, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<PatientInfoDO> patientInfoDoList = this.patientInfoMapper.select(queryParam, page);
        return new PageResult<>(patientInfoDoList, PageUtil.transformToPaginator(page));
    }

    @Override
    public List<PatientInfoDO> getPatientInfoByUserIdList(List<Long> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)) {
            return null;
        }
        PatientQuery queryParam = new PatientQuery();
        queryParam.setUserIdList(userIdList);
        return this.patientInfoMapper.select(queryParam);
    }
}
