package com.andy.recruitment.api.user.response;

import com.andy.recruitment.api.organization.response.OrganizationDepartmentRes;
import com.andy.recruitment.api.organization.response.OrganizationRes;
import com.andy.recruitment.api.patient.response.PatientInfoRes;
import com.andy.recruitment.api.reference.response.ReferenceInfoRes;
import com.andy.recruitment.api.researcher.response.ResearcherInfoRes;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户详情信息
 *
 * @author 庞先海 2020-02-17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfoDetailRes extends UserInfoRes {

    private static final long serialVersionUID = 6746332876684392310L;

    /**
     * 患者信息
     */
    private PatientInfoRes patientInfoRes;

    /**
     * 推荐人信息
     */
    private ReferenceInfoRes referenceInfoRes;

    /**
     * 研究员信息
     */
    private ResearcherInfoRes researcherInfoRes;

    /**
     * 机构信息
     */
    private OrganizationRes organizationRes;
    /**
     * 机构科室信息
     */
    private OrganizationDepartmentRes organizationDepartmentRes;
}
