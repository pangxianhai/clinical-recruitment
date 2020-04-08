package com.andy.recruitment.dao.reference.entity;

import com.andy.recruitment.common.reference.constant.ReferenceRole;
import com.andy.recruitment.common.reference.constant.ReferenceStatus;
import com.andy.spring.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推荐人信息
 *
 * @author 庞先海 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ReferenceInfoDO extends BaseDO {

    private static final long serialVersionUID = - 1171395858993872274L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 医院ID
     */
    private Long hospitalId;
    /**
     * 科室ID
     */
    private Long departmentId;
    /**
     * 执业机构
     */
    private String medicalInstitution;
    /**
     * 执业类别
     */
    private String medicalCategory;
    /**
     * 备注
     */
    private String remark;
    /**
     * 推荐人状态
     */
    private ReferenceStatus status;
    /**
     * 推荐人角色
     */
    private ReferenceRole referenceRole;
}
