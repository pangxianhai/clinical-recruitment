package com.andy.recruitment.web.controller.patient.controller;

import com.andy.recruitment.biz.patient.service.PatientInfoService;
import com.andy.recruitment.dao.patient.constant.PatientStatus;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.patient.entity.PatientQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.patient.request.PatientAddReq;
import com.andy.recruitment.web.controller.patient.request.PatientQueryReq;
import com.andy.recruitment.web.controller.patient.response.PatientInfoRes;
import com.andy.recruitment.web.controller.patient.util.PatientInfoUtil;
import com.soyoung.base.auth.LoginInfo;
import com.soyoung.base.context.ServletContext;
import com.soyoung.base.converter.MyParameter;
import com.soyoung.base.page.PageResult;
import java.util.List;
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

    @Autowired
    public PatientController(PatientInfoService patientInfoService) {
        this.patientInfoService = patientInfoService;
    }

    @RequiresUser
    @PostMapping
    public boolean addPatient(@RequestBody PatientAddReq patientAddReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        PatientInfoDO patientInfoDo = PatientInfoUtil.transformPatientInfoDo(patientAddReq);
        patientInfoDo.setStatus(PatientStatus.NORMAL);
        UserInfoDO userInfoDo = PatientInfoUtil.transformUserInfo(patientAddReq);
        this.patientInfoService.registerPatient(patientInfoDo, userInfoDo, loginInfo.getRealName());
        return true;
    }

    @GetMapping
    public PageResult<PatientInfoRes> getPatient(@MyParameter PatientQueryReq queryReq) {
        PatientQuery query = PatientInfoUtil.transformPatientQuery(queryReq);
        PageResult<PatientInfoDO> pageResult = this.patientInfoService.getPatient(query, queryReq.getPaginator());
        List<PatientInfoRes> patientInfoResList = PatientInfoUtil.transformReferenceRes(pageResult.getData());
        return new PageResult<>(patientInfoResList, pageResult.getPaginator());
    }

    @GetMapping("/{patientId:\\d+}")
    public PatientInfoRes getPatient(@PathVariable Long patientId) {
        PatientInfoDO patientInfoDo = this.patientInfoService.getPatient(patientId);
        return PatientInfoUtil.transformReferenceRes(patientInfoDo);
    }

    @RequiresUser
    @PutMapping("/{patientId:\\d+}")
    public boolean updatePatientId(@PathVariable Long patientId, @RequestBody PatientAddReq patientAddReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        PatientInfoDO patientInfoDo = PatientInfoUtil.transformPatientInfoDo(patientAddReq);
        patientInfoDo.setId(patientId);
        UserInfoDO userInfoDo = PatientInfoUtil.transformUserInfo(patientAddReq);
        this.patientInfoService.updatePatient(patientInfoDo, userInfoDo, loginInfo.getRealName());
        return true;
    }
}
