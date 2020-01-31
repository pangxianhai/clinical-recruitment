package com.andy.recruitment.web.controller.recruitment.request;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 招募项目添加参数
 *
 * @author 庞先海 2020-01-31
 */
@Data
public class RecruitmentAddReq implements Serializable {

    private static final long serialVersionUID = 1768873276941799885L;

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
    /**
     * 研究机构列表
     */
    private List<Long> organizationList;

}
