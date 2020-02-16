package com.andy.recruitment.web.controller.patient.util;

import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.dao.patient.constant.PatientStatus;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.patient.entity.PatientQuery;
import com.andy.recruitment.dao.user.constant.Gender;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.patient.request.PatientAddReq;
import com.andy.recruitment.web.controller.patient.request.PatientQueryReq;
import com.andy.recruitment.web.controller.patient.response.PatientInfoRes;
import com.andy.recruitment.web.controller.user.response.UserInfoRes;
import com.andy.recruitment.web.controller.user.util.UserInfoUtil;
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

    public static PatientInfoRes transformReferenceRes(PatientInfoDO patientInfoDo) {
        List<PatientInfoDO> patientInfoDoList = Collections.singletonList(patientInfoDo);
        List<PatientInfoRes> patientInfoResList = PatientInfoUtil.transformReferenceRes(patientInfoDoList);
        if (CollectionUtils.isEmpty(patientInfoResList)) {
            return null;
        }
        return patientInfoResList.get(0);
    }

    public static List<PatientInfoRes> transformReferenceRes(List<PatientInfoDO> patientInfoDoList) {
        if (CollectionUtils.isEmpty(patientInfoDoList)) {
            return Collections.emptyList();
        }
        List<Long> userIdList = new ArrayList<>(patientInfoDoList.size());
        for (PatientInfoDO patientInfoDo : patientInfoDoList) {
            userIdList.add(patientInfoDo.getUserId());
        }
        Map<Long, UserInfoRes> userInfoResMap = UserInfoUtil.getUserInfoRes(userIdList);
        return patientInfoDoList.stream().map(
            referenceInfoDo -> PatientInfoUtil.transformReferenceRes(referenceInfoDo, userInfoResMap)).filter(
            Objects::nonNull).collect(Collectors.toList());

    }

    private static PatientInfoRes transformReferenceRes(PatientInfoDO patientInfoDo,
        Map<Long, UserInfoRes> userInfoResMap) {
        if (patientInfoDo == null) {
            return null;
        }
        PatientInfoRes patientInfoRes = new PatientInfoRes();
        BeanUtils.copyProperties(patientInfoDo, patientInfoRes);
        patientInfoRes.setPatientId(patientInfoDo.getId());
        patientInfoRes.setUserInfoRes(userInfoResMap.get(patientInfoDo.getUserId()));
        patientInfoRes.setAddress(
            regionService.parseAddressName(patientInfoDo.getProvinceId(), patientInfoDo.getCityId(),
                patientInfoDo.getDistrictId()));
        return patientInfoRes;
    }
}
