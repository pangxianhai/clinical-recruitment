package com.andy.recruitment.biz.researcher.service;

import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.researcher.entity.ResearcherQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;

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
     * 更新研究员信息
     *
     * @param researcherInfoDo 研究员信息
     * @param userInfoDo       用户信息
     * @param operator         操作人
     */
    void updateResearcher(ResearcherInfoDO researcherInfoDo, UserInfoDO userInfoDo, String operator);

    /**
     * 分页查询研究员
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 研究员信息
     */
    PageResult<ResearcherInfoDO> getResearcherInfo(ResearcherQuery query, Paginator paginator);

    /**
     * 通过研究员ID查询研究员信息
     *
     * @param researcherId 研究员ID
     * @return 研究员信息
     */
    ResearcherInfoDO getResearcherInfoByResearchId(Long researcherId);

    /**
     * 通过用户id查询研究员信息
     *
     * @param userId 用户ID
     * @return 研究员信息
     */
    ResearcherInfoDO getResearcherInfoByUserId(Long userId);
}
