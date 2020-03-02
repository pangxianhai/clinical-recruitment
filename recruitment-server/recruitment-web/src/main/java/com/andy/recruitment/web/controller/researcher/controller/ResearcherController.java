package com.andy.recruitment.web.controller.researcher.controller;

import com.andy.recruitment.biz.organization.service.OrganizationDepartmentService;
import com.andy.recruitment.biz.researcher.service.ResearcherService;
import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.researcher.constant.ResearcherStatus;
import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.researcher.entity.ResearcherQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.researcher.request.ResearcherAddReq;
import com.andy.recruitment.web.controller.researcher.request.ResearcherQueryReq;
import com.andy.recruitment.web.controller.researcher.request.ResearcherRegisterReq;
import com.andy.recruitment.web.controller.researcher.response.ResearcherInfoDetailRes;
import com.andy.recruitment.web.controller.researcher.util.ResearcherUtil;
import com.andy.spring.auth.LoginInfo;
import com.andy.spring.auth.RoleType;
import com.andy.spring.context.ServletContext;
import com.andy.spring.converter.MyParameter;
import com.andy.spring.page.PageResult;
import com.andy.spring.util.asserts.AssertUtil;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 研究员 controller 接口
 *
 * @author 庞先海 2020-01-31
 */
@RestController
@RequestMapping("/researcher")
public class ResearcherController {

    private final ResearcherService researcherService;

    private final OrganizationDepartmentService organizationDepartmentService;

    private final UserService userService;

    @Autowired
    public ResearcherController(ResearcherService researcherService,
        OrganizationDepartmentService organizationDepartmentService, UserService userService) {
        this.researcherService = researcherService;
        this.organizationDepartmentService = organizationDepartmentService;
        this.userService = userService;
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @GetMapping
    public PageResult<ResearcherInfoDetailRes> listResearcher(@MyParameter ResearcherQueryReq queryReq) {
        ResearcherQuery query = ResearcherUtil.transformResearcherQuery(queryReq);
        PageResult<ResearcherInfoDO> pageResult = this.researcherService.getResearcherInfo(query,
            queryReq.getPaginator());
        List<ResearcherInfoDetailRes> researcherInfoDetailResList = ResearcherUtil.transformResearcherDetailRes(
            pageResult.getData());
        return new PageResult<>(researcherInfoDetailResList, pageResult.getPaginator());
    }

    @RequiresUser
    @GetMapping("/{researcherId:\\d+}")
    public ResearcherInfoDetailRes getResearcherDetail(@PathVariable Long researcherId) {
        ResearcherInfoDO researcherInfoDo = this.researcherService.getResearcherInfoByResearchId(researcherId);
        return ResearcherUtil.transformResearcherDetailRes(researcherInfoDo);
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PostMapping
    public boolean addResearcher(@RequestBody ResearcherAddReq researcherAddReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        ResearcherInfoDO researcherInfoDo = ResearcherUtil.transformResearcherInfo(researcherAddReq);
        researcherInfoDo.setStatus(ResearcherStatus.NORMAL);
        UserInfoDO userInfoDo = ResearcherUtil.transformUserInfo(researcherAddReq);
        this.researcherService.registerResearcher(researcherInfoDo, userInfoDo, loginInfo.getRealName());
        return true;
    }

    @RequiresUser
    @PostMapping("register")
    public LoginInfo registerResearcher(@RequestBody ResearcherRegisterReq researcherRegisterReq) {
        ResearcherInfoDO researcherInfoDo = ResearcherUtil.transformResearcherInfo(researcherRegisterReq);
        OrganizationDepartmentDO departmentDo = organizationDepartmentService.getOrganizationDepartmentById(
            researcherRegisterReq.getDepartmentId());
        AssertUtil.assertNull(departmentDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ORGANIZATION_DEPARTMENT_NOT_EXIST);
        });
        researcherInfoDo.setOrganizationId(departmentDo.getOrganizationId());
        researcherInfoDo.setStatus(ResearcherStatus.NON_EXAMINE);
        UserInfoDO userInfoDo = ResearcherUtil.transformUserInfo(researcherRegisterReq);
        this.researcherService.registerResearcher(researcherInfoDo, userInfoDo, researcherRegisterReq.getName());
        return this.userService.userInfoLogin(userInfoDo);
    }

    @RequiresUser
    @PutMapping("/{researcherId:\\d+}")
    public boolean updateResearcher(@PathVariable Long researcherId, @RequestBody ResearcherAddReq researcherAddReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        ResearcherInfoDO researcherInfoDo = ResearcherUtil.transformResearcherInfo(researcherAddReq);
        researcherInfoDo.setId(researcherId);
        //所属科室暂不允许修改
        researcherInfoDo.setOrganizationId(null);
        researcherInfoDo.setDepartmentId(null);
        UserInfoDO userInfoDo = ResearcherUtil.transformUserInfo(researcherAddReq);
        this.researcherService.updateResearcher(researcherInfoDo, userInfoDo, loginInfo.getRealName());
        return true;
    }
}
