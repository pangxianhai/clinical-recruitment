package com.andy.recruitment.web.controller.recruitment.webservice;

import com.andy.recruitment.doctor.ao.DoctorAO;
import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.oss.ao.OssAO;
import com.andy.recruitment.patient.ao.PatientAO;
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
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.doctor.response.DoctorInfoVO;
import com.andy.recruitment.web.controller.doctor.util.DoctorUtil;
import com.andy.recruitment.web.controller.oss.response.UploadImageVO;
import com.andy.recruitment.web.controller.patient.response.PatientVO;
import com.andy.recruitment.web.controller.patient.util.PatientUtil;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationQueryRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationUpdateRQ;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentApplicationVO;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentVO;
import com.andy.recruitment.web.controller.recruitment.response.ResearchCenterVO;
import com.andy.recruitment.web.controller.recruitment.util.RecruitmentUtil;
import com.andy.recruitment.web.controller.user.response.UserInfoVO;
import com.andy.recruitment.web.controller.user.util.UserUtil;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.StringUtil;
import com.xgimi.commons.util.asserts.AssertUtil;
import com.xgimi.context.ServletContext;
import com.xgimi.converter.MyParameter;
import java.util.List;
import java.util.stream.Collectors;
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

    private final OssAO ossAO;

    @Value("${recruitment.address}")
    private String serverAddress;

    @Autowired
    public RecruitmentApplicationWebservice(RecruitmentApplicationAO recruitmentApplicationAO, PatientAO patientAO,
                                            UserAO userAO, RegionAO regionAO, DoctorAO doctorAO,
                                            ResearchCenterAO researchCenterAO, RecruitmentAO recruitmentAO,
                                            OssAO ossAO) {
        this.recruitmentApplicationAO = recruitmentApplicationAO;
        this.patientAO = patientAO;
        this.userAO = userAO;
        this.regionAO = regionAO;
        this.doctorAO = doctorAO;
        this.researchCenterAO = researchCenterAO;
        this.recruitmentAO = recruitmentAO;
        this.ossAO = ossAO;
    }

    /**
     * 招募项目报名
     *
     * @return 患者用户ID
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Long applicationRecruitment(@RequestBody RecruitmentApplicationRQ applicationRQ) {
        String operator = UserUtil.getOperator(applicationRQ.getName());
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        PatientInfo patientInfo = PatientUtil.transformPatientInfo(applicationRQ, regionAO);
        UserInfo userInfo = PatientUtil.transformUserInfo(applicationRQ);
        patientInfo.setUserInfo(userInfo);
        patientInfo = this.patientAO.registerPatient(patientInfo, operator);

        RecruitmentApplicationInfo applicationInfo = RecruitmentUtil.transformApplicationInfo(applicationRQ);
        if (null != applicationRQ.getDoctorUserId()) {
            DoctorInfo doctorInfo = this.doctorAO.getDoctorInfoByUserId(applicationRQ.getDoctorUserId());
            if (null != doctorInfo) {
                applicationInfo.setDoctorId(doctorInfo.getDoctorId());
            }
        }
        applicationInfo.setPatientId(patientInfo.getPatientId());
        if (null != loginInfo) {
            UserInfo loginUserInfo = this.userAO.getUserInfoByUserId(loginInfo.getUserId());
            if (UserType.DOCTOR.equals(loginUserInfo.getUserType())) {
                DoctorInfo doctorInfo = this.doctorAO.getDoctorInfoByUserId(loginUserInfo.getUserId());
                if (null != doctorInfo) {
                    applicationInfo.setDoctorId(doctorInfo.getDoctorId());
                }
            }
        }
        this.recruitmentApplicationAO.addRecruitmentApplication(applicationInfo, operator);
        return patientInfo.getUserId();
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
    @RequestMapping(value = "/status/{applicationId:\\d+}", method = RequestMethod.PATCH)
    public boolean accedeApplicationInfo(@PathVariable Long applicationId,
                                         @RequestBody RecruitmentApplicationUpdateRQ updateRQ) {
        RecruitmentApplicationInfo applicationInfo = new RecruitmentApplicationInfo();
        applicationInfo.setApplicationId(applicationId);
        applicationInfo.setStatus(RecruitmentApplicationStatus.parse(updateRQ.getStatus()));
        this.recruitmentApplicationAO.updateRecruitmentApplication(applicationInfo, ServletContext.getLoginUname());
        return true;
    }

    @Login
    @RequestMapping(value = "/{applicationId:\\d+}", method = RequestMethod.GET)
    public RecruitmentApplicationVO recruitmentDetail(@PathVariable Long applicationId) {
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
        this.buildDoctorInfoAddressInfo(applicationVO);
        applicationVO.setDiseaseImageList(this.buildImageVO(applicationInfo.getDiseaseImageList()));
        return applicationVO;
    }


    private PageResult<RecruitmentApplicationVO> queryApplication(RecruitmentApplicationQueryRQ queryRQ) {
        RecruitmentApplicationQueryParam queryParam = RecruitmentUtil.transformApplicationQueryParam(queryRQ);
        if (StringUtil.isEmpty(queryRQ.getOrderSegment())) {
            queryRQ.setOrderSegment("created_time.desc");
        }
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
        this.buildRecruitmentInfo(applicationVO);
    }

    private void buildRecruitmentInfo(RecruitmentApplicationVO applicationVO) {
        RecruitmentInfo recruitmentInfo = this.recruitmentAO.getRecruitmentInfoById(applicationVO.getRecruitmentId());
        if (null == recruitmentInfo) {
            RecruitmentVO recruitmentVO = new RecruitmentVO();
            recruitmentVO.setTitle("罕见病病例");
            applicationVO.setRecruitmentVO(recruitmentVO);
        } else {
            applicationVO.setRecruitmentVO(RecruitmentUtil.transformRecruitmentVO(recruitmentInfo));
            List<ResearchCenterInfo> centerInfoList = researchCenterAO.getResearchCenterByRecruitmentId(
                recruitmentInfo.getRecruitmentId());
            List<ResearchCenterVO> centerVOList = RecruitmentUtil.transformResearchCenterVO(regionAO, centerInfoList);
            applicationVO.getRecruitmentVO().setResearchCenterVOList(centerVOList);
        }
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

    private void buildDoctorInfoAddressInfo(RecruitmentApplicationVO applicationVO) {
        if (null == applicationVO) {
            return;
        }
        DoctorInfoVO doctorInfoVO = applicationVO.getDoctorInfoVO();
        if (null == doctorInfoVO) {
            return;
        }
        doctorInfoVO.setAddress(this.regionAO.parseAddressName(doctorInfoVO.getProvinceId(), doctorInfoVO.getCityId(),
                                                               doctorInfoVO.getDistrictId()));
    }

    private List<UploadImageVO> buildImageVO(List<String> imageList) {
        if (CollectionUtil.isEmpty(imageList)) {
            return null;
        }
        return imageList.stream().map(image -> {
            String imageUrl = ossAO.generateUrl(image);
            String thumbnailUrl = ossAO.generateUrl(image, 80, 80);
            return new UploadImageVO(null, imageUrl, thumbnailUrl);
        }).collect(Collectors.toList());
    }
}
