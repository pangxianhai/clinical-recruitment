package com.andy.recruitment.web.controller.recruitment.webservice;

import com.andy.recruitment.doctor.ao.DoctorAO;
import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.patient.PatientAO;
import com.andy.recruitment.patient.model.PatientInfo;
import com.andy.recruitment.recruitment.ao.RecruitmentAO;
import com.andy.recruitment.recruitment.ao.RecruitmentApplicationAO;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationInfo;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationQueryParam;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.researchcenter.ao.ResearchCenterAO;
import com.andy.recruitment.researchcenter.model.ResearchCenterInfo;
import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.doctor.response.DoctorInfoVO;
import com.andy.recruitment.web.controller.doctor.util.DoctorUtil;
import com.andy.recruitment.web.controller.patient.response.PatientVO;
import com.andy.recruitment.web.controller.patient.util.PatientUtil;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationQueryRQ;
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
import com.xgimi.context.ServletContext;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 招募信息申请记录controller
 *
 * @author 庞先海 2019-02-07
 */
@Controller
@RequestMapping("/recruitmentapplication")
public class RecruitmentApplicationController {

    private final RecruitmentApplicationAO recruitmentApplicationAO;

    private final RecruitmentAO recruitmentAO;

    private final PatientAO patientAO;

    private final DoctorAO doctorAO;

    private final UserAO userAO;

    private final RegionAO regionAO;

    private final ResearchCenterAO researchCenterAO;

    @Autowired
    public RecruitmentApplicationController(RecruitmentApplicationAO recruitmentApplicationAO,
                                            RecruitmentAO recruitmentAO, PatientAO patientAO, DoctorAO doctorAO,
                                            UserAO userAO, RegionAO regionAO, ResearchCenterAO researchCenterAO) {
        this.recruitmentApplicationAO = recruitmentApplicationAO;
        this.recruitmentAO = recruitmentAO;
        this.patientAO = patientAO;
        this.doctorAO = doctorAO;
        this.userAO = userAO;
        this.regionAO = regionAO;
        this.researchCenterAO = researchCenterAO;
    }

    @Login
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String recruitmentList(Map<String, Object> model) {
        return "recruitment/application/list";
    }

    @Login
    @RequestMapping(value = "/detail/{applicationId:\\d+}", method = RequestMethod.GET)
    public String recruitmentDetail(@PathVariable Long applicationId, Map<String, Object> model) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        UserInfo userInfo = this.userAO.getUserInfoByUserId(loginInfo.getUserId());
        RecruitmentApplicationInfo applicationInfo = this.recruitmentApplicationAO.getRecruitmentApplicationInfo(
            applicationId);
        if (UserType.PATIENT.equals(userInfo.getUserType())) {
            PatientInfo patientInfo = this.patientAO.getPatientInfoByUserId(userInfo.getUserId());
            AssertUtil.assertNull(patientInfo, () -> {
                throw new BusinessException(BusinessErrorCode.USER_ACCOUNT_ERROR);
            });
            AssertUtil.assertBoolean(applicationInfo.getPatientId().equals(patientInfo.getPatientId()), () -> {
                throw new BusinessException(BusinessErrorCode.RECRUITMENT_APPLICATION_NOT_AUTH);
            });
        }
        if (UserType.DOCTOR.equals(userInfo.getUserType())) {
            DoctorInfo doctorInfo = this.doctorAO.getDoctorInfoByUserId(userInfo.getUserId());
            AssertUtil.assertNull(doctorInfo, () -> {
                throw new BusinessException(BusinessErrorCode.USER_ACCOUNT_ERROR);
            });
            AssertUtil.assertBoolean(applicationInfo.getDoctorId().equals(doctorInfo.getDoctorId()), () -> {
                throw new BusinessException(BusinessErrorCode.RECRUITMENT_APPLICATION_NOT_AUTH);
            });
        }
        RecruitmentApplicationVO applicationVO = RecruitmentUtil.transformApplicationVO(applicationInfo);
        this.buildApplicationVO(applicationVO);
        this.buildPatientAddressInfo(applicationVO);
        model.put("applicationVO", applicationVO);
        return "recruitment/application/detail";
    }

    @Login
    @RequestMapping(value = "/listInfo", method = RequestMethod.GET)
    public String recruitmentList(RecruitmentApplicationQueryRQ queryRQ, Map<String, Object> model) {
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
        model.put("applicationVOList", applicationVOList);
        this.buildApplicationVO(applicationVOList);
        return "recruitment/application/listInfo";
    }

    @Login
    @RequestMapping(value = "/list-pc", method = RequestMethod.GET)
    public String recruitmentPcList(Map<String, Object> model) {
        return "recruitment/application/list-pc";
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

    private void buildPatientAddressInfo(RecruitmentApplicationVO applicationVO) {
        if (null == applicationVO) {
            return;
        }
        PatientVO patientVO = applicationVO.getPatientVO();
        if (null == patientVO) {
            return;
        }
        patientVO.setAddress(this.regionAO.parseAddressName(patientVO.getProvinceId(), patientVO.getCityId(),
                                                            patientVO.getDistrictId()));
    }
}
