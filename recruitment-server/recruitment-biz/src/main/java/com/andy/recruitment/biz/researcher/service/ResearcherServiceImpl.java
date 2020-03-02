package com.andy.recruitment.biz.researcher.service;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.researcher.dao.ResearcherDAO;
import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.researcher.entity.ResearcherQuery;
import com.andy.recruitment.dao.user.dao.UserDAO;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import com.andy.spring.util.asserts.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 研究员服务接口实现
 *
 * @author 庞先海 2020-01-31
 */
@Service
public class ResearcherServiceImpl implements ResearcherService {

    private final ResearcherDAO researcherDAO;

    private final UserDAO userDAO;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public ResearcherServiceImpl(ResearcherDAO researcherDAO, UserDAO userDAO,
        TransactionTemplate transactionTemplate) {
        this.researcherDAO = researcherDAO;
        this.userDAO = userDAO;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void registerResearcher(ResearcherInfoDO researcherInfoDo, UserInfoDO userInfoDo, String operator) {
        transactionTemplate.execute((status) -> {
            Long userId = this.userDAO.registerUser(userInfoDo, operator);
            researcherInfoDo.setUserId(userId);
            ResearcherInfoDO existResearcherInfoDo = this.researcherDAO.getResearcherInfoByUserId(userId);
            if (existResearcherInfoDo == null) {
                this.researcherDAO.addResearcherInfo(researcherInfoDo, operator);
            } else {
                researcherInfoDo.setId(existResearcherInfoDo.getId());
                this.researcherDAO.updateResearcherInfo(researcherInfoDo, operator);
            }
            return null;
        });
    }

    @Override
    public void updateResearcher(ResearcherInfoDO researcherInfoDo, UserInfoDO userInfoDo, String operator) {
        ResearcherInfoDO sourceResearcherInfoDo = this.researcherDAO.getResearcherInfoByResearchId(
            researcherInfoDo.getId());
        AssertUtil.assertNull(sourceResearcherInfoDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RESEARCHER_NOT_EXIST);
        });
        transactionTemplate.execute((status) -> {
            this.researcherDAO.updateResearcherInfo(researcherInfoDo, operator);
            userInfoDo.setId(sourceResearcherInfoDo.getUserId());
            this.userDAO.updateUserInfo(userInfoDo, operator);
            return null;
        });
    }

    @Override
    public PageResult<ResearcherInfoDO> getResearcherInfo(ResearcherQuery query, Paginator paginator) {
        return this.researcherDAO.getResearcherInfo(query, paginator);
    }

    @Override
    public ResearcherInfoDO getResearcherInfoByResearchId(Long researcherId) {
        return this.researcherDAO.getResearcherInfoByResearchId(researcherId);
    }

    @Override
    public ResearcherInfoDO getResearcherInfoByUserId(Long userId) {
        return this.researcherDAO.getResearcherInfoByUserId(userId);
    }
}
