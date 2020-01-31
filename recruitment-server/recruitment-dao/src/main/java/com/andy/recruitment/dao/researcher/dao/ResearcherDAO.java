package com.andy.recruitment.dao.researcher.dao;

import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.researcher.entity.ResearcherQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;

/**
 * 研究员DAO接口
 *
 * @author 庞先海 2020-01-27
 */
public interface ResearcherDAO {

    /**
     * 分页查询研究员
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 研究员信息
     */
    PageResult<ResearcherInfoDO> getResearcherInfo(ResearcherQuery query, Paginator paginator);

    /**
     * 通过用户ID查询研究员信息
     *
     * @param userId 用户ID
     * @return 研究员信息
     */
    ResearcherInfoDO getResearcherInfoByUserId(Long userId);

    /**
     * 更新研究员信息
     *
     * @param researcherInfoDo 研究员信息
     * @param operator         操作人
     */
    void updateResearcherInfo(ResearcherInfoDO researcherInfoDo, String operator);

    /**
     * 添加研究员信息
     *
     * @param researcherInfoDo 研究员信息
     * @param operator         操作人
     */
    void addResearcherInfo(ResearcherInfoDO researcherInfoDo, String operator);
}
