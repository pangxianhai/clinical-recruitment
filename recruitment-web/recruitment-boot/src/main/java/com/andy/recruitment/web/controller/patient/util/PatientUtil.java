package com.andy.recruitment.web.controller.patient.util;

import com.andy.recruitment.patient.model.PatientInfo;
import com.andy.recruitment.patient.model.PatientQueryParam;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.region.model.AddressInfo;
import com.andy.recruitment.user.constant.Gender;
import com.andy.recruitment.user.constant.UserStatus;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.patient.request.PatientAddRQ;
import com.andy.recruitment.web.controller.patient.request.PatientQueryRQ;
import com.andy.recruitment.web.controller.patient.response.PatientVO;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.StringUtil;
import com.xgimi.util.BeanUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 患者工具类
 *
 * @author 庞先海 2019-01-22
 */
public class PatientUtil {

    public static PatientInfo transformPatientInfo(PatientAddRQ patientAddRQ, RegionAO regionAO) {
        if (null == patientAddRQ) {
            return null;
        }
        PatientInfo patientInfo = new PatientInfo();
        BeanUtil.copyProperties(patientAddRQ, patientInfo);
        buildPatientAddAddress(patientInfo, patientAddRQ, regionAO);
        return patientInfo;
    }

    public static PatientVO transformPatientVO(PatientInfo patientInfo) {
        if (null == patientInfo) {
            return null;
        }
        PatientVO patientVO = new PatientVO();
        BeanUtil.copyProperties(patientInfo, patientVO);
        return patientVO;
    }

    public static List<PatientVO> transformPatientVO(List<PatientInfo> patientInfoList) {
        if (CollectionUtil.isEmpty(patientInfoList)) {
            return new ArrayList<>(0);
        }
        return patientInfoList.stream().map(PatientUtil::transformPatientVO).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static PatientQueryParam transformPatientQueryParam(PatientQueryRQ queryRQ) {
        if (null == queryRQ) {
            return null;
        }
        PatientQueryParam queryParam = new PatientQueryParam();
        BeanUtil.copyProperties(queryRQ, queryParam);
        queryParam.setStatus(UserStatus.parse(queryRQ.getStatus()));
        return queryParam;
    }

    public static UserInfo transformUserInfo(PatientAddRQ patientAddRQ) {
        if (null == patientAddRQ) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(patientAddRQ, userInfo);
        userInfo.setGender(Gender.parse(patientAddRQ.getGender()));
        userInfo.setRealName(patientAddRQ.getName());
        userInfo.setUserType(UserType.PATIENT);
        return userInfo;
    }

    public static void buildPatientAddAddress(PatientInfo patientInfo, PatientAddRQ patientAddRQ, RegionAO regionAO) {
        if (StringUtil.isEmpty(patientAddRQ.getAddress())) {
            return;
        }
        AddressInfo addressInfo = regionAO.parseAddressInfo(patientAddRQ.getAddress());
        if (null == addressInfo) {
            return;
        }
        if (null != addressInfo.getProvince()) {
            patientInfo.setProvinceId(addressInfo.getProvince().getRegionId());
        }
        if (null != addressInfo.getCity()) {
            patientInfo.setCityId(addressInfo.getCity().getRegionId());
        }
        if (null != addressInfo.getDistrict()) {
            patientInfo.setDistrictId(addressInfo.getDistrict().getRegionId());
        }
    }
}
