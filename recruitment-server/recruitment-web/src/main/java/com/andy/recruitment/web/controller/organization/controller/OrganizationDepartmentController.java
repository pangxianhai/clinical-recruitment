package com.andy.recruitment.web.controller.organization.controller;

import com.andy.recruitment.biz.organization.service.OrganizationDepartmentService;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentQuery;
import com.andy.recruitment.web.controller.organization.request.OrganizationDepartmentAddReq;
import com.andy.recruitment.web.controller.organization.request.OrganizationDepartmentQueryReq;
import com.andy.recruitment.web.controller.organization.response.OrganizationDepartmentDetailRes;
import com.andy.recruitment.web.controller.organization.response.OrganizationDepartmentRes;
import com.andy.recruitment.web.controller.organization.util.OrganizationDepartmentUtil;
import com.andy.spring.auth.RoleType;
import com.andy.spring.context.ServletContext;
import com.andy.spring.converter.MyParameter;
import com.andy.spring.page.PageResult;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/organization")
public class OrganizationDepartmentController {

    private final OrganizationDepartmentService organizationDepartmentService;

    @Autowired
    public OrganizationDepartmentController(OrganizationDepartmentService organizationDepartmentService) {
        this.organizationDepartmentService = organizationDepartmentService;
    }

    @GetMapping("/department")
    public PageResult<OrganizationDepartmentDetailRes> listOrganizationDepartment(
        @MyParameter OrganizationDepartmentQueryReq queryReq) {
        OrganizationDepartmentQuery query = OrganizationDepartmentUtil.transformOrganizationDepartmentQuery(queryReq);
        PageResult<OrganizationDepartmentDO> pageResult = this.organizationDepartmentService.getOrganizationDepartment(
            query, queryReq.getPaginator());
        List<OrganizationDepartmentDetailRes> departmentDetailResList = OrganizationDepartmentUtil.transformOrganizationDepartmentDetailRes(
            pageResult.getData());
        return new PageResult<>(departmentDetailResList, pageResult.getPaginator());
    }

    @GetMapping("/{organizationId}/department")
    public List<OrganizationDepartmentRes> listOrganizationDepartment(@PathVariable Long organizationId) {
        List<OrganizationDepartmentDO> organizationDepartmentDoList = this.organizationDepartmentService.getOrganizationDepartmentByOrganization(
            organizationId);
        return OrganizationDepartmentUtil.transformOrganizationDepartmentRes(organizationDepartmentDoList);
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PostMapping("/department")
    public boolean addOrganizationDepartment(@RequestBody OrganizationDepartmentAddReq departmentAddReq) {
        OrganizationDepartmentDO organizationDepartmentDo = OrganizationDepartmentUtil.transformOrganizationDepartmentDo(
            departmentAddReq);
        this.organizationDepartmentService.addOrganizationDepartment(organizationDepartmentDo,
            ServletContext.getLoginInfo().getRealName());
        return true;
    }

}
