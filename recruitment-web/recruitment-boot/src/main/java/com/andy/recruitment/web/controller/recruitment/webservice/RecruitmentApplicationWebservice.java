package com.andy.recruitment.web.controller.recruitment.webservice;

import com.andy.recruitment.doctor.ao.DoctorAO;
import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.patient.PatientAO;
import com.andy.recruitment.patient.model.PatientInfo;
import com.andy.recruitment.recruitment.ao.RecruitmentAO;
import com.andy.recruitment.recruitment.ao.RecruitmentApplicationAO;
import com.andy.recruitment.recruitment.constant.RecruitmentApplicationStatus;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationInfo;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationQueryParam;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.researchcenter.ao.ResearchCenterAO;
import com.andy.recruitment.researchcenter.model.ResearchCenterInfo;
import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.UserStatus;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.doctor.response.DoctorInfoVO;
import com.andy.recruitment.web.controller.doctor.util.DoctorUtil;
import com.andy.recruitment.web.controller.patient.response.PatientVO;
import com.andy.recruitment.web.controller.patient.util.PatientUtil;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationQueryRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationUpdateRQ;
import com.andy.recruitment.web.controller.recruitment.response.ApplicationResultVO;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentApplicationVO;
import com.andy.recruitment.web.controller.recruitment.response.ResearchCenterVO;
import com.andy.recruitment.web.controller.recruitment.util.RecruitmentUtil;
import com.andy.recruitment.web.controller.user.response.UserInfoVO;
import com.andy.recruitment.web.controller.user.util.UserUtil;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.util.CollectionUtil;
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

    private final ResearchCenterAO researchCenterAO;

    private final RecruitmentAO recruitmentAO;

    @Value("${recruitment.address}")
    private String serverAddress;

    @Autowired
    public RecruitmentApplicationWebservice(RecruitmentApplicationAO recruitmentApplicationAO, PatientAO patientAO,
                                            UserAO userAO, RegionAO regionAO, DoctorAO doctorAO,
                                            ResearchCenterAO researchCenterAO, RecruitmentAO recruitmentAO) {
        this.recruitmentApplicationAO = recruitmentApplicationAO;
        this.patientAO = patientAO;
        this.userAO = userAO;
        this.regionAO = regionAO;
        this.doctorAO = doctorAO;
        this.researchCenterAO = researchCenterAO;
        this.recruitmentAO = recruitmentAO;
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
                    try {
                        UserInfo addUserInfo = PatientUtil.transformUserInfo(applicationRQ);
                        Long userId = this.userAO.addUserInfo(addUserInfo, applicationRQ.getName());
                        addUserInfo.setUserId(userId);
                        PatientInfo patientInfo = PatientUtil.transformPatientInfo(applicationRQ, regionAO);
                        patientInfo.setUserId(addUserInfo.getUserId());
                        this.patientAO.addPatientInfo(patientInfo, applicationRQ.getName());
                        patentUserId = userId;
                    } catch (Exception e) {
                        throw new BusinessException(BusinessErrorCode.OPERATE_ERROR);
                    }
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
        return this.queryApplication(queryParamRQ);
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

    private PageResult<RecruitmentApplicationVO> queryApplication(RecruitmentApplicationQueryRQ queryRQ) {
        RecruitmentApplicationQueryParam queryParam = RecruitmentUtil.transformApplicationQueryParam(queryRQ);
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        UserInfo userInfo = this.userAO.getUserInfoByUserId(loginInfo.getUserId());
        if (UserType.PATIENT.equals(userInfo.getUserType())) {
            PatientInfo patientInfo = this.patientAO.getPatientInfoByUserId(userInfo.getUserId());
            AssertUtil.assertNull(patientInfo, () -> {
                throw new BusinessException(BusinessErrorCode.USER_ACCOUNT_ERROR);
            });
            queryParam.setPatientId(patientInfo.getPatientId());
        }
        if (UserType.DOCTOR.equals(userInfo.getUserType())) {
            DoctorInfo doctorInfo = this.doctorAO.getDoctorInfoByUserId(userInfo.getUserId());
            AssertUtil.assertNull(doctorInfo, () -> {
                throw new BusinessException(BusinessErrorCode.USER_ACCOUNT_ERROR);
            });
            queryParam.setDoctorId(doctorInfo.getDoctorId());
        }
        PageResult<RecruitmentApplicationInfo> pageResult = this.recruitmentApplicationAO.getRecruitmentApplicationInfo(
            queryParam, queryRQ.getPaginator());
        List<RecruitmentApplicationVO> applicationVOList = RecruitmentUtil.transformApplicationVO(pageResult.getData());
        this.buildApplicationVO(applicationVOList);
        return new PageResult<>(applicationVOList, pageResult.getPaginator());
    }


    private void buildApplicationVO(List<RecruitmentApplicationVO> applicationVOList) {
        if (CollectionUtil.isEmpty(applicationVOList)) {
            return;
        }
        applicationVOList.forEach(this::buildApplicationVO);
    }


    private void buildApplicationVO(RecruitmentApplicationVO applicationVO) {
        RecruitmentInfo recruitmentInfo = this.recruitmentAO.getRecruitmentInfoById(applicationVO.getRecruitmentId());
        applicationVO.setRecruitmentVO(RecruitmentUtil.transformRecruitmentVO(recruitmentInfo));
        DoctorInfo doctorInfo = this.doctorAO.getDoctorInfoById(applicationVO.getDoctorId());
        DoctorInfoVO doctorInfoVO = DoctorUtil.transformDoctorInfoVO(doctorInfo);
        if (null != doctorInfoVO) {
            UserInfo userInfo = this.userAO.getUserInfoByUserId(doctorInfo.getUserId());
            UserInfoVO userInfoVO = UserUtil.transformUserInfoVO(userInfo);
            doctorInfoVO.setUserInfoVO(userInfoVO);
            applicationVO.setDoctorInfoVO(doctorInfoVO);
        }
        PatientInfo patientInfo = this.patientAO.getPatientInfoById(applicationVO.getPatientId());
        PatientVO patientVO = PatientUtil.transformPatientVO(patientInfo);
        if (null != patientVO) {
            UserInfo userInfo = this.userAO.getUserInfoByUserId(patientInfo.getUserId());
            UserInfoVO userInfoVO = UserUtil.transformUserInfoVO(userInfo);
            patientVO.setUserInfoVO(userInfoVO);
            applicationVO.setPatientVO(patientVO);
        }
        List<ResearchCenterInfo> centerInfoList = researchCenterAO.getResearchCenterByRecruitmentId(
            recruitmentInfo.getRecruitmentId());
        List<ResearchCenterVO> centerVOList = RecruitmentUtil.transformResearchCenterVO(regionAO, centerInfoList);
        applicationVO.getRecruitmentVO().setResearchCenterVOList(centerVOList);
    }
}
