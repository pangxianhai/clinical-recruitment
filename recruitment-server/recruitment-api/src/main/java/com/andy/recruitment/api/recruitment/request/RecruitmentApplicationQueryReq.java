package com.andy.recruitment.api.recruitment.request;

import com.andy.spring.base.BaseQueryReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 招募申请记录查询参数
 *
 * @author 庞先海 2020-03-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RecruitmentApplicationQueryReq extends BaseQueryReq {

    private static final long serialVersionUID = 4897345430894789130L;
    /**
     * 招募ID
     */
    private Long recruitmentId;
    /**
     * 患者用户ID
     */
    private Long patientUserId;
    /**
     * 推荐人用户ID
     */
    private Long referenceUserId;
    /**
     * 选择的机构ID
     */
    private Long organizationId;
    /**
     * 选择的科室ID
     */
    private Long departmentId;
    /**
     * 申请状态
     */
    private Integer status;
    /**
     * 招募登记编号
     */
    private String recruitmentRegisterCode;
}
