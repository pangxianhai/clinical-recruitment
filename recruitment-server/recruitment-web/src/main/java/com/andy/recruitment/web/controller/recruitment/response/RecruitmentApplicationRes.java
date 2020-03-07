package com.andy.recruitment.web.controller.recruitment.response;

import com.andy.recruitment.dao.recruitment.constant.RecruitmentApplicationStatus;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 申请记录
 *
 * @author 庞先海 2020-03-04
 */
@Data
public class RecruitmentApplicationRes implements Serializable {

    private static final long serialVersionUID = - 1993449057321323150L;

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
     * 选择的机构ID
     */
    private Long organizationId;
    /**
     * 选择的科室ID
     */
    private Long departmentId;
    /**
     * 患者用户ID
     */
    private Long patientUserId;
    /**
     * 推荐人用户ID
     */
    private Long referenceUserId;
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
    private List<ImageRes> diseaseImageList;
    /**
     * 申请状态
     */
    private RecruitmentApplicationStatus status;
    /**
     * 申请时间
     */
    private String applicationTime;
}
