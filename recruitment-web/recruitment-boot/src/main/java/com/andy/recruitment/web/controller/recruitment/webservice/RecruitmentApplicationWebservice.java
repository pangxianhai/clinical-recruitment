package com.andy.recruitment.web.controller.recruitment.webservice;

import com.andy.recruitment.doctor.ao.DoctorAO;
import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.patient.PatientAO;
import com.andy.recruitment.patient.model.PatientInfo;
import com.andy.recruitment.recruitment.ao.RecruitmentApplicationAO;
import com.andy.recruitment.recruitment.constant.RecruitmentApplicationStatus;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationInfo;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationQueryParam;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.UserStatus;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.patient.util.PatientUtil;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationQueryRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationUpdateRQ;
import com.andy.recruitment.web.controller.recruitment.response.ApplicationResultVO;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentApplicationVO;
import com.andy.recruitment.web.controller.recruitment.util.RecruitmentUtil;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.util.asserts.AssertUtil;
import com.xgimi.commons.util.encrypt.EncodeUtil;
import com.xgimi.context.ServletContext;
import com.xgimi.converter.MyParameter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 招募信息申请记录webservice接口
 *
 * @author 庞先海 2019-01-25
 */
@RestController
@RequestMapping("/recruitmentapplication")
public class RecruitmentApplicationWebservice {

    private final RecruitmentApplicationAO recruitmentApplicationAO;

    private final PatientAO patientAO;

    private final UserAO userAO;

    private final RegionAO regionAO;

    private final DoctorAO doctorAO;

    @Value("${recruitment.address}")
    private String serverAddress;

    @Autowired
    public RecruitmentApplicationWebservice(RecruitmentApplicationAO recruitmentApplicationAO, PatientAO patientAO,
                                            UserAO userAO, RegionAO regionAO, DoctorAO doctorAO) {
        this.recruitmentApplicationAO = recruitmentApplicationAO;
        this.patientAO = patientAO;
        this.userAO = userAO;
        this.regionAO = regionAO;
        this.doctorAO = doctorAO;
    }

    @RequestMapping(value = "/application.json", method = RequestMethod.POST)
    public ApplicationResultVO applicationRecruitment(@RequestBody RecruitmentApplicationRQ applicationRQ) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        UserInfo userInfo = null;
        if (null != loginInfo) {
            userInfo = this.userAO.getUserInfoByUserId(loginInfo.getUserId());
        }
        if (null == userInfo) {
            Long recruitmentId = applicationRQ.getRecruitmentId();
            String redirectURL = "/recruitment/detail/" + recruitmentId + "?action=application&doctorId="
                                 + applicationRQ.getDoctorId();
            String url = serverAddress + "/user/login?action=application&recruitmentId=" + recruitmentId
                         + "&userType=3&redirectURL=" + EncodeUtil.urlEncode(redirectURL);
            return new ApplicationResultVO(url);
        } else {
            AssertUtil.assertBoolean(UserStatus.NORMAL.equals(userInfo.getStatus()), () -> {
                throw new BusinessException(BusinessErrorCode.USER_FREEZE);
            });
            if (UserType.PATIENT.equals(userInfo.getUserType())) {
                RecruitmentApplicationInfo applicationInfo = RecruitmentUtil.transformApplicationInfo(applicationRQ);
                this.recruitmentApplicationAO.addRecruitmentApplication(loginInfo.getUserId(), applicationInfo,
                                                                        ServletContext.getLoginUname());
            } else if (UserType.DOCTOR.equals(userInfo.getUserType())) {
                UserInfo patentUserInfo = this.userAO.getUserInfoByPhone(applicationRQ.getPhone());
                Long patentUserId;
                if (null == patentUserInfo) {
                    UserInfo addUserInfo = PatientUtil.transformUserInfo(applicationRQ);
                    Long userId = this.userAO.addUserInfo(addUserInfo, applicationRQ.getName());
                    addUserInfo.setUserId(userId);
                    PatientInfo patientInfo = PatientUtil.transformPatientInfo(applicationRQ, regionAO);
                    patientInfo.setUserId(addUserInfo.getUserId());
                    this.patientAO.addPatientInfo(patientInfo, applicationRQ.getName());
                    patentUserId = userId;
                } else {
                    patentUserId = patentUserInfo.getUserId();
                }
                RecruitmentApplicationInfo applicationInfo = RecruitmentUtil.transformApplicationInfo(applicationRQ);
                DoctorInfo doctorInfo = this.doctorAO.getDoctorInfoByUserId(loginInfo.getUserId());
                applicationInfo.setDoctorId(doctorInfo.getDoctorId());
                this.recruitmentApplicationAO.addRecruitmentApplication(patentUserId, applicationInfo,
                                                                        ServletContext.getLoginUname());
            } else {
                throw new BusinessException(BusinessErrorCode.ILLEGAL_ACCESS);
            }
            return new ApplicationResultVO();
        }
    }

    @Login
    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageResult<RecruitmentApplicationVO> getApplicationInfo(
        @MyParameter RecruitmentApplicationQueryRQ queryParamRQ) {
        RecruitmentApplicationQueryParam queryParam = RecruitmentUtil.transformApplicationQueryParam(queryParamRQ);
        PageResult<RecruitmentApplicationInfo> pageResult = this.recruitmentApplicationAO.getRecruitmentApplicationInfo(
            queryParam, queryParamRQ.getPaginator());
        List<RecruitmentApplicationVO> applicationVOList = RecruitmentUtil.transformApplicationVO(pageResult.getData());
        return new PageResult<>(applicationVOList, pageResult.getPaginator());
    }

    @Login
    @RequestMapping(value = "/{applicationId:\\d+}", method = RequestMethod.PUT)
    public boolean updateApplicationInfo(@PathVariable Long applicationId,
                                         @RequestBody RecruitmentApplicationUpdateRQ updateRQ) {
        RecruitmentApplicationInfo applicationInfo = RecruitmentUtil.transformApplicationInfo(updateRQ);
        applicationInfo.setApplicationId(applicationId);
        this.recruitmentApplicationAO.updateRecruitmentApplication(applicationInfo, ServletContext.getLoginUname());
        return true;
    }


    @Login
    @RequestMapping(value = "/{applicationId:\\d+}/accede.json", method = RequestMethod.POST)
    public boolean accedeApplicationInfo(@PathVariable Long applicationId) {
        RecruitmentApplicationInfo applicationInfo = new RecruitmentApplicationInfo();
        applicationInfo.setApplicationId(applicationId);
        applicationInfo.setStatus(RecruitmentApplicationStatus.ACCEDE_SUCCESS);
        this.recruitmentApplicationAO.updateRecruitmentApplication(applicationInfo, ServletContext.getLoginUname());
        return true;
    }

    @Login
    @RequestMapping(value = "/{applicationId:\\d+}/cancelAccede.json", method = RequestMethod.POST)
    public boolean cancelAccedeApplicationInfo(@PathVariable Long applicationId) {
        RecruitmentApplicationInfo applicationInfo = new RecruitmentApplicationInfo();
        applicationInfo.setApplicationId(applicationId);
        applicationInfo.setStatus(RecruitmentApplicationStatus.NOT_ACCEDE);
        this.recruitmentApplicationAO.updateRecruitmentApplication(applicationInfo, ServletContext.getLoginUname());
        return true;
    }
}
