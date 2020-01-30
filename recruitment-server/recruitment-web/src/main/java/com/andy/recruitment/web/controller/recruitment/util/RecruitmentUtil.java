package com.andy.recruitment.web.controller.recruitment.util;

import com.andy.recruitment.dao.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentQueryReq;
import com.soyoung.base.util.DateUtil;
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
        return queryParam;
    }
}
