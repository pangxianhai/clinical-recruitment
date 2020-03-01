package com.andy.recruitment.biz.patient.service;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.patient.constant.PatientStatus;
import com.andy.recruitment.dao.patient.dao.PatientInfoDAO;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.patient.entity.PatientQuery;
import com.andy.recruitment.dao.user.dao.UserDAO;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;
import com.soyoung.base.util.asserts.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 患者信息服务接口实现
 *
 * @author 庞先海 2020-02-16
 */
@Service
public class PatientInfoServiceImpl implements PatientInfoService {

    private final PatientInfoDAO patientInfoDAO;

    private final UserDAO userDAO;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public PatientInfoServiceImpl(PatientInfoDAO patientInfoDAO, UserDAO userDAO,
        TransactionTemplate transactionTemplate) {
        this.patientInfoDAO = patientInfoDAO;
        this.userDAO = userDAO;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Long registerPatient(PatientInfoDO patientInfoDo, UserInfoDO userInfoDo, String operator) {
        return transactionTemplate.execute((status) -> {
            Long userId = this.userDAO.registerUser(userInfoDo, operator);
            patientInfoDo.setUserId(userId);
            PatientInfoDO existPatientInfoDo = this.patientInfoDAO.getPatientInfoByUserId(userId);
            if (existPatientInfoDo == null) {
                this.patientInfoDAO.addPatientInfo(patientInfoDo, operator);
            } else {
                patientInfoDo.setId(existPatientInfoDo.getId());
                this.patientInfoDAO.updatePatientInfo(patientInfoDo, operator);
            }
            return userId;
        });
    }

    @Override
    public void updatePatient(PatientInfoDO patientInfoDo, UserInfoDO userInfoDo, String operator) {
        PatientInfoDO existPatientInfoDo = this.patientInfoDAO.getPatientInfoById(patientInfoDo.getId());
        AssertUtil.assertNull(existPatientInfoDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.PATIENT_NOT_EXIST);
        });
        transactionTemplate.execute((status) -> {
            this.patientInfoDAO.updatePatientInfo(patientInfoDo, operator);
            userInfoDo.setId(existPatientInfoDo.getUserId());
            this.userDAO.updateUserInfo(userInfoDo, operator);
            return null;
        });
    }

    @Override
    public void updatePatientStatus(Long patientId, PatientStatus status, String operator) {
        PatientInfoDO patientInfoDo = new PatientInfoDO();
        patientInfoDo.setId(patientId);
        patientInfoDo.setStatus(status);
        this.patientInfoDAO.updatePatientInfo(patientInfoDo, operator);
    }

    @Override
    public PageResult<PatientInfoDO> getPatient(PatientQuery query, Paginator paginator) {
        return this.patientInfoDAO.getPatientInfo(query, paginator);
    }

    @Override
    public PatientInfoDO getPatient(Long patientId) {
        return this.patientInfoDAO.getPatientInfoById(patientId);
    }

    @Override
    public PatientInfoDO getPatientByUserId(Long userId) {
        return this.patientInfoDAO.getPatientInfoByUserId(userId);
    }
}
