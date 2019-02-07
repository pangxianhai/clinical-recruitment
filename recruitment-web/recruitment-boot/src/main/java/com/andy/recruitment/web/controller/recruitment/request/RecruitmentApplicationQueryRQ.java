package com.andy.recruitment.web.controller.recruitment.request;

import com.xgimi.base.BaseQueryRQ;
import java.io.Serializable;

/**
 * 招募申请信息查询参数
 *
 * @author 庞先海 2019-01-25
 */
public class RecruitmentApplicationQueryRQ extends BaseQueryRQ implements Serializable {

    /**
     * 申请记录ID
     */
    private Long applicationId;
    /**
     * 招募ID
     */
    private Long recruitmentId;
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
    private Integer status;
    /**
     * 招募登记编号
     */
    private String recruitmentRegisterCode;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(Long recruitmentId) {
        this.recruitmentId = recruitmentId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRecruitmentRegisterCode() {
        return recruitmentRegisterCode;
    }

    public void setRecruitmentRegisterCode(String recruitmentRegisterCode) {
        this.recruitmentRegisterCode = recruitmentRegisterCode;
    }
}
