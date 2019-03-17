package com.andy.recruitment.web.controller.patient.webservice;


import com.andy.recruitment.patient.PatientAO;
import com.andy.recruitment.patient.model.PatientInfo;
import com.andy.recruitment.patient.model.PatientQueryParam;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.patient.request.PatientQueryRQ;
import com.andy.recruitment.web.controller.patient.response.PatientVO;
import com.andy.recruitment.web.controller.patient.util.PatientUtil;
import com.andy.recruitment.web.controller.region.response.RegionVO;
import com.andy.recruitment.web.controller.region.util.RegionUtil;
import com.andy.recruitment.web.controller.user.util.UserUtil;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.commons.page.PageResult;
import com.xgimi.context.ServletContext;
import com.xgimi.converter.MyParameter;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 患者controller
 *
 * @author 庞先海 2019-01-22
 */
@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientAO patientAO;

    private final UserAO userAO;

    private final RegionAO regionAO;

    @Autowired
    public PatientController(PatientAO patientAO, UserAO userAO, RegionAO regionAO) {
        this.patientAO = patientAO;
        this.userAO = userAO;
        this.regionAO = regionAO;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(String redirectURL, Integer userType, String openId, String nickname,
                           Map<String, Object> model) {
        UserInfo userInfo = this.userAO.getUserInfoByOpenId(openId);
        if (null != userInfo) {
            this.userAO.saveUserInfoCookie(userInfo, ServletContext.getResponse());
            return "redirect:" + redirectURL;
        } else {
            model.put("redirectURL", redirectURL);
            model.put("userType", userType);
            model.put("openId", openId);
            model.put("nickname", nickname);
            return "patient/register";
        }
    }

    @Login
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public String patientInfo(Map<String, Object> model) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        PatientInfo patientInfo = patientAO.getPatientInfoByUserId(loginInfo.getUserId());
        UserInfo userInfo = this.userAO.getUserInfoByUserId(loginInfo.getUserId());
        PatientVO patientVO = PatientUtil.transformPatientVO(patientInfo);
        patientVO.setAddress(this.regionAO.parseAddressName(patientVO.getProvinceId(), patientVO.getCityId(),
                                                            patientVO.getDistrictId()));
        model.put("userInfoVO", UserUtil.transformUserInfoVO(userInfo));
        model.put("patientVO", patientVO);
        return "patient/me";
    }

    @Login
    @RequestMapping(value = "/list-pc", method = RequestMethod.GET)
    public String patientListPc(Map<String, Object> model) {
        return "patient/list-pc";
    }

    @Login
    @RequestMapping(value = "/listPcInfo", method = RequestMethod.GET)
    public String patientListInfoPc(@MyParameter PatientQueryRQ queryRQ, Map<String, Object> model) {
        PatientQueryParam queryParam = PatientUtil.transformPatientQueryParam(queryRQ);
        PageResult<PatientInfo> pageResult = this.patientAO.getPatientInfo(queryParam, queryRQ.getPaginator());
        List<PatientVO> patientVOList = PatientUtil.transformPatientVO(pageResult.getData());
        for (PatientVO patientVO : patientVOList) {
            UserInfo userInfo = this.userAO.getUserInfoByUserId(patientVO.getUserId());
            patientVO.setUserInfoVO(UserUtil.transformUserInfoVO(userInfo));
            String address = this.regionAO.parseAddressName(patientVO.getProvinceId(), patientVO.getCityId(),
                                                            patientVO.getDistrictId());
            patientVO.setAddress(address);
        }
        model.put("patientVOList", patientVOList);
        model.put("paginator", pageResult.getPaginator());
        return "patient/listInfo-pc";
    }

    @Login
    @RequestMapping(value = "/add-pc", method = RequestMethod.GET)
    public String patientAddPc(Map<String, Object> model) {
        List<RegionVO> regionVOList = RegionUtil.buildSelectOptions(null, regionAO);
        model.put("regionVOList", regionVOList);
        return "patient/add-pc";
    }

}
