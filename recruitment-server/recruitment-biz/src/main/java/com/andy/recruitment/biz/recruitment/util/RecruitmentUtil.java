package com.andy.recruitment.biz.recruitment.util;

import com.andy.recruitment.api.recruitment.response.RecruitmentInfoRes;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.spring.util.DateUtil;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

/**
 * 招募项目工具
 *
 * @author 庞先海 2020-03-07
 */
public class RecruitmentUtil {

    private final static String DATE_PATTERN = "yyyy-MM-dd";

    public static List<RecruitmentInfoRes> transformRecruitmentInfoRes(List<RecruitmentInfoDO> recruitmentInfoDoList) {
        if (CollectionUtils.isEmpty(recruitmentInfoDoList)) {
            return Collections.emptyList();
        }
        return recruitmentInfoDoList.stream().map(RecruitmentUtil::transformRecruitmentInfoRes).filter(
            Objects::nonNull).collect(Collectors.toList());
    }

    public static RecruitmentInfoRes transformRecruitmentInfoRes(RecruitmentInfoDO recruitmentInfoDo) {
        if (recruitmentInfoDo == null) {
            return null;
        }
        RecruitmentInfoRes recruitmentInfoRes = new RecruitmentInfoRes();
        BeanUtils.copyProperties(recruitmentInfoDo, recruitmentInfoRes);
        recruitmentInfoRes.setStartTime(DateUtil.format(recruitmentInfoDo.getStartTime(), DATE_PATTERN));
        recruitmentInfoRes.setStopTime(DateUtil.format(recruitmentInfoDo.getStopTime(), DATE_PATTERN));
        recruitmentInfoRes.setCreatedTime(DateUtil.format(recruitmentInfoDo.getCreatedTime(), DATE_PATTERN));
        recruitmentInfoRes.setRecruitmentId(recruitmentInfoDo.getId());
        return recruitmentInfoRes;
    }
}
