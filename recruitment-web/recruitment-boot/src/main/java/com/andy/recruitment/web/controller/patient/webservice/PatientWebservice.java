package com.andy.recruitment.web.controller.patient.webservice;

import com.andy.recruitment.patient.PatientAO;
import com.andy.recruitment.patient.model.PatientInfo;
import com.andy.recruitment.patient.model.PatientQueryParam;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.user.ao.UserAO;
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public boolean patientRegister(@RequestBody PatientAddRQ patientAddRQ) {
        String operator = UserUtil.getOperator(patientAddRQ.getName());
        PatientInfo patientInfo = PatientUtil.transformPatientInfo(patientAddRQ, regionAO);
        UserInfo userInfo = PatientUtil.transformUserInfo(patientAddRQ);
        patientInfo.setUserInfo(userInfo);
        this.patientAO.registerPatient(patientInfo, operator);
        return true;
    }

    @Login
    @RequestMapping(value = "", method = RequestMethod.GET)
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

    @RequestMapping(value = "/currentInfo", method = RequestMethod.GET)
    public PatientVO getPatientInfo() {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        if (null == loginInfo) {
            return null;
        }
        PatientInfo patientInfo = this.patientAO.getPatientInfoByUserId(loginInfo.getUserId());
        if (null == patientInfo) {
            return null;
        }
        PatientVO patientVO = PatientUtil.transformPatientVO(patientInfo);
        UserInfo userInfo = this.userAO.getUserInfoByUserId(loginInfo.getUserId());
        patientVO.setUserInfoVO(UserUtil.transformUserInfoVO(userInfo));
        patientVO.setAddress(this.regionAO.parseAddressName(patientVO.getProvinceId(), patientVO.getCityId(),
                                                            patientVO.getDistrictId()));
        return patientVO;
    }
}
