package com.andy.recruitment.biz.researcher.service;

import com.andy.recruitment.dao.researcher.dao.ResearcherDAO;
import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.researcher.entity.ResearcherQuery;
import com.andy.recruitment.dao.user.dao.UserDAO;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;
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
    public PageResult<ResearcherInfoDO> getResearcherInfo(ResearcherQuery query, Paginator paginator) {
        return this.researcherDAO.getResearcherInfo(query, paginator);
    }
}
