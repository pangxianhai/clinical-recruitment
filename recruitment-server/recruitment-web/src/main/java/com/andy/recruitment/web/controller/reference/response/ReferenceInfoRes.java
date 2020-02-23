package com.andy.recruitment.web.controller.reference.response;

import com.andy.recruitment.dao.reference.constant.ReferenceStatus;
import com.andy.recruitment.web.controller.user.response.UserInfoRes;
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
     * 用户ID
     */
    private Long userId;
    /**
     * 省ID
     */
    private Long provinceId;
    /**
     * 城市ID
     */
    private Long cityId;
    /**
     * 区ID
     */
    private Long districtId;
    /**
     * 推荐人所在地址
     */
    private String address;
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
