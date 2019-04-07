package com.andy.recruitment.web.controller.patient.webservice;

import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
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
import com.xgimi.commons.util.asserts.AssertUtil;
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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public boolean register(@RequestBody PatientAddRQ patientAddRQ) {
        PatientInfo patientInfo = PatientUtil.transformPatientInfo(patientAddRQ, regionAO);
        UserInfo userInfo = PatientUtil.transformUserInfo(patientAddRQ);
        patientInfo.setUserInfo(userInfo);
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

    @Login
    @RequestMapping(value = "", method = RequestMethod.POST)
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

    @Login
    @RequestMapping(value = "/currentInfo", method = RequestMethod.GET)
    public PatientVO getPatientInfo() {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        PatientInfo patientInfo = this.patientAO.getPatientInfoByUserId(loginInfo.getUserId());
        AssertUtil.assertNull(patientInfo, () -> {
            throw new BusinessException(BusinessErrorCode.OPERATE_ERROR);
        });
        PatientVO patientVO = PatientUtil.transformPatientVO(patientInfo);
        UserInfo userInfo = this.userAO.getUserInfoByUserId(loginInfo.getUserId());
        patientVO.setUserInfoVO(UserUtil.transformUserInfoVO(userInfo));
        patientVO.setAddress(this.regionAO.parseAddressName(patientVO.getProvinceId(), patientVO.getCityId(),
                                                            patientVO.getDistrictId()));
        return patientVO;
    }
}
