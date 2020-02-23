package com.andy.recruitment.web.controller.researcher.response;

import com.andy.recruitment.web.controller.organization.response.OrganizationDepartmentRes;
import com.andy.recruitment.web.controller.organization.response.OrganizationRes;
import com.andy.recruitment.web.controller.user.response.UserInfoRes;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 研究员详细信息
 *
 * @author 庞先海 2020-02-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResearcherInfoDetailRes extends ResearcherInfoRes {

    private static final long serialVersionUID = - 1251299913792934927L;
    /**
     * 用户信息
     */
    private UserInfoRes userInfoRes;
    /**
     * 机构信息
     */
    private OrganizationRes organizationRes;
    /**
     * 机构科室信息
     */
    private OrganizationDepartmentRes organizationDepartmentRes;
}
