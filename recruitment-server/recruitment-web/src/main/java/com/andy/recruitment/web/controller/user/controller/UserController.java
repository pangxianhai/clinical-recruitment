package com.andy.recruitment.web.controller.user.controller;

import com.andy.recruitment.biz.organization.service.OrganizationDepartmentService;
import com.andy.recruitment.biz.organization.service.OrganizationService;
import com.andy.recruitment.biz.patient.service.PatientInfoService;
import com.andy.recruitment.biz.reference.service.ReferenceService;
import com.andy.recruitment.biz.researcher.service.ResearcherService;
import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.biz.weixin.service.WeiXinService;
import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.organization.response.OrganizationDepartmentRes;
import com.andy.recruitment.web.controller.organization.response.OrganizationRes;
import com.andy.recruitment.web.controller.organization.util.OrganizationDepartmentUtil;
import com.andy.recruitment.web.controller.organization.util.OrganizationUtil;
import com.andy.recruitment.web.controller.patient.util.PatientInfoUtil;
import com.andy.recruitment.web.controller.reference.util.ReferenceUtil;
import com.andy.recruitment.web.controller.researcher.util.ResearcherUtil;
import com.andy.recruitment.web.controller.user.request.WxLoginReq;
import com.andy.recruitment.web.controller.user.response.UserInfoDetailRes;
import com.andy.recruitment.web.controller.user.response.UserInfoRes;
import com.andy.recruitment.web.controller.user.response.WxLoginRes;
import com.andy.recruitment.web.controller.user.util.UserInfoUtil;
import com.soyoung.base.auth.LoginInfo;
import com.soyoung.base.context.ServletContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户controller接口
 *
 * @author 庞先海 2020-02-02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final PatientInfoService patientInfoService;

    private final ResearcherService researcherService;

    private final ReferenceService referenceService;

    private final OrganizationDepartmentService organizationDepartmentService;

    private final OrganizationService organizationService;

    private final WeiXinService weiXinService;

    @Autowired
    public UserController(UserService userService, PatientInfoService patientInfoService,
        ResearcherService researcherService, ReferenceService referenceService,
        OrganizationDepartmentService organizationDepartmentService, OrganizationService organizationService,
        WeiXinService weiXinService) {
        this.userService = userService;
        this.patientInfoService = patientInfoService;
        this.researcherService = researcherService;
        this.referenceService = referenceService;
        this.organizationDepartmentService = organizationDepartmentService;
        this.organizationService = organizationService;
        this.weiXinService = weiXinService;
    }

    @GetMapping("/phone/{phone:\\d+}")
    public UserInfoRes getUserByPhone(@PathVariable String phone) {
        UserInfoDO userInfoDo = this.userService.getUserInfoByPhone(phone);
        return UserInfoUtil.transformUserInfoRes(userInfoDo);
    }

    @GetMapping("/current")
    public UserInfoDetailRes getCurrentUser() {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        if (loginInfo == null) {
            return null;
        }
        UserInfoDO userInfoDo = this.userService.getUserInfoByUserId(loginInfo.getUserId());
        UserInfoDetailRes userDetailRes = new UserInfoDetailRes();
        BeanUtils.copyProperties(userInfoDo, userDetailRes);
        userDetailRes.setUserId(userInfoDo.getId());

        PatientInfoDO patientInfoDo = this.patientInfoService.getPatientByUserId(loginInfo.getUserId());
        userDetailRes.setPatientInfoRes(PatientInfoUtil.transformReferenceRes(patientInfoDo));

        ResearcherInfoDO researcherInfoDo = this.researcherService.getResearcherInfoByUserId(loginInfo.getUserId());
        userDetailRes.setResearcherInfoRes(ResearcherUtil.transformResearcherRes(researcherInfoDo));
        if (researcherInfoDo != null) {
            OrganizationDO organizationDo = organizationService.getOrganizationById(
                researcherInfoDo.getOrganizationId());
            OrganizationRes organizationRes = OrganizationUtil.transformOrganizationRes(organizationDo);
            userDetailRes.setOrganizationRes(organizationRes);

            OrganizationDepartmentDO departmentDo = organizationDepartmentService.getOrganizationDepartmentById(
                researcherInfoDo.getDepartmentId());
            OrganizationDepartmentRes departmentRes = OrganizationDepartmentUtil.transformOrganizationDepartmentRes(
                departmentDo);
            userDetailRes.setOrganizationDepartmentRes(departmentRes);
        }

        ReferenceInfoDO referenceInfoDo = this.referenceService.getReferenceByUserId(loginInfo.getUserId());
        userDetailRes.setReferenceInfoRes(ReferenceUtil.transformReferenceRes(referenceInfoDo));
        return userDetailRes;
    }

    @GetMapping("/login/wx")
    public String getWxLoginUrl(String redirectUrl) {
        return weiXinService.getWeiXinLoginUrl(redirectUrl);
    }

    @PostMapping("/login/wx")
    public WxLoginRes wxLogin(@RequestBody WxLoginReq wxLoginReq) {
        UserInfoDO userInfoDo = this.userService.wxLogin(wxLoginReq.getCode());
        if (userInfoDo.getId() != null) {
            LoginInfo loginInfo = this.userService.userInfoLogin(userInfoDo);

            WxLoginRes wxLoginRes = UserInfoUtil.transformWxLoginRes(userInfoDo);
            wxLoginRes.setUserId(loginInfo.getUserId());
            wxLoginRes.setToken(loginInfo.getToken());
            wxLoginRes.setUserName(loginInfo.getUserName());

            PatientInfoDO patientInfoDo = this.patientInfoService.getPatientByUserId(loginInfo.getUserId());
            wxLoginRes.setHasPatient(patientInfoDo != null);
            ResearcherInfoDO researcherInfoDo = this.researcherService.getResearcherInfoByUserId(loginInfo.getUserId());
            wxLoginRes.setHasResearcher(researcherInfoDo != null);
            ReferenceInfoDO referenceInfoDo = this.referenceService.getReferenceByUserId(loginInfo.getUserId());
            wxLoginRes.setHasReference(referenceInfoDo != null);
            return wxLoginRes;
        } else {
            return UserInfoUtil.transformWxLoginRes(userInfoDo);
        }
    }
}