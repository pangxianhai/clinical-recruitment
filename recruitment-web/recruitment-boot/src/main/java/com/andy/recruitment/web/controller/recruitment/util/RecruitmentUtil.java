package com.andy.recruitment.web.controller.recruitment.util;

import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentAddRQ;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentVO;
import com.xgimi.commons.util.DateUtil;
import com.xgimi.util.BeanUtil;

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
}
