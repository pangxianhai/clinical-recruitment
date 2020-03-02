package com.andy.recruitment.dao.recruitment.entity;

import com.andy.recruitment.dao.recruitment.constant.RecruitmentApplicationStatus;
import com.andy.spring.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 招募信息申请信息DO
 *
 * @author 庞先海 2019-01-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RecruitmentApplicationDO extends BaseDO {

    private static final long serialVersionUID = - 6130304493809730015L;
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
    private String diseaseImage;
    /**
     * 申请状态
     */
    private RecruitmentApplicationStatus status;

}
