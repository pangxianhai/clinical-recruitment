package com.andy.recruitment.web.controller.recruitment.util;

import com.andy.recruitment.recruitment.constant.RecruitmentApplicationStatus;
import com.andy.recruitment.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationInfo;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationQueryParam;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.recruitment.model.RecruitmentQueryParam;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.researchcenter.model.ResearchCenterInfo;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentAddRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationQueryRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationUpdateRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentQueryRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentUpdateRQ;
import com.andy.recruitment.web.controller.recruitment.request.ResearchCenterAddRQ;
import com.andy.recruitment.web.controller.recruitment.request.ResearchCenterUpdateRQ;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentApplicationVO;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentVO;
import com.andy.recruitment.web.controller.recruitment.response.ResearchCenterVO;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.DateUtil;
import com.xgimi.commons.util.StringUtil;
import com.xgimi.util.BeanUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 招募信息工具
 *
 * @author 庞先海 2019-01-06
 */
public class RecruitmentUtil {

    public static RecruitmentInfo transformRecruitmentInfo(RecruitmentAddRQ recruitmentAddRQ) {
        if (null == recruitmentAddRQ) {
            return null;
        }
        RecruitmentInfo recruitmentInfo = new RecruitmentInfo();
        BeanUtil.copyProperties(recruitmentAddRQ, recruitmentInfo);
        if (StringUtil.isNotEmpty(recruitmentAddRQ.getStartTime())) {
            String startTime = recruitmentAddRQ.getStartTime() + " 00:00:00";
            recruitmentInfo.setStartTime(DateUtil.parse(startTime));
        }
        if (StringUtil.isNotEmpty(recruitmentAddRQ.getStopTime())) {
            String stopTime = recruitmentAddRQ.getStopTime() + " 23:59:59";
            recruitmentInfo.setStopTime(DateUtil.parse(stopTime));
        }
        return recruitmentInfo;
    }

    public static RecruitmentVO transformRecruitmentVO(RecruitmentInfo recruitmentInfo) {
        if (null == recruitmentInfo) {
            return null;
        }
        RecruitmentVO recruitmentVO = new RecruitmentVO();
        BeanUtil.copyProperties(recruitmentInfo, recruitmentVO);
        recruitmentVO.setStartTime(DateUtil.format(recruitmentInfo.getStartTime(), "yyyy-MM-dd"));
        recruitmentVO.setStopTime(DateUtil.format(recruitmentInfo.getStopTime(), "yyyy-MM-dd"));
        recruitmentVO.setCreatedTime(DateUtil.format(recruitmentInfo.getCreatedTime()));
        return recruitmentVO;
    }

    public static List<RecruitmentVO> transformRecruitmentVO(List<RecruitmentInfo> recruitmentInfoList) {
        if (CollectionUtil.isEmpty(recruitmentInfoList)) {
            return new ArrayList<>(0);
        }
        return recruitmentInfoList.stream().map(RecruitmentUtil::transformRecruitmentVO).filter(
            Objects::nonNull).collect(Collectors.toList());
    }

    public static RecruitmentApplicationInfo transformApplicationInfo(RecruitmentApplicationRQ applicationRQ) {
        if (null == applicationRQ) {
            return null;
        }
        RecruitmentApplicationInfo applicationInfo = new RecruitmentApplicationInfo();
        BeanUtil.copyProperties(applicationRQ, applicationInfo);
        return applicationInfo;
    }

    public static RecruitmentApplicationQueryParam transformApplicationQueryParam(
        RecruitmentApplicationQueryRQ queryParamRQ) {
        if (null == queryParamRQ) {
            return null;
        }
        RecruitmentApplicationQueryParam queryParam = new RecruitmentApplicationQueryParam();
        BeanUtil.copyProperties(queryParamRQ, queryParam);
        queryParam.setStatus(RecruitmentApplicationStatus.parse(queryParamRQ.getStatus()));
        return queryParam;
    }

    public static RecruitmentApplicationVO transformApplicationVO(RecruitmentApplicationInfo applicationInfo) {
        if (null == applicationInfo) {
            return null;
        }
        RecruitmentApplicationVO applicationVO = new RecruitmentApplicationVO();
        BeanUtil.copyProperties(applicationInfo, applicationVO);
        applicationVO.setApplicationTime(DateUtil.format(applicationInfo.getApplicationTime(), "yyyy-MM-dd"));
        return applicationVO;
    }

    public static List<RecruitmentApplicationVO> transformApplicationVO(
        List<RecruitmentApplicationInfo> applicationInfoList) {
        if (CollectionUtil.isEmpty(applicationInfoList)) {
            return new ArrayList<>(0);
        }
        return applicationInfoList.stream().map(RecruitmentUtil::transformApplicationVO).filter(
            Objects::nonNull).collect(Collectors.toList());
    }

    public static RecruitmentApplicationInfo transformApplicationInfo(RecruitmentApplicationUpdateRQ updateRQ) {
        if (null == updateRQ) {
            return null;
        }
        RecruitmentApplicationInfo applicationInfo = new RecruitmentApplicationInfo();
        BeanUtil.copyProperties(updateRQ, applicationInfo);
        applicationInfo.setStatus(RecruitmentApplicationStatus.parse(updateRQ.getStatus()));
        return applicationInfo;
    }

    public static ResearchCenterInfo transformResearchCenterInfo(ResearchCenterAddRQ addRQ) {
        if (null == addRQ) {
            return null;
        }
        ResearchCenterInfo researchCenterInfo = new ResearchCenterInfo();
        BeanUtil.copyProperties(addRQ, researchCenterInfo);
        return researchCenterInfo;
    }

    public static List<ResearchCenterInfo> transformResearchCenterInfo(List<ResearchCenterAddRQ> addRQList) {
        if (CollectionUtil.isEmpty(addRQList)) {
            return null;
        }
        return addRQList.stream().map(RecruitmentUtil::transformResearchCenterInfo).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static ResearchCenterInfo transformResearchCenterInfo(ResearchCenterUpdateRQ updateRQ) {
        if (null == updateRQ) {
            return null;
        }
        ResearchCenterInfo researchCenterInfo = new ResearchCenterInfo();
        BeanUtil.copyProperties(updateRQ, researchCenterInfo);
        return researchCenterInfo;
    }

    public static List<ResearchCenterInfo> transformResearchCenterInfoByUpdate(
        List<ResearchCenterUpdateRQ> updateRQList) {
        if (CollectionUtil.isEmpty(updateRQList)) {
            return null;
        }
        return updateRQList.stream().map(RecruitmentUtil::transformResearchCenterInfo).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static ResearchCenterVO transformResearchCenterVO(RegionAO regionAO, ResearchCenterInfo centerInfo) {
        if (null == centerInfo) {
            return null;
        }
        ResearchCenterVO centerVO = new ResearchCenterVO();
        BeanUtil.copyProperties(centerInfo, centerVO);
        String address = regionAO.parseAddressName(centerInfo.getProvinceId(), centerInfo.getCityId(),
                                                   centerInfo.getDistrictId());
        centerVO.setAddress(address);
        return centerVO;
    }

    public static List<ResearchCenterVO> transformResearchCenterVO(RegionAO regionAO,
                                                                   List<ResearchCenterInfo> centerInfoList) {
        if (CollectionUtil.isEmpty(centerInfoList)) {
            return new ArrayList<>(0);
        }
        return centerInfoList.stream().map(
            (centerInfo) -> RecruitmentUtil.transformResearchCenterVO(regionAO, centerInfo)).filter(
            Objects::nonNull).collect(Collectors.toList());
    }

    public static RecruitmentQueryParam transformQueryParam(RecruitmentQueryRQ queryRQ) {
        if (null == queryRQ) {
            return null;
        }
        RecruitmentQueryParam queryParam = new RecruitmentQueryParam();
        BeanUtil.copyProperties(queryRQ, queryParam);
        queryParam.setStartTimeBegin(DateUtil.parse(queryRQ.getStartTimeBegin(), "yyyy-MM-dd"));
        if (StringUtil.isNotEmpty(queryRQ.getStartTimeEnd())) {
            queryParam.setStartTimeEnd(DateUtil.parse(queryRQ.getStartTimeEnd() + " 23:59:59"));
        }
        queryParam.setStopTimeBegin(DateUtil.parse(queryRQ.getStopTimeBegin(), "yyyy-MM-dd"));
        if (StringUtil.isNotEmpty(queryRQ.getStopTimeEnd())) {
            queryParam.setStopTimeEnd(DateUtil.parse(queryRQ.getStopTimeEnd() + " 23:59:59"));
        }
        queryParam.setStatus(RecruitmentStatus.parse(queryRQ.getStatus()));
        return queryParam;
    }

    public static RecruitmentInfo transformRecruitmentInfo(RecruitmentUpdateRQ recruitmentUpdateRQ) {
        if (null == recruitmentUpdateRQ) {
            return null;
        }
        RecruitmentInfo recruitmentInfo = new RecruitmentInfo();
        BeanUtil.copyProperties(recruitmentUpdateRQ, recruitmentInfo);
        if (StringUtil.isNotEmpty(recruitmentUpdateRQ.getStartTime())) {
            String startTime = recruitmentUpdateRQ.getStartTime() + " 00:00:00";
            recruitmentInfo.setStartTime(DateUtil.parse(startTime));
        }
        if (StringUtil.isNotEmpty(recruitmentUpdateRQ.getStopTime())) {
            String stopTime = recruitmentUpdateRQ.getStopTime() + " 23:59:59";
            recruitmentInfo.setStopTime(DateUtil.parse(stopTime));
        }
        return recruitmentInfo;
    }
}
