package com.andy.recruitment.recruitment.model;

import com.andy.recruitment.recruitment.constant.RecruitmentApplicationStatus;
import com.xgimi.base.BaseDO;
import com.xgimi.commons.util.JsonUtil;

/**
 * 招募信息申请信息DO
 *
 * @author 庞先海 2019-01-24
 */
public class RecruitmentApplicationDO extends BaseDO {

    /**
     * 招募ID
     */
    private Long recruitmentId;
    /**
     * 招募登记编号
     */
    private String recruitmentRegisterCode;
    /**
     * 患者ID
     */
    private Long patientId;
    /**
     * 医生ID
     */
    private Long doctorId;

    /**
     * 申请状态
     */
    private RecruitmentApplicationStatus status;

    public Long getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(Long recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public String getRecruitmentRegisterCode() {
        return recruitmentRegisterCode;
    }

    public void setRecruitmentRegisterCode(String recruitmentRegisterCode) {
        this.recruitmentRegisterCode = recruitmentRegisterCode;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public RecruitmentApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(RecruitmentApplicationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
