package com.andy.recruitment.recruitment.util;

import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.recruitment.model.RecruitmentInfoDO;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.util.BeanUtil;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 招募信息工具类
 *
 * @author 庞先海 2018-12-29
 */
public class RecruitmentUtil {

    public static RecruitmentInfo transformRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDO) {
        if (null == recruitmentInfoDO) {
            return null;
        }
        RecruitmentInfo recruitmentInfo = new RecruitmentInfo();
        BeanUtil.copyProperties(recruitmentInfoDO, recruitmentInfo);
        recruitmentInfo.setRecruitmentId(recruitmentInfoDO.getId());
        return recruitmentInfo;
    }

    public static List<RecruitmentInfo> transformRecruitmentInfo(List<RecruitmentInfoDO> recruitmentInfoDOList) {
        if (CollectionUtil.isEmpty(recruitmentInfoDOList)) {
            return null;
        }
        return recruitmentInfoDOList.stream().map(RecruitmentUtil::transformRecruitmentInfo).filter(
            Objects::nonNull).collect(Collectors.toList());
    }

    public static RecruitmentInfoDO transformRecruitmentInfoDO(RecruitmentInfo recruitmentInfo) {
        if (null == recruitmentInfo) {
            return null;
        }
        RecruitmentInfoDO recruitmentInfoDO = new RecruitmentInfoDO();
        BeanUtil.copyProperties(recruitmentInfo, recruitmentInfoDO);
        if (null != recruitmentInfo.getStartTime()) {
            recruitmentInfoDO.setStartTime(new Timestamp(recruitmentInfo.getStartTime().getTime()));
        }
        if (null != recruitmentInfo.getStopTime()) {
            recruitmentInfoDO.setStopTime(new Timestamp(recruitmentInfo.getStopTime().getTime()));
        }
        return recruitmentInfoDO;
    }
}
