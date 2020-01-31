package com.andy.recruitment.web.controller.researcher.util;

import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.user.constant.Gender;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.researcher.request.ResearcherAddReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 研究员工具
 *
 * @author 庞先海 2020-01-31
 */
@Component
public class ResearcherUtil {

    private static RegionService regionService;

    @Autowired
    public ResearcherUtil(RegionService regionService) {
        ResearcherUtil.regionService = regionService;
    }

    public static ResearcherInfoDO transformResearcherInfo(ResearcherAddReq researcherAddReq) {
        if (null == researcherAddReq) {
            return null;
        }
        ResearcherInfoDO researcherInfoDo = new ResearcherInfoDO();
        BeanUtils.copyProperties(researcherAddReq, researcherInfoDo);
        return researcherInfoDo;
    }


    public static UserInfoDO transformUserInfo(ResearcherAddReq researcherAddReq) {
        if (null == researcherAddReq) {
            return null;
        }
        UserInfoDO userInfoDo = new UserInfoDO();
        BeanUtils.copyProperties(researcherAddReq, userInfoDo);
        userInfoDo.setGender(Gender.parse(researcherAddReq.getGender()));
        userInfoDo.setRealName(researcherAddReq.getName());
        return userInfoDo;
    }
}
