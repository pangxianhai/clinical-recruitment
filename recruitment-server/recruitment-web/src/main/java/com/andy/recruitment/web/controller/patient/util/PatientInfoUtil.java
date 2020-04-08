package com.andy.recruitment.web.controller.patient.util;

import com.andy.recruitment.api.patient.request.PatientAddReq;
import com.andy.recruitment.api.patient.request.PatientQueryReq;
import com.andy.recruitment.api.patient.request.PatientRegisterReq;
import com.andy.recruitment.biz.patient.service.PatientInfoService;
import com.andy.recruitment.biz.region.entity.AddressInfo;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.common.patient.constant.PatientStatus;
import com.andy.recruitment.common.user.constant.Gender;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.patient.entity.PatientQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.spring.util.asserts.AssertUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 患者信息工具
 *
 * @author 庞先海 2020-02-16
 */
@Component
public class PatientInfoUtil {

    private static RegionService regionService;

    private static PatientInfoService patientInfoService;

    @Autowired
    public PatientInfoUtil(RegionService regionService, PatientInfoService patientInfoService) {
        PatientInfoUtil.regionService = regionService;
        PatientInfoUtil.patientInfoService = patientInfoService;
    }

    public static PatientInfoDO transformPatientInfoDo(PatientAddReq patientAddReq) {
        if (null == patientAddReq) {
            return null;
        }
        PatientInfoDO patientInfoDo = new PatientInfoDO();
        BeanUtils.copyProperties(patientAddReq, patientInfoDo);
        return patientInfoDo;
    }

    public static UserInfoDO transformUserInfo(PatientAddReq patientAddReq) {
        if (null == patientAddReq) {
            return null;
        }
        UserInfoDO userInfoDo = new UserInfoDO();
        BeanUtils.copyProperties(patientAddReq, userInfoDo);
        userInfoDo.setGender(Gender.parse(patientAddReq.getGender()));
        userInfoDo.setRealName(patientAddReq.getName());
        return userInfoDo;
    }

    public static PatientInfoDO transformPatientInfoDo(PatientRegisterReq patientRegisterReq) {
        if (null == patientRegisterReq) {
            return null;
        }
        PatientInfoDO patientInfoDo = new PatientInfoDO();
        BeanUtils.copyProperties(patientRegisterReq, patientInfoDo);
        AddressInfo addressInfo = regionService.parseAddressInfo(patientRegisterReq.getAddress());
        AssertUtil.assertNull(addressInfo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.PATIENT_REGISTER_REGION_ERROR);
        });
        AssertUtil.assertNull(addressInfo.getProvince(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.PATIENT_REGISTER_REGION_ERROR);
        });
        AssertUtil.assertNull(addressInfo.getCity(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.PATIENT_REGISTER_REGION_ERROR);
        });
        patientInfoDo.setProvinceId(addressInfo.getProvince().getId());
        patientInfoDo.setCityId(addressInfo.getCity().getId());
        if (addressInfo.getDistrict() != null) {
            patientInfoDo.setDistrictId(addressInfo.getDistrict().getId());
        }
        return patientInfoDo;
    }

    public static UserInfoDO transformUserInfo(PatientRegisterReq patientRegisterReq) {
        if (null == patientRegisterReq) {
            return null;
        }
        UserInfoDO userInfoDo = new UserInfoDO();
        BeanUtils.copyProperties(patientRegisterReq, userInfoDo);
        userInfoDo.setGender(Gender.parse(patientRegisterReq.getGender()));
        userInfoDo.setRealName(patientRegisterReq.getName());
        return userInfoDo;
    }


    public static PatientQuery transformPatientQuery(PatientQueryReq queryReq) {
        if (queryReq == null) {
            return null;
        }

        PatientQuery query = new PatientQuery();
        BeanUtils.copyProperties(queryReq, query);
        if (queryReq.getStatus() != null) {
            query.setStatus(PatientStatus.parse(queryReq.getStatus()));
        }
        return query;
    }
}