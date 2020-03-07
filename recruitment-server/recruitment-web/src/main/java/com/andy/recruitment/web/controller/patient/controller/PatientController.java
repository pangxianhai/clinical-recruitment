package com.andy.recruitment.web.controller.patient.controller;

import com.andy.recruitment.biz.patient.service.PatientInfoService;
import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.common.patient.constant.PatientStatus;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.patient.entity.PatientQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.patient.request.PatientAddReq;
import com.andy.recruitment.web.controller.patient.request.PatientQueryReq;
import com.andy.recruitment.web.controller.patient.request.PatientRegisterReq;
import com.andy.recruitment.web.controller.patient.response.PatientInfoDetailRes;
import com.andy.recruitment.web.controller.patient.util.PatientInfoUtil;
import com.andy.spring.auth.LoginInfo;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 患者 controller 接口
 *
 * @author 庞先海 2020-02-16
 */
@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientInfoService patientInfoService;

    private final UserService userService;

    @Autowired
    public PatientController(PatientInfoService patientInfoService, UserService userService) {
        this.patientInfoService = patientInfoService;
        this.userService = userService;
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PostMapping
    public boolean addPatient(@RequestBody PatientAddReq patientAddReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        PatientInfoDO patientInfoDo = PatientInfoUtil.transformPatientInfoDo(patientAddReq);
        UserInfoDO userInfoDo = PatientInfoUtil.transformUserInfo(patientAddReq);
        this.patientInfoService.registerPatient(patientInfoDo, userInfoDo, loginInfo.getRealName());
        return true;
    }

    @PostMapping("register")
    public LoginInfo registerPatient(@RequestBody PatientRegisterReq patientRegisterReq) {
        PatientInfoDO patientInfoDo = PatientInfoUtil.transformPatientInfoDo(patientRegisterReq);
        UserInfoDO userInfoDo = PatientInfoUtil.transformUserInfo(patientRegisterReq);
        this.patientInfoService.registerPatient(patientInfoDo, userInfoDo, patientRegisterReq.getName());
        return this.userService.userInfoLogin(userInfoDo);
    }

    @GetMapping
    public PageResult<PatientInfoDetailRes> getPatient(@MyParameter PatientQueryReq queryReq) {
        PatientQuery query = PatientInfoUtil.transformPatientQuery(queryReq);
        PageResult<PatientInfoDO> pageResult = this.patientInfoService.getPatient(query, queryReq.getPaginator());
        List<PatientInfoDetailRes> patientInfoDetailResList = PatientInfoUtil.transformReferenceDetailRes(
            pageResult.getData());
        return new PageResult<>(patientInfoDetailResList, pageResult.getPaginator());
    }

    @GetMapping("/{patientId:\\d+}")
    public PatientInfoDetailRes getPatient(@PathVariable Long patientId) {
        PatientInfoDO patientInfoDo = this.patientInfoService.getPatient(patientId);
        return PatientInfoUtil.transformReferenceDetailRes(patientInfoDo);
    }

    @RequiresUser
    @PutMapping("/{patientId:\\d+}")
    public boolean updatePatient(@PathVariable Long patientId, @RequestBody PatientAddReq patientAddReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        PatientInfoDO patientInfoDo = PatientInfoUtil.transformPatientInfoDo(patientAddReq);
        patientInfoDo.setId(patientId);
        UserInfoDO userInfoDo = PatientInfoUtil.transformUserInfo(patientAddReq);
        this.patientInfoService.updatePatient(patientInfoDo, userInfoDo, loginInfo.getRealName());
        return true;
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PutMapping("/{patientId:\\d+}/freeze")
    public boolean freezePatient(@PathVariable Long patientId) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        this.patientInfoService.updatePatientStatus(patientId, PatientStatus.FREEZE, loginInfo.getRealName());
        return true;
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PutMapping("/{patientId:\\d+}/unfreeze")
    public boolean unfreezePatient(@PathVariable Long patientId) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        this.patientInfoService.updatePatientStatus(patientId, PatientStatus.NORMAL, loginInfo.getRealName());
        return true;
    }
}
