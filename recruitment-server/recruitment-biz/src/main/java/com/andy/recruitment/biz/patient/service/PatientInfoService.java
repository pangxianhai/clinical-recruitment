package com.andy.recruitment.biz.patient.service;

import com.andy.recruitment.api.patient.response.PatientInfoDetailRes;
import com.andy.recruitment.common.patient.constant.PatientStatus;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.patient.entity.PatientQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.List;
import java.util.Map;

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
     * @return 患者用户ID
     */
    Long registerPatient(PatientInfoDO patientInfoDo, UserInfoDO userInfoDo, String operator);

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
     * 通过患者用户id列表查询患者信息
     *
     * @param userIdList 患者用户id列表
     * @return 患者信息
     */
    List<PatientInfoDO> getPatientInfoByUserIdList(List<Long> userIdList);

    /**
     * 批量查询患者详情
     *
     * @param userIdList 用户 id 列表
     * @return 患者详情
     */
    Map<Long, PatientInfoDetailRes> getReferenceDetailRes(List<Long> userIdList);

    /**
     * 通过患者ID查询患者信息
     *
     * @param patientId 患者ID
     * @return 患者信息
     */
    PatientInfoDO getPatient(Long patientId);

    /**
     * 通过用户ID查询患者信息 只返回有效的患者信息
     *
     * @param userId 用户ID
     * @return 患者信息
     */
    PatientInfoDO getPatientByUserId(Long userId);
}
