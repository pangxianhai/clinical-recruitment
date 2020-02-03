package com.andy.recruitment.web.controller.researcher.response;

import com.andy.recruitment.dao.researcher.constant.ResearcherStatus;
import com.andy.recruitment.web.controller.organization.response.OrganizationDepartmentRes;
import com.andy.recruitment.web.controller.organization.response.OrganizationRes;
import com.andy.recruitment.web.controller.user.response.UserInfoRes;
import java.io.Serializable;
import lombok.Data;

/**
 * 研究员信息
 *
 * @author 庞先海 2020-01-31
 */
@Data
public class ResearcherInfoRes implements Serializable {

    private static final long serialVersionUID = - 1899971211991016838L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户信息
     */
    private UserInfoRes userInfoRes;
    /**
     * 所属机构id
     */
    private Long organizationId;
    /**
     * 机构信息
     */
    private OrganizationRes organizationRes;
    /**
     * 所属科室ID
     */
    private Long departmentId;
    /**
     * 机构科室信息
     */
    private OrganizationDepartmentRes organizationDepartmentRes;
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
     * 研究员状态
     */
    private ResearcherStatus status;
}
