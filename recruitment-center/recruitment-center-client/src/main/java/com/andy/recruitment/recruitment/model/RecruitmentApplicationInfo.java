package com.andy.recruitment.recruitment.model;

import com.andy.recruitment.recruitment.constant.RecruitmentApplicationStatus;
import com.xgimi.commons.util.JsonUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 招募申请信息
 *
 * @author 庞先海 2019-01-24
 */
public class RecruitmentApplicationInfo implements Serializable {

    /**
     * 申请记录ID
     */
    private Long applicationId;
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
    /**
     * 申请状态
     */
    private RecruitmentApplicationStatus status;
    /**
     * 申请时间
     */
    private Date applicationTime;

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

    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
