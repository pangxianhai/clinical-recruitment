package com.andy.recruitment.biz.researcher.service;

import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.user.entity.UserInfoDO;

/**
 * 研究员服务接口
 *
 * @author 庞先海 2020-01-31
 */
public interface ResearcherService {

    /**
     * 研究员注册
     *
     * @param researcherInfoDo 研究员信息
     * @param userInfoDo       用户信息
     * @param operator         操作人
     */
    void registerResearcher(ResearcherInfoDO researcherInfoDo, UserInfoDO userInfoDo, String operator);
}
