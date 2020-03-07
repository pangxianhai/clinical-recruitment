package com.andy.recruitment.web.controller.recruitment.util;

import com.andy.recruitment.biz.oss.service.OssService;
import com.andy.recruitment.biz.recruitment.service.RecruitmentService;
import com.andy.recruitment.biz.region.entity.AddressInfo;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.recruitment.constant.RecruitmentApplicationStatus;
import com.andy.recruitment.dao.recruitment.constant.RecruitmentCategory;
import com.andy.recruitment.dao.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationQuery;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.andy.recruitment.dao.user.constant.Gender;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.organization.response.OrganizationDepartmentDetailRes;
import com.andy.recruitment.web.controller.organization.util.OrganizationDepartmentUtil;
import com.andy.recruitment.web.controller.patient.response.PatientInfoDetailRes;
import com.andy.recruitment.web.controller.patient.util.PatientInfoUtil;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentAddReq;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationAddReq;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationQueryReq;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentQueryReq;
import com.andy.recruitment.web.controller.recruitment.response.ImageRes;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentApplicationDetailRes;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentInfoRes;
import com.andy.recruitment.web.controller.reference.response.ReferenceDetailInfoRes;
import com.andy.recruitment.web.controller.reference.util.ReferenceUtil;
import com.andy.spring.util.DateUtil;
import com.andy.spring.util.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
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

    private static OssService ossService;

    private static RecruitmentService recruitmentService;

    @Autowired
    public RecruitmentUtil(RegionService regionService, OssService ossService, RecruitmentService recruitmentService) {
        RecruitmentUtil.regionService = regionService;
        RecruitmentUtil.ossService = ossService;
        RecruitmentUtil.recruitmentService = recruitmentService;
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

    public static Map<Long, RecruitmentInfoRes> getRecruitmentInfoRes(List<Long> recruitmentIdList) {
        if (CollectionUtils.isEmpty(recruitmentIdList)) {
            return Collections.emptyMap();
        }
        List<RecruitmentInfoDO> recruitmentInfoDoList = recruitmentService.getRecruitmentInfo(recruitmentIdList);
        List<RecruitmentInfoRes> recruitmentInfoResList = transformRecruitmentInfoRes(recruitmentInfoDoList);
        if (CollectionUtils.isEmpty(recruitmentInfoResList)) {
            return Collections.emptyMap();
        }
        return recruitmentInfoResList.stream().collect(
            Collectors.toMap(RecruitmentInfoRes::getRecruitmentId, Function.identity(), (d1, d2) -> d1));
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

    public static List<RecruitmentApplicationDetailRes> transformApplicationDetailRes(
        List<RecruitmentApplicationDO> applicationDoList) {
        if (CollectionUtils.isEmpty(applicationDoList)) {
            return Collections.emptyList();
        }
        List<Long> recruitmentIdList = new ArrayList<>(applicationDoList.size());
        List<Long> departmentIdList = new ArrayList<>(applicationDoList.size());
        List<Long> patientUserIdList = new ArrayList<>(applicationDoList.size());
        List<Long> referenceUserIdList = new ArrayList<>(applicationDoList.size());
        for (RecruitmentApplicationDO applicationDo : applicationDoList) {
            if (applicationDo.getRecruitmentId() != null) {
                recruitmentIdList.add(applicationDo.getRecruitmentId());
            }
            if (applicationDo.getDepartmentId() != null) {
                departmentIdList.add(applicationDo.getDepartmentId());
            }
            if (applicationDo.getPatientUserId() != null) {
                patientUserIdList.add(applicationDo.getPatientUserId());
            }
            if (applicationDo.getReferenceUserId() != null) {
                referenceUserIdList.add(applicationDo.getReferenceUserId());
            }
        }

        Map<Long, RecruitmentInfoRes> recruitmentInfoResMap = getRecruitmentInfoRes(recruitmentIdList);
        Map<Long, OrganizationDepartmentDetailRes> departmentDetailResMap = OrganizationDepartmentUtil.getOrganizationDepartmentDetailRes(
            departmentIdList);
        Map<Long, PatientInfoDetailRes> patientInfoDetailResMap = PatientInfoUtil.getReferenceDetailRes(
            patientUserIdList);
        Map<Long, ReferenceDetailInfoRes> referenceDetailInfoResMap = ReferenceUtil.getReferenceDetailInfoRes(
            referenceUserIdList);

        List<RecruitmentApplicationDetailRes> applicationDetailResList = new ArrayList<>(applicationDoList.size());
        for (RecruitmentApplicationDO applicationDo : applicationDoList) {
            RecruitmentApplicationDetailRes applicationDetailRes = transformApplicationDetailRes(applicationDo);
            if (applicationDetailRes == null) {
                continue;
            }
            if (MapUtils.isNotEmpty(recruitmentInfoResMap)) {
                applicationDetailRes.setRecruitmentInfoRes(recruitmentInfoResMap.get(applicationDo.getRecruitmentId()));
            }
            if (MapUtils.isNotEmpty(departmentDetailResMap)) {
                applicationDetailRes.setDepartmentDetailRes(
                    departmentDetailResMap.get(applicationDo.getDepartmentId()));
            }
            if (MapUtils.isNotEmpty(patientInfoDetailResMap)) {
                applicationDetailRes.setPatientInfoDetailRes(
                    patientInfoDetailResMap.get(applicationDo.getPatientUserId()));
            }
            if (MapUtils.isNotEmpty(referenceDetailInfoResMap)) {
                applicationDetailRes.setReferenceDetailInfoRes(
                    referenceDetailInfoResMap.get(applicationDo.getReferenceUserId()));
            }
            applicationDetailResList.add(applicationDetailRes);
        }
        return applicationDetailResList;
    }

    private static RecruitmentApplicationDetailRes transformApplicationDetailRes(
        RecruitmentApplicationDO applicationDo) {
        if (applicationDo == null) {
            return null;
        }
        RecruitmentApplicationDetailRes applicationDetailRes = new RecruitmentApplicationDetailRes();
        BeanUtils.copyProperties(applicationDo, applicationDetailRes);
        applicationDetailRes.setDiseaseImageList(buildImageRes(applicationDo.getDiseaseImage()));
        applicationDetailRes.setApplicationId(applicationDo.getId());
        applicationDetailRes.setApplicationTime(DateUtil.format(applicationDo.getCreatedTime(), "yyyy-MM-dd"));
        return applicationDetailRes;
    }

    private static List<ImageRes> buildImageRes(String diseaseImage) {
        if (StringUtils.isEmpty(diseaseImage)) {
            return Collections.emptyList();
        }
        String[] diseaseImageArr = diseaseImage.split(DISEASE_IMAGE_SPACE);
        List<ImageRes> imageResList = new ArrayList<>(diseaseImageArr.length);
        for (String image : diseaseImageArr) {
            String imageUrl = ossService.generateUrl(image);
            String thumbnailUrl = ossService.generateUrl(image, 80, 80);
            ImageRes imageRes = new ImageRes(imageUrl, thumbnailUrl);
            imageResList.add(imageRes);
        }
        return imageResList;
    }
}
