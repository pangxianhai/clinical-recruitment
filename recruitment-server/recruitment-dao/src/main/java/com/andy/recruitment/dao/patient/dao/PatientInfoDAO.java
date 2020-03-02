package com.andy.recruitment.dao.patient.dao;

import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.patient.entity.PatientQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;

/**
 * 病人信息服务
 *
 * @author 庞先海 2018-12-25
 */
public interface PatientInfoDAO {

    /**
     * 添加患者
     *
     * @param patientInfoDo 患者信息
     * @param operator      操作人
     * @return 患者ID
     */
    Long addPatientInfo(PatientInfoDO patientInfoDo, String operator);

    /**
     * 更新患者信息
     *
     * @param patientInfoDo 患者信息
     * @param operator      操作人
     */
    void updatePatientInfo(PatientInfoDO patientInfoDo, String operator);

    /**
     * 通过患者ID获取患者信息
     *
     * @param patientId 患者ID
     * @return 患者信息
     */
    PatientInfoDO getPatientInfoById(Long patientId);

    /**
     * 通过用户ID获取患者信息
     *
     * @param userId 用户ID
     * @return 患者信息
     */
    PatientInfoDO getPatientInfoByUserId(Long userId);

    /**
     * 患者信息查询
     *
     * @param queryParam 查询参数
     * @param paginator  分页信息
     * @return 患者信息列表
     */
    PageResult<PatientInfoDO> getPatientInfo(PatientQuery queryParam, Paginator paginator);
}
