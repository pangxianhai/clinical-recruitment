package com.andy.recruitment.web.controller.recruitment.request;

import com.andy.recruitment.web.controller.patient.request.PatientAddRQ;
import java.io.Serializable;

/**
 * 申请招募参数
 *
 * @author 庞先海 2019-01-25
 */
public class RecruitmentApplicationRQ extends PatientAddRQ implements Serializable {

    /**
     * 招募ID
     */
    private Long recruitmentId;
    /**
     * 医生ID
     */
    private Long doctorUserId;

    public Long getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(Long recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public Long getDoctorUserId() {
        return doctorUserId;
    }

    public void setDoctorUserId(Long doctorUserId) {
        this.doctorUserId = doctorUserId;
    }
}
