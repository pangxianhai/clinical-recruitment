package com.andy.recruitment.web.controller.patient.webservice;

import com.andy.recruitment.patient.PatientAO;
import com.andy.recruitment.patient.model.PatientInfo;
import com.andy.recruitment.patient.model.PatientQueryParam;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.Gender;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.patient.request.PatientAddRQ;
import com.andy.recruitment.web.controller.patient.request.PatientQueryRQ;
import com.andy.recruitment.web.controller.patient.response.PatientVO;
import com.andy.recruitment.web.controller.patient.util.PatientUtil;
import com.andy.recruitment.web.controller.user.util.UserUtil;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.commons.page.PageResult;
import com.xgimi.context.ServletContext;
import com.xgimi.converter.MyParameter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 患者webservice
 *
 * @author 庞先海 2019-01-22
 */
@RestController
@RequestMapping("/patient")
public class PatientWebservice {

    private final PatientAO patientAO;

    private final RegionAO regionAO;

    private final UserAO userAO;

    @Autowired
    public PatientWebservice(PatientAO patientAO, RegionAO regionAO, UserAO userAO) {
        this.patientAO = patientAO;
        this.regionAO = regionAO;
        this.userAO = userAO;
    }

    @RequestMapping(value = "/register.json", method = RequestMethod.POST)
    public boolean register(@RequestBody PatientAddRQ patientAddRQ) {
        UserInfo userInfo = this.userAO.getUserInfoByPhone(patientAddRQ.getPhone());
        UserInfo addUserInfo = PatientUtil.transformUserInfo(patientAddRQ);
        if (null == userInfo) {
            Long userId = this.userAO.addUserInfo(addUserInfo, patientAddRQ.getName());
            addUserInfo.setUserId(userId);
            PatientInfo patientInfo = PatientUtil.transformPatientInfo(patientAddRQ, regionAO);
            patientInfo.setUserId(addUserInfo.getUserId());
            this.patientAO.addPatientInfo(patientInfo, patientAddRQ.getName());
        } else {
            addUserInfo.setUserId(userInfo.getUserId());
            this.userAO.updateUserInfo(addUserInfo, addUserInfo.getRealName());
            PatientInfo existPatient = this.patientAO.getPatientInfoByUserId(addUserInfo.getUserId());
            PatientInfo patientInfo = PatientUtil.transformPatientInfo(patientAddRQ, regionAO);
            if (null == existPatient) {
                patientInfo.setUserId(addUserInfo.getUserId());
                this.patientAO.addPatientInfo(patientInfo, patientAddRQ.getName());
                this.patientAO.addPatientInfo(patientInfo, patientAddRQ.getName());
            } else {
                patientInfo.setPatientId(existPatient.getPatientId());
                this.patientAO.updatePatientInfo(patientInfo, patientAddRQ.getName());
            }
        }
        this.userAO.saveUserInfoCookie(addUserInfo, ServletContext.getResponse());
        return true;
    }

    @Login
    @RequestMapping(value = "/list.json", method = RequestMethod.GET)
    public PageResult<PatientVO> getPatientInfo(@MyParameter PatientQueryRQ queryRQ) {
        PatientQueryParam queryParam = PatientUtil.transformPatientQueryParam(queryRQ);
        PageResult<PatientInfo> pageResult = this.patientAO.getPatientInfo(queryParam, queryRQ.getPaginator());
        List<PatientVO> patientVOList = PatientUtil.transformPatientVO(pageResult.getData());
        for (PatientVO patientVO : patientVOList) {
            UserInfo userInfo = this.userAO.getUserInfoByUserId(patientVO.getUserId());
            patientVO.setUserInfoVO(UserUtil.transformUserInfoVO(userInfo));
        }
        return new PageResult<>(patientVOList, pageResult.getPaginator());
    }

    @Login
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public boolean add(@RequestBody PatientAddRQ addRQ) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();

        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(addRQ.getPhone());
        userInfo.setRealName(addRQ.getName());
        userInfo.setGender(Gender.parse(addRQ.getGender()));
        Long userId = this.userAO.addUserInfo(userInfo, loginInfo.getRealName());

        PatientInfo patientInfo = PatientUtil.transformPatientInfo(addRQ, regionAO);
        patientInfo.setUserId(userId);
        this.patientAO.addPatientInfo(patientInfo, loginInfo.getRealName());
        return true;
    }
}
