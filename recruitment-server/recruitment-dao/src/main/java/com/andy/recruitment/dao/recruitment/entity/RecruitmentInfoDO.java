package com.andy.recruitment.dao.recruitment.entity;

import com.andy.recruitment.dao.recruitment.constant.RecruitmentStatus;
import com.soyoung.base.base.BaseDO;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 招募信息DO
 *
 * @author 庞先海 2018-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RecruitmentInfoDO extends BaseDO {

    private static final long serialVersionUID = 6477619200423047094L;

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
    private LocalDateTime startTime;
    /**
     * 截至时间
     */
    private LocalDateTime stopTime;
    /**
     * 招募状态
     */
    private RecruitmentStatus status;
}