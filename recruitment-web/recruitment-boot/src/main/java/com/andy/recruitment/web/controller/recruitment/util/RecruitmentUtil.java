package com.andy.recruitment.web.controller.recruitment.util;

import com.andy.recruitment.recruitment.constant.RecruitmentApplicationStatus;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationInfo;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationQueryParam;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentAddRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationQueryParamRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationUpdateRQ;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentApplicationVO;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentVO;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.DateUtil;
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
        recruitmentInfo.setStartTime(DateUtil.parse(recruitmentAddRQ.getStartTime()));
        recruitmentInfo.setStopTime(DateUtil.parse(recruitmentAddRQ.getStopTime()));
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
        return recruitmentVO;
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
        RecruitmentApplicationQueryParamRQ queryParamRQ) {
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
        applicationVO.setApplicationTime(DateUtil.format(applicationInfo.getApplicationTime()));
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
}
