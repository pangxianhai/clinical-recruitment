package com.andy.recruitment.web.controller.organization.controller;

import com.andy.recruitment.biz.organization.service.OrganizationDepartmentService;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentQuery;
import com.andy.recruitment.web.controller.organization.request.OrganizationDepartmentAddReq;
import com.andy.recruitment.web.controller.organization.request.OrganizationDepartmentQueryReq;
import com.andy.recruitment.web.controller.organization.response.OrganizationDepartmentDetailRes;
import com.andy.recruitment.web.controller.organization.util.OrganizationDepartmentUtil;
import com.soyoung.base.auth.RoleType;
import com.soyoung.base.context.ServletContext;
import com.soyoung.base.converter.MyParameter;
import com.soyoung.base.page.PageResult;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构科室
 *
 * @author 庞先海 2020-01-31
 */
@RestController
@RequestMapping("/organization/department")
public class OrganizationDepartmentController {

    private final OrganizationDepartmentService organizationDepartmentService;

    @Autowired
    public OrganizationDepartmentController(OrganizationDepartmentService organizationDepartmentService) {
        this.organizationDepartmentService = organizationDepartmentService;
    }

    @GetMapping
    public PageResult<OrganizationDepartmentDetailRes> listOrganizationDepartment(
        @MyParameter OrganizationDepartmentQueryReq queryReq) {
        OrganizationDepartmentQuery query = OrganizationDepartmentUtil.transformOrganizationDepartmentQuery(queryReq);
        PageResult<OrganizationDepartmentDO> pageResult = this.organizationDepartmentService.getOrganizationDepartment(
            query, queryReq.getPaginator());
        List<OrganizationDepartmentDetailRes> departmentDetailResList = OrganizationDepartmentUtil.transformOrganizationDepartmentDetailRes(
            pageResult.getData());
        return new PageResult<>(departmentDetailResList, pageResult.getPaginator());
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PostMapping
    public boolean addOrganizationDepartment(@RequestBody OrganizationDepartmentAddReq departmentAddReq) {
        OrganizationDepartmentDO organizationDepartmentDo = OrganizationDepartmentUtil.transformOrganizationDepartmentDo(
            departmentAddReq);
        this.organizationDepartmentService.addOrganizationDepartment(organizationDepartmentDo,
            ServletContext.getLoginInfo().getRealName());
        return true;
    }

}
