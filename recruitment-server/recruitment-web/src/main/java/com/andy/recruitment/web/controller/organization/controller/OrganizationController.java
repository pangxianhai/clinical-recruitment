package com.andy.recruitment.web.controller.organization.controller;

import com.andy.recruitment.biz.organization.service.OrganizationService;
import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationQuery;
import com.andy.recruitment.web.controller.organization.request.OrganizationAddReq;
import com.andy.recruitment.web.controller.organization.request.OrganizationQueryReq;
import com.andy.recruitment.web.controller.organization.response.OrganizationRes;
import com.andy.recruitment.web.controller.organization.util.OrganizationUtil;
import com.soyoung.base.auth.RoleType;
import com.soyoung.base.context.ServletContext;
import com.soyoung.base.converter.MyParameter;
import com.soyoung.base.page.PageResult;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构controller接口
 *
 * @author 庞先海 2020-01-30
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;

    }

    @GetMapping
    public PageResult<OrganizationRes> listOrganization(@MyParameter OrganizationQueryReq queryReq) {
        OrganizationQuery query = OrganizationUtil.transformOrganizationQuery(queryReq);
        PageResult<OrganizationDO> pageResult = this.organizationService.getOrganization(query,
            queryReq.getPaginator());
        return new PageResult<>(OrganizationUtil.transformOrganizationRes(pageResult.getData()),
            pageResult.getPaginator());
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PostMapping
    public boolean addOrganization(@RequestBody OrganizationAddReq addReq) {
        OrganizationDO organizationDo = OrganizationUtil.transformOrganization(addReq);
        this.organizationService.addOrganization(organizationDo, ServletContext.getLoginInfo().getRealName());
        return true;
    }
}
