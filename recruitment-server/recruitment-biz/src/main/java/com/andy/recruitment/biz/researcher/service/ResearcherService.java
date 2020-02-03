package com.andy.recruitment.biz.researcher.service;

import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.researcher.entity.ResearcherQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;

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

    /**
     * 分页查询研究员
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 研究员信息
     */
    PageResult<ResearcherInfoDO> getResearcherInfo(ResearcherQuery query, Paginator paginator);
}
