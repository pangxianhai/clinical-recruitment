package com.andy.recruitment.web.controller.recruitment.request;

import java.io.Serializable;
import java.util.List;

/**
 * 招募信息添加参数
 *
 * @author 庞先海 2019-01-06
 */
public class RecruitmentAddRQ implements Serializable {

    /**
     * 登记编号
     */
    private String registerCode;
    /**
     * 标题
     */
    private String title;
    /**
     * 试验分期
     */
    private String practiceStages;
    /**
     * 适应症状
     */
    private String indication;
    /**
     * 药物名称
     */
    private String drugName;
    /**
     * 药物类型
     */
    private String drugType;
    /**
     * 招募人数
     */
    private Integer recruitmentNumber;
    /**
     * 简介
     */
    private String introduction;
    /**
     * 治疗方案
     */
    private String treatmentPlan;
    /**
     * 初筛要点
     */
    private String screeningStandard;
    /**
     * 入排标准
     */
    private String entryCriteria;
    /**
     * 研究中心
     */
    private List<ResearchCenterAddRQ> researchCenterList;
    /**
     * 患者权益
     */
    private String patientRights;
    /**
     * 启始时间
     */
    private String startTime;
    /**
     * 截至时间
     */
    private String stopTime;

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPracticeStages() {
        return practiceStages;
    }

    public void setPracticeStages(String practiceStages) {
        this.practiceStages = practiceStages;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public Integer getRecruitmentNumber() {
        return recruitmentNumber;
    }

    public void setRecruitmentNumber(Integer recruitmentNumber) {
        this.recruitmentNumber = recruitmentNumber;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public String getScreeningStandard() {
        return screeningStandard;
    }

    public void setScreeningStandard(String screeningStandard) {
        this.screeningStandard = screeningStandard;
    }

    public String getEntryCriteria() {
        return entryCriteria;
    }

    public void setEntryCriteria(String entryCriteria) {
        this.entryCriteria = entryCriteria;
    }

    public List<ResearchCenterAddRQ> getResearchCenterList() {
        return researchCenterList;
    }

    public void setResearchCenterList(List<ResearchCenterAddRQ> researchCenterList) {
        this.researchCenterList = researchCenterList;
    }

    public String getPatientRights() {
        return patientRights;
    }

    public void setPatientRights(String patientRights) {
        this.patientRights = patientRights;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }
}
