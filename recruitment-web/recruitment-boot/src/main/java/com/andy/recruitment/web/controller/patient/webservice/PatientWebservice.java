package com.andy.recruitment.web.controller.patient.webservice;

import com.andy.recruitment.patient.PatientAO;
import com.andy.recruitment.patient.model.PatientInfo;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.region.model.AddressInfo;
import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.patient.request.PatientAddRQ;
import com.andy.recruitment.web.controller.patient.util.PatientUtil;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.context.ServletContext;
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

    @Login
    @RequestMapping(value = "/register.json", method = RequestMethod.POST)
    public boolean register(@RequestBody PatientAddRQ patientAddRQ) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        PatientInfo patientInfo = PatientUtil.transformPatientInfo(patientAddRQ);
        patientInfo.setUserId(loginInfo.getUserId());
        AddressInfo addressInfo = regionAO.parseAddressInfo(patientAddRQ.getAddress());
        if (null != addressInfo.getProvince()) {
            patientInfo.setProvinceId(addressInfo.getProvince().getRegionId());
        }
        if (null != addressInfo.getCity()) {
            patientInfo.setCityId(addressInfo.getCity().getRegionId());
        }
        if (null != addressInfo.getDistrict()) {
            patientInfo.setDistrictId(addressInfo.getDistrict().getRegionId());
        }
        this.patientAO.addPatientInfo(patientInfo, ServletContext.getLoginUname());
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(loginInfo.getUserId());
        userInfo.setUserType(UserType.PATIENT);
        userInfo.setRealName(patientAddRQ.getName());
        this.userAO.updateUserInfo(userInfo, ServletContext.getLoginUname());
        this.userAO.bandPhone(loginInfo.getUserId(), patientAddRQ.getPhone(), null);
        return true;
    }
}
