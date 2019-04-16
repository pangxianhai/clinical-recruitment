package com.andy.recruitment.web.controller.recruitment.request;

import com.andy.recruitment.web.controller.patient.request.PatientAddRQ;
import java.io.Serializable;
import java.util.List;

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
    /**
     * 病症描述
     */
    private String diseaseDesc;
    /**
     * 遗传病描述
     */
    private String geneticDiseaseDesc;
    /**
     * 病例图片
     */
    private List<String> diseaseImageList;

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

    public String getDiseaseDesc() {
        return diseaseDesc;
    }

    public void setDiseaseDesc(String diseaseDesc) {
        this.diseaseDesc = diseaseDesc;
    }

    public List<String> getDiseaseImageList() {
        return diseaseImageList;
    }

    public void setDiseaseImageList(List<String> diseaseImageList) {
        this.diseaseImageList = diseaseImageList;
    }

    public String getGeneticDiseaseDesc() {
        return geneticDiseaseDesc;
    }

    public void setGeneticDiseaseDesc(String geneticDiseaseDesc) {
        this.geneticDiseaseDesc = geneticDiseaseDesc;
    }
}
