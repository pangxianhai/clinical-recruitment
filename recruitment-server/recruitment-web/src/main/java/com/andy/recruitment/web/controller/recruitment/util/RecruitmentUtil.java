package com.andy.recruitment.web.controller.recruitment.util;

import com.andy.recruitment.dao.recruitment.constant.RecruitmentCategory;
import com.andy.recruitment.dao.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentAddReq;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentQueryReq;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentInfoRes;
import com.soyoung.base.util.DateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * 招募项目工具
 *
 * @author 庞先海 2020-01-30
 */
public class RecruitmentUtil {

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
}
