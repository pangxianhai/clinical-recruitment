package com.andy.recruitment.web.controller.recruitment.util;

import com.andy.recruitment.biz.region.entity.AddressInfo;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.recruitment.constant.RecruitmentCategory;
import com.andy.recruitment.dao.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.andy.recruitment.dao.user.constant.Gender;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentAddReq;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationAddReq;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentQueryReq;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentInfoRes;
import com.andy.spring.util.DateUtil;
import com.andy.spring.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 招募项目工具
 *
 * @author 庞先海 2020-01-30
 */
@Component
public class RecruitmentUtil {

    private static RegionService regionService;

    @Autowired
    public RecruitmentUtil(RegionService regionService) {
        RecruitmentUtil.regionService = regionService;
    }

    public static RecruitmentQuery transformQueryParam(RecruitmentQueryReq queryReq) {
        if (null == queryReq) {
            return null;
        }
        RecruitmentQuery queryParam = new RecruitmentQuery();
        BeanUtils.copyProperties(queryReq, queryParam);
        queryParam.setStartTimeBegin(DateUtil.parse(queryReq.getStartTimeBegin(), "yyyy-MM-dd"));
        if (StringUtils.isNotEmpty(queryReq.getStartTimeEnd())) {
            queryParam.setStartTimeEnd(DateUtil.parse(queryReq.getStartTimeEnd() + " 23:59:59"));
        }
        queryParam.setStopTimeBegin(DateUtil.parse(queryReq.getStopTimeBegin(), "yyyy-MM-dd"));
        if (StringUtils.isNotEmpty(queryReq.getStopTimeEnd())) {
            queryParam.setStopTimeEnd(DateUtil.parse(queryReq.getStopTimeEnd() + " 23:59:59"));
        }
        queryParam.setStatus(RecruitmentStatus.parse(queryReq.getStatus()));
        queryParam.setCategory(RecruitmentCategory.parse(queryReq.getCategory()));
        return queryParam;
    }

    public static RecruitmentInfoDO transformRecruitmentInfoDo(RecruitmentAddReq recruitmentAddReq) {
        if (recruitmentAddReq == null) {
            return null;
        }
        RecruitmentInfoDO recruitmentInfoDo = new RecruitmentInfoDO();
        BeanUtils.copyProperties(recruitmentAddReq, recruitmentInfoDo);
        if (StringUtils.isNotEmpty(recruitmentAddReq.getStartTime())) {
            String startTime = recruitmentAddReq.getStartTime() + " 00:00:00";
            recruitmentInfoDo.setStartTime(DateUtil.parse(startTime));
        }
        if (StringUtils.isNotEmpty(recruitmentAddReq.getStopTime())) {
            String stopTime = recruitmentAddReq.getStopTime() + " 23:59:59";
            recruitmentInfoDo.setStopTime(DateUtil.parse(stopTime));
        }
        recruitmentInfoDo.setCategory(RecruitmentCategory.parse(recruitmentAddReq.getCategory()));
        return recruitmentInfoDo;
    }

    public static RecruitmentInfoRes transformRecruitmentInfoRes(RecruitmentInfoDO recruitmentInfoDo) {
        if (recruitmentInfoDo == null) {
            return null;
        }
        RecruitmentInfoRes recruitmentInfoRes = new RecruitmentInfoRes();
        BeanUtils.copyProperties(recruitmentInfoDo, recruitmentInfoRes);
        recruitmentInfoRes.setStartTime(DateUtil.format(recruitmentInfoDo.getStartTime(), "yyyy-MM-dd"));
        recruitmentInfoRes.setStopTime(DateUtil.format(recruitmentInfoDo.getStopTime(), "yyyy-MM-dd"));
        recruitmentInfoRes.setCreatedTime(DateUtil.format(recruitmentInfoDo.getCreatedTime(), "yyyy-MM-dd"));
        recruitmentInfoRes.setRecruitmentId(recruitmentInfoDo.getId());
        return recruitmentInfoRes;
    }

    public static List<RecruitmentInfoRes> transformRecruitmentInfoRes(List<RecruitmentInfoDO> recruitmentInfoDoList) {
        if (CollectionUtils.isEmpty(recruitmentInfoDoList)) {
            return new ArrayList<>(0);
        }
        return recruitmentInfoDoList.stream().map(RecruitmentUtil::transformRecruitmentInfoRes).filter(
            Objects::nonNull).collect(Collectors.toList());
    }

    public static PatientInfoDO buildPatientInfoDo(RecruitmentApplicationAddReq applicationAddReq) {
        if (applicationAddReq == null) {
            return null;
        }
        PatientInfoDO patientInfoDo = new PatientInfoDO();
        patientInfoDo.setAge(applicationAddReq.getPatientAge());
        AddressInfo addressInfo = regionService.parseAddressInfo(applicationAddReq.getPatientAddress());
        if (addressInfo != null) {
            if (addressInfo.getProvince() != null) {
                patientInfoDo.setProvinceId(addressInfo.getProvince().getId());
            }
            if (addressInfo.getCity() != null) {
                patientInfoDo.setCityId(addressInfo.getCity().getId());
            }
            if (addressInfo.getDistrict() != null) {
                patientInfoDo.setDistrictId(addressInfo.getDistrict().getId());
            }
        }
        return patientInfoDo;
    }

    public static UserInfoDO buildUserInfoDo(RecruitmentApplicationAddReq applicationAddReq) {
        if (applicationAddReq == null) {
            return null;
        }
        UserInfoDO userInfoDo = new UserInfoDO();
        userInfoDo.setRealName(applicationAddReq.getPatientName());
        userInfoDo.setPhone(applicationAddReq.getPatientPhone());
        userInfoDo.setGender(Gender.parse(applicationAddReq.getPatientGender()));
        return userInfoDo;
    }

    public static RecruitmentApplicationDO transformRecruitmentApplicationDo(
        RecruitmentApplicationAddReq applicationAddReq, RecruitmentInfoDO recruitmentInfoDo) {
        if (applicationAddReq == null) {
            return null;
        }
        RecruitmentApplicationDO applicationDo = new RecruitmentApplicationDO();
        applicationDo.setRecruitmentId(recruitmentInfoDo.getId());
        applicationDo.setRecruitmentRegisterCode(recruitmentInfoDo.getRegisterCode());
        applicationDo.setDepartmentId(applicationAddReq.getDepartmentId());
        applicationDo.setDiseaseDesc(applicationAddReq.getDiseaseDesc());
        applicationDo.setDiseaseImage(StringUtil.join(applicationAddReq.getDiseaseImageList(), ","));
        return applicationDo;
    }
}
