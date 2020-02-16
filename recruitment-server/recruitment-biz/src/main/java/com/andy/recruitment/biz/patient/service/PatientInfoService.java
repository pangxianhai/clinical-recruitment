package com.andy.recruitment.biz.patient.service;

import com.andy.recruitment.dao.patient.constant.PatientStatus;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.patient.entity.PatientQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;

/**
 * 患者信息服务
 *
 * @author 庞先海 2020-02-16
 */
public interface PatientInfoService {

    /**
     * 患者注册
     *
     * @param patientInfoDo 患者信息
     * @param userInfoDo    用户信息
     * @param operator      操作人
     */
    void registerPatient(PatientInfoDO patientInfoDo, UserInfoDO userInfoDo, String operator);

    /**
     * 更新患者信息
     *
     * @param patientInfoDo 患者信息
     * @param userInfoDo    用户信息
     * @param operator      操作人
     */
    void updatePatient(PatientInfoDO patientInfoDo, UserInfoDO userInfoDo, String operator);

    /**
     * 更新 患者状态
     *
     * @param patientId 患者状态
     * @param status    修改后的状态
     * @param operator  操作人
     */
    void updatePatientStatus(Long patientId, PatientStatus status, String operator);

    /**
     * 分页查询患者信息
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 患者信息
     */
    PageResult<PatientInfoDO> getPatient(PatientQuery query, Paginator paginator);

    /**
     * 通过患者ID查询患者信息
     *
     * @param patientId 患者ID
     * @return 患者信息
     */
    PatientInfoDO getPatient(Long patientId);
}
