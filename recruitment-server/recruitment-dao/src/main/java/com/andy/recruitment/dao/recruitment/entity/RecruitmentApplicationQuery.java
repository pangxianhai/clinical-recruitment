package com.andy.recruitment.dao.recruitment.entity;

import com.andy.recruitment.common.recruitment.constant.RecruitmentApplicationStatus;
import java.io.Serializable;
import lombok.Data;

/**
 * 招募申请信息查询参数
 *
 * @author 庞先海 2019-01-24
 */
@Data
public class RecruitmentApplicationQuery implements Serializable {

    private static final long serialVersionUID = 4459042633156736860L;
    /**
     * 申请记录ID
     */
    private Long applicationId;
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
    private Long hospitalId;
    /**
     * 选择的科室ID
     */
    private Long departmentId;
    /**
     * 申请状态
     */
    private RecruitmentApplicationStatus status;
    /**
     * 招募登记编号
     */
    private String recruitmentRegisterCode;
}
