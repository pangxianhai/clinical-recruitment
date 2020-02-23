package com.andy.recruitment.web.controller.patient.util;

import com.andy.recruitment.biz.region.entity.AddressInfo;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.patient.constant.PatientStatus;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.patient.entity.PatientQuery;
import com.andy.recruitment.dao.user.constant.Gender;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.patient.request.PatientAddReq;
import com.andy.recruitment.web.controller.patient.request.PatientQueryReq;
import com.andy.recruitment.web.controller.patient.request.PatientRegisterReq;
import com.andy.recruitment.web.controller.patient.response.PatientInfoDetailRes;
import com.andy.recruitment.web.controller.patient.response.PatientInfoRes;
import com.andy.recruitment.web.controller.user.response.UserInfoRes;
import com.andy.recruitment.web.controller.user.util.UserInfoUtil;
import com.soyoung.base.util.asserts.AssertUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
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

    @Autowired
    public PatientInfoUtil(RegionService regionService) {
        PatientInfoUtil.regionService = regionService;
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

    public static PatientInfoDetailRes transformReferenceDetailRes(PatientInfoDO patientInfoDo) {
        if (patientInfoDo == null) {
            return null;
        }
        List<PatientInfoDO> patientInfoDoList = Collections.singletonList(patientInfoDo);
        List<PatientInfoDetailRes> patientInfoDetailResList = PatientInfoUtil.transformReferenceDetailRes(
            patientInfoDoList);
        if (CollectionUtils.isEmpty(patientInfoDetailResList)) {
            return null;
        }
        return patientInfoDetailResList.get(0);
    }

    public static List<PatientInfoDetailRes> transformReferenceDetailRes(List<PatientInfoDO> patientInfoDoList) {
        if (CollectionUtils.isEmpty(patientInfoDoList)) {
            return Collections.emptyList();
        }
        List<Long> userIdList = new ArrayList<>(patientInfoDoList.size());
        for (PatientInfoDO patientInfoDo : patientInfoDoList) {
            userIdList.add(patientInfoDo.getUserId());
        }
        Map<Long, UserInfoRes> userInfoResMap = UserInfoUtil.getUserInfoRes(userIdList);
        return patientInfoDoList.stream().map(
            referenceInfoDo -> PatientInfoUtil.transformReferenceDetailRes(referenceInfoDo, userInfoResMap)).filter(
            Objects::nonNull).collect(Collectors.toList());
    }

    public static PatientInfoRes transformReferenceRes(PatientInfoDO patientInfoDo) {
        if (patientInfoDo == null) {
            return null;
        }
        PatientInfoRes patientInfoRes = new PatientInfoRes();
        BeanUtils.copyProperties(patientInfoDo, patientInfoRes);
        patientInfoRes.setPatientId(patientInfoDo.getId());
        patientInfoRes.setAddress(
            regionService.parseAddressName(patientInfoDo.getProvinceId(), patientInfoDo.getCityId(),
                patientInfoDo.getDistrictId()));
        return patientInfoRes;
    }

    private static PatientInfoDetailRes transformReferenceDetailRes(PatientInfoDO patientInfoDo,
        Map<Long, UserInfoRes> userInfoResMap) {
        if (patientInfoDo == null) {
            return null;
        }
        PatientInfoDetailRes patientInfoDetailRes = new PatientInfoDetailRes();
        BeanUtils.copyProperties(patientInfoDo, patientInfoDetailRes);
        patientInfoDetailRes.setPatientId(patientInfoDo.getId());
        patientInfoDetailRes.setUserInfoRes(userInfoResMap.get(patientInfoDo.getUserId()));
        patientInfoDetailRes.setAddress(
            regionService.parseAddressName(patientInfoDo.getProvinceId(), patientInfoDo.getCityId(),
                patientInfoDo.getDistrictId()));
        return patientInfoDetailRes;
    }
}