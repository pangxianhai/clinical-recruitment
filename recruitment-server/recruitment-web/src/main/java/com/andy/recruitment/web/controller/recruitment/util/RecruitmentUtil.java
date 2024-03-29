package com.andy.recruitment.web.controller.recruitment.util;

import com.andy.recruitment.api.recruitment.request.RecruitmentAddReq;
import com.andy.recruitment.api.recruitment.request.RecruitmentApplicationAddReq;
import com.andy.recruitment.api.recruitment.request.RecruitmentApplicationQueryReq;
import com.andy.recruitment.api.recruitment.request.RecruitmentQueryReq;
import com.andy.recruitment.biz.region.entity.AddressInfo;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.common.recruitment.constant.RecruitmentApplicationStatus;
import com.andy.recruitment.common.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.common.user.constant.Gender;
import com.andy.recruitment.dao.hospital.entity.DepartmentDO;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationQuery;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.spring.util.DateUtil;
import com.andy.spring.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
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

    private final static String DISEASE_IMAGE_SPACE = ",";

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
        return recruitmentInfoDo;
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
        applicationDo.setDiseaseImage(StringUtil.join(applicationAddReq.getDiseaseImageList(), DISEASE_IMAGE_SPACE));
        return applicationDo;
    }

    public static RecruitmentApplicationQuery transformRecruitmentApplicationQuery(
        RecruitmentApplicationQueryReq queryReq) {
        if (queryReq == null) {
            return null;
        }
        RecruitmentApplicationQuery query = new RecruitmentApplicationQuery();
        BeanUtils.copyProperties(queryReq, query);
        query.setStatus(RecruitmentApplicationStatus.parse(queryReq.getStatus()));
        return query;
    }

    public static List<DepartmentDO> parseDepartmentDo(List<List<Long>> hospitalDepartmentList) {
        //格式如：[[机构id,科室id],[机构id,科室id],]
        if (CollectionUtils.isEmpty(hospitalDepartmentList)) {
            return null;
        }
        List<DepartmentDO> departmentDoList = new ArrayList<>(16);
        for (List<Long> hospitalDepartment : hospitalDepartmentList) {
            if (hospitalDepartment.size() != 2) {
                continue;
            }
            DepartmentDO departmentDo = new DepartmentDO();
            departmentDo.setHospitalId(hospitalDepartment.get(0));
            departmentDo.setId(hospitalDepartment.get(1));
            departmentDoList.add(departmentDo);
        }
        return departmentDoList;
    }
}
