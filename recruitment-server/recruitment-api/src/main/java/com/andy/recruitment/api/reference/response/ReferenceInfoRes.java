package com.andy.recruitment.api.reference.response;

import com.andy.recruitment.common.reference.constant.ReferenceRole;
import com.andy.recruitment.common.reference.constant.ReferenceStatus;
import java.io.Serializable;
import lombok.Data;

/**
 * 推荐人信息
 *
 * @author 庞先海 2020-02-15
 */
@Data
public class ReferenceInfoRes implements Serializable {

    private static final long serialVersionUID = - 3642147770564844962L;
    /**
     * 推荐人ID
     */
    private Long referenceId;
    /**
     * 医院ID
     */
    private Long hospitalId;
    /**
     * 科室ID
     */
    private Long departmentId;
    /**
     * 角色
     */
    private ReferenceRole referenceRole;
    /**
     * 用户ID
     */
    private Long userId;
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
}
