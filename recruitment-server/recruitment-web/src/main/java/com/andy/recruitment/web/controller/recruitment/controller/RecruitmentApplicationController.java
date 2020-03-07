package com.andy.recruitment.web.controller.recruitment.controller;

import com.andy.recruitment.api.recruitment.request.RecruitmentApplicationAddReq;
import com.andy.recruitment.api.recruitment.request.RecruitmentApplicationQueryReq;
import com.andy.recruitment.api.recruitment.response.RecruitmentApplicationDetailRes;
import com.andy.recruitment.biz.organization.service.OrganizationDepartmentService;
import com.andy.recruitment.biz.patient.service.PatientInfoService;
import com.andy.recruitment.biz.recruitment.service.RecruitmentApplicationService;
import com.andy.recruitment.biz.recruitment.service.RecruitmentService;
import com.andy.recruitment.biz.reference.service.ReferenceService;
import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.common.recruitment.constant.RecruitmentApplicationStatus;
import com.andy.recruitment.common.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationQuery;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.recruitment.util.RecruitmentUtil;
import com.andy.spring.auth.LoginInfo;
import com.andy.spring.auth.RoleType;
import com.andy.spring.context.ServletContext;
import com.andy.spring.converter.MyParameter;
import com.andy.spring.page.PageResult;
import com.andy.spring.util.asserts.AssertUtil;
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
 * 招募项目申请记录 controller 接口
 *
 * @author 庞先海 2020-03-01
 */
@RestController
@RequestMapping("/recruitment")
public class RecruitmentApplicationController {

    private final RecruitmentApplicationService recruitmentApplicationService;

    private final PatientInfoService patientInfoService;

    private final ReferenceService referenceService;

    private final RecruitmentService recruitmentService;

    private final OrganizationDepartmentService organizationDepartmentService;

    @Autowired
    public RecruitmentApplicationController(RecruitmentApplicationService recruitmentApplicationService,
        PatientInfoService patientInfoService, ReferenceService referenceService, RecruitmentService recruitmentService,
        OrganizationDepartmentService organizationDepartmentService) {
        this.recruitmentApplicationService = recruitmentApplicationService;
        this.patientInfoService = patientInfoService;
        this.referenceService = referenceService;
        this.recruitmentService = recruitmentService;
        this.organizationDepartmentService = organizationDepartmentService;
    }

    @RequiresUser
    @RequiresRoles(RoleType.CUSTOMER_CODE + "")
    @PostMapping("/{recruitmentId:\\d+}/application")
    public boolean recruitmentApplication(@PathVariable Long recruitmentId,
        @RequestBody RecruitmentApplicationAddReq applicationAddReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        PatientInfoDO patientInfoDo = RecruitmentUtil.buildPatientInfoDo(applicationAddReq);
        UserInfoDO userInfoDo = RecruitmentUtil.buildUserInfoDo(applicationAddReq);
        Long patientUserId = this.patientInfoService.registerPatient(patientInfoDo, userInfoDo,
            loginInfo.getRealName());
        ReferenceInfoDO referenceInfoDo = referenceService.getReferenceByPhone(applicationAddReq.getReferencePhone());
        RecruitmentInfoDO recruitmentInfoDo = recruitmentService.getRecruitmentInfo(recruitmentId);
        AssertUtil.assertNull(recruitmentInfoDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_NOT_EXIST);
        });
        AssertUtil.assertTrue(RecruitmentStatus.IN_PROCESS.equals(recruitmentInfoDo.getStatus()), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_NOT_IN_PROCESS);
        });
        OrganizationDepartmentDO organizationDepartmentDo = organizationDepartmentService.getOrganizationDepartmentById(
            applicationAddReq.getDepartmentId());
        AssertUtil.assertNull(organizationDepartmentDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ORGANIZATION_DEPARTMENT_NOT_EXIST);
        });

        RecruitmentApplicationDO applicationDo = RecruitmentUtil.transformRecruitmentApplicationDo(applicationAddReq,
            recruitmentInfoDo);
        applicationDo.setPatientUserId(patientUserId);
        if (referenceInfoDo != null) {
            applicationDo.setReferenceUserId(referenceInfoDo.getUserId());
        }
        applicationDo.setDepartmentId(organizationDepartmentDo.getId());
        applicationDo.setOrganizationId(organizationDepartmentDo.getOrganizationId());
        applicationDo.setStatus(RecruitmentApplicationStatus.NOT_ACCEDE);
        this.recruitmentApplicationService.addRecruitmentApplication(applicationDo, loginInfo.getRealName());
        return true;
    }

    @RequiresUser
    @GetMapping("/application")
    public PageResult<RecruitmentApplicationDetailRes> getRecruitmentApplication(
        @MyParameter RecruitmentApplicationQueryReq queryReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        RecruitmentApplicationQuery query = RecruitmentUtil.transformRecruitmentApplicationQuery(queryReq);
        if (RoleType.CUSTOMER.equals(loginInfo.getRoleType())) {
            if (query.getPatientUserId() == null && query.getReferenceUserId() == null
                && query.getDepartmentId() == null) {
                query.setPatientUserId(loginInfo.getUserId());
            }
        }
        return this.recruitmentApplicationService.getRecruitmentApplicationInfo(query, queryReq.getPaginator());
    }

    @RequiresUser
    @GetMapping("/application/{applicationId:\\d+}")
    public RecruitmentApplicationDetailRes getRecruitmentApplication(@PathVariable Long applicationId) {
        return this.recruitmentApplicationService.getRecruitmentApplicationInfo(applicationId);
    }
}
