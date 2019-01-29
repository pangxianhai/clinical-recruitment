package com.andy.recruitment.patient.service;

import com.andy.recruitment.patient.model.PatientInfo;
import com.andy.recruitment.patient.model.PatientQueryParam;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;

/**
 * 病人信息服务
 *
 * @author 庞先海 2018-12-25
 */
public interface PatientInfoService {

    /**
     * 添加患者
     *
     * @param patientInfo 患者信息
     * @param operator    操作人
     */
    void addPatientInfo(PatientInfo patientInfo, String operator);

    /**
     * 更新患者信息
     *
     * @param patientInfo 患者信息
     * @param operator    操作人
     */
    void updatePatientInfo(PatientInfo patientInfo, String operator);

    /**
     * 通过患者ID获取患者信息
     *
     * @param patientId 患者ID
     * @return 患者信息
     */
    PatientInfo getPatientInfoById(Long patientId);

    /**
     * 通过用户ID获取患者信息
     *
     * @param userId 用户ID
     * @return 患者信息
     */
    PatientInfo getPatientInfoByUserId(Long userId);

    /**
     * 患者信息查询
     *
     * @param queryParam 查询参数
     * @param paginator  分页信息
     * @return 患者信息列表
     */
    PageResult<PatientInfo> getPatientInfo(PatientQueryParam queryParam, Paginator paginator);
}
