package com.andy.recruitment.api.recruitment.request;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    @NotBlank
    @Length(max = 32)
    private String registerCode;
    /**
     * 标题
     */
    @NotBlank
    @Length(max = 32)
    private String title;
    /**
     * 类目
     */
    @NotNull
    private Integer category;
    /**
     * 试验分期
     */
    @NotBlank
    @Length(max = 32)
    private String practiceStages;
    /**
     * 适应症状
     */
    @NotBlank
    @Length(max = 32)
    private String indication;
    /**
     * 药物名称
     */
    @NotBlank
    @Length(max = 1024)
    private String drugName;
    /**
     * 药物类型
     */
    @NotBlank
    @Length(max = 512)
    private String drugType;
    /**
     * 招募人数
     */
    @NotNull
    private Integer recruitmentNumber;
    /**
     * 简介
     */
    @NotBlank
    @Length(max = 10000)
    private String introduction;
    /**
     * 治疗方案
     */
    @NotBlank
    @Length(max = 10000)
    private String treatmentPlan;
    /**
     * 初筛要点
     */
    @Length(max = 10000)
    private String screeningStandard;
    /**
     * 入排标准
     */
    @NotBlank
    @Length(max = 10000)
    private String entryCriteria;

    /**
     * 患者权益
     */
    @NotBlank
    @Length(max = 10000)
    private String patientRights;
    /**
     * 启始时间
     */
    @NotBlank
    @Length(max = 32)
    private String startTime;
    /**
     * 截至时间
     */
    @NotBlank
    @Length(max = 32)
    private String stopTime;
    /**
     * 申办方
     */
    @NotBlank
    @Length(max = 64)
    private String bidParty;
    /**
     * 研究机构科室列表
     * organizationDepartmentList的每项为列表 第一个机构ID 第二个科室ID
     */
    @NotNull
    private List<List<Long>> organizationDepartmentList;

}
