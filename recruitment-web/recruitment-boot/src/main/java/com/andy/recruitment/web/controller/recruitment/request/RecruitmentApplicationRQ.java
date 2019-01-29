package com.andy.recruitment.web.controller.recruitment.request;

import java.io.Serializable;

/**
 * 申请招募参数
 *
 * @author 庞先海 2019-01-25
 */
public class RecruitmentApplicationRQ implements Serializable {

    /**
     * 招募ID
     */
    private Long recruitmentId;
    /**
     * 医生ID
     */
    private Long doctorId;

    public Long getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(Long recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
