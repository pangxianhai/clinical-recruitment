package com.andy.recruitment.web.controller.recruitment.response;

import com.andy.recruitment.web.controller.organization.response.OrganizationDepartmentDetailRes;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 招募详情信息
 *
 * @author 庞先海 2020-01-31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RecruitmentInfoDetailRes extends RecruitmentInfoRes {

    private static final long serialVersionUID = - 3370225946705834051L;

    /**
     * 科室信息
     */
    private List<OrganizationDepartmentDetailRes> departmentDetailResList;
}
