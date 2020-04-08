package com.andy.recruitment.web.controller.user.controller;

import com.andy.recruitment.api.hospital.response.DepartmentDetailRes;
import com.andy.recruitment.api.patient.response.PatientInfoRes;
import com.andy.recruitment.api.reference.response.ReferenceInfoRes;
import com.andy.recruitment.api.user.request.WxLoginReq;
import com.andy.recruitment.api.user.response.UserInfoDetailRes;
import com.andy.recruitment.api.user.response.UserInfoRes;
import com.andy.recruitment.api.user.response.WxLoginRes;
import com.andy.recruitment.biz.hospital.service.DepartmentService;
import com.andy.recruitment.biz.hospital.service.HospitalService;
import com.andy.recruitment.biz.patient.service.PatientInfoService;
import com.andy.recruitment.biz.reference.service.ReferenceService;
import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.biz.weixin.service.WeiXinService;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.user.util.UserInfoUtil;
import com.andy.spring.auth.LoginInfo;
import com.andy.spring.context.ServletContext;
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

    private final ReferenceService referenceService;

    private final DepartmentService departmentService;

    private final HospitalService hospitalService;

    private final WeiXinService weiXinService;

    @Autowired
    public UserController(UserService userService, PatientInfoService patientInfoService,
        ReferenceService referenceService, DepartmentService departmentService, HospitalService hospitalService,
        WeiXinService weiXinService) {
        this.userService = userService;
        this.patientInfoService = patientInfoService;
        this.referenceService = referenceService;
        this.departmentService = departmentService;
        this.hospitalService = hospitalService;
        this.weiXinService = weiXinService;
    }

    @GetMapping("/phone/{phone:\\d+}")
    public UserInfoRes getUserByPhone(@PathVariable String phone) {
        return this.userService.getUserInfoByPhone(phone);
    }

    @GetMapping("/current")
    public UserInfoDetailRes getCurrentUser() {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        if (loginInfo == null) {
            return null;
        }
        UserInfoDO userInfoDo = this.userService.getUserDoByUserId(loginInfo.getUserId());
        UserInfoDetailRes userDetailRes = new UserInfoDetailRes();
        BeanUtils.copyProperties(userInfoDo, userDetailRes);
        userDetailRes.setUserId(userInfoDo.getId());

        PatientInfoRes patientInfoRes = this.patientInfoService.getPatientByUserId(loginInfo.getUserId());
        userDetailRes.setPatientInfoRes(patientInfoRes);

        ReferenceInfoRes referenceInfoRes = this.referenceService.getReferenceByUserId(loginInfo.getUserId());
        userDetailRes.setReferenceInfoRes(referenceInfoRes);

        DepartmentDetailRes departmentDetailRes = departmentService.getDepartmentById(
            referenceInfoRes.getDepartmentId());
        userDetailRes.setDepartmentDetailRes(departmentDetailRes);

        return userDetailRes;
    }

    @GetMapping("/{userId:\\d+}")
    public UserInfoRes getUserInfo(@PathVariable Long userId) {
        return this.userService.getUserInfoByUserId(userId);
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

            PatientInfoDO patientInfoDo = this.patientInfoService.getPatientDoByUserId(loginInfo.getUserId());
            wxLoginRes.setHasPatient(patientInfoDo != null);

            ReferenceInfoDO referenceInfoDo = this.referenceService.getReferenceDoByUserId(loginInfo.getUserId());
            wxLoginRes.setHasReference(referenceInfoDo != null);
            return wxLoginRes;
        } else {
            return UserInfoUtil.transformWxLoginRes(userInfoDo);
        }
    }
}