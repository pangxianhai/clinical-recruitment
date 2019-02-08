package com.andy.recruitment.web.controller.recruitment.response;

import com.andy.recruitment.recruitment.constant.RecruitmentApplicationStatus;
import com.andy.recruitment.web.controller.doctor.response.DoctorInfoVO;
import com.andy.recruitment.web.controller.patient.response.PatientVO;
import java.io.Serializable;

/**
 * 招募申请信息VO
 *
 * @author 庞先海 2019-01-25
 */
public class RecruitmentApplicationVO implements Serializable {

    /**
     * 申请记录ID
     */
    private Long applicationId;
    /**
     * 招募ID
     */
    private Long recruitmentId;

    /**
     * 招募信息
     */
    private RecruitmentVO recruitmentVO;

    /**
     * 患者ID
     */
    private Long patientId;
    /**
     * 患者信息
     */
    private PatientVO patientVO;
    /**
     * 医生ID
     */
    private Long doctorId;
    /**
     * 医生信息VO
     */
    private DoctorInfoVO doctorInfoVO;
    /**
     * 申请状态
     */
    private RecruitmentApplicationStatus status;
    /**
     * 申请时间
     */
    private String applicationTime;

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

    public RecruitmentApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(RecruitmentApplicationStatus status) {
        this.status = status;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public RecruitmentVO getRecruitmentVO() {
        return recruitmentVO;
    }

    public void setRecruitmentVO(RecruitmentVO recruitmentVO) {
        this.recruitmentVO = recruitmentVO;
    }

    public PatientVO getPatientVO() {
        return patientVO;
    }

    public void setPatientVO(PatientVO patientVO) {
        this.patientVO = patientVO;
    }

    public DoctorInfoVO getDoctorInfoVO() {
        return doctorInfoVO;
    }

    public void setDoctorInfoVO(DoctorInfoVO doctorInfoVO) {
        this.doctorInfoVO = doctorInfoVO;
    }
}
