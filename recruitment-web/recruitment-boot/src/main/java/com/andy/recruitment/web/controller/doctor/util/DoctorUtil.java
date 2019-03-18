package com.andy.recruitment.web.controller.doctor.util;

import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.doctor.model.DoctorQueryParam;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.region.model.AddressInfo;
import com.andy.recruitment.user.constant.Gender;
import com.andy.recruitment.user.constant.UserStatus;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.doctor.request.DoctorAddRQ;
import com.andy.recruitment.web.controller.doctor.request.DoctorQueryRQ;
import com.andy.recruitment.web.controller.doctor.response.DoctorInfoVO;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.util.BeanUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 医生工具类
 *
 * @author 庞先海 2019-01-20
 */
public class DoctorUtil {

    public static DoctorInfo transformDoctorInfo(DoctorAddRQ doctorAddRQ, RegionAO regionAO) {
        if (null == doctorAddRQ) {
            return null;
        }
        DoctorInfo doctorInfo = new DoctorInfo();
        BeanUtil.copyProperties(doctorAddRQ, doctorInfo);
        AddressInfo addressInfo = regionAO.parseAddressInfo(doctorAddRQ.getAddress());
        if (null != addressInfo) {
            if (null != addressInfo.getProvince()) {
                doctorInfo.setProvinceId(addressInfo.getProvince().getRegionId());
            }
            if (null != addressInfo.getCity()) {
                doctorInfo.setCityId(addressInfo.getCity().getRegionId());
            }
            if (null != addressInfo.getDistrict()) {
                doctorInfo.setDistrictId(addressInfo.getDistrict().getRegionId());
            }
        }
        return doctorInfo;
    }

    public static DoctorInfoVO transformDoctorInfoVO(DoctorInfo doctorInfo) {
        if (null == doctorInfo) {
            return null;
        }
        DoctorInfoVO doctorInfoVO = new DoctorInfoVO();
        BeanUtil.copyProperties(doctorInfo, doctorInfoVO);
        return doctorInfoVO;
    }

    public static List<DoctorInfoVO> transformDoctorInfoVO(List<DoctorInfo> doctorInfoList) {
        if (CollectionUtil.isEmpty(doctorInfoList)) {
            return new ArrayList<>(0);
        }
        return doctorInfoList.stream().map(DoctorUtil::transformDoctorInfoVO).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static DoctorQueryParam transformDoctorQueryParam(DoctorQueryRQ queryRQ) {
        if (null == queryRQ) {
            return null;
        }
        DoctorQueryParam queryParam = new DoctorQueryParam();
        BeanUtil.copyProperties(queryRQ, queryParam);
        queryParam.setStatus(UserStatus.parse(queryRQ.getStatus()));
        return queryParam;
    }

    public static UserInfo transformUserInfo(DoctorAddRQ doctorAddRQ) {
        if (null == doctorAddRQ) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(doctorAddRQ, userInfo);
        userInfo.setGender(Gender.parse(doctorAddRQ.getGender()));
        userInfo.setRealName(doctorAddRQ.getName());
        userInfo.setUserType(UserType.DOCTOR);
        return userInfo;
    }
}
