package com.andy.recruitment.biz.recruitment.service;

import com.andy.recruitment.dao.recruitment.dao.RecruitmentDAO;
import com.andy.recruitment.dao.recruitment.dao.RecruitmentOrganizationDAO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 招募项目服务接口实现
 *
 * @author 庞先海 2020-01-30
 */
@Service
public class RecruitmentServiceImpl implements RecruitmentService {

    private final RecruitmentDAO recruitmentDAO;

    private final RecruitmentOrganizationDAO recruitmentOrganizationDAO;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public RecruitmentServiceImpl(RecruitmentDAO recruitmentDAO, RecruitmentOrganizationDAO recruitmentOrganizationDAO,
        TransactionTemplate transactionTemplate) {
        this.recruitmentDAO = recruitmentDAO;
        this.recruitmentOrganizationDAO = recruitmentOrganizationDAO;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public PageResult<RecruitmentInfoDO> getRecruitmentInfo(RecruitmentQuery queryParam, Paginator paginator) {
        return this.recruitmentDAO.getRecruitmentInfo(queryParam, paginator);
    }

    @Override
    public void addRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, List<Long> organizationIdList,
        String operator) {
        transactionTemplate.execute((status) -> {
            Long recruitmentId = this.recruitmentDAO.addRecruitmentInfo(recruitmentInfoDo, operator);
            if (CollectionUtils.isEmpty(organizationIdList)) {
                return null;
            }
            for (Long organizationId : organizationIdList) {
                this.recruitmentOrganizationDAO.addRecruitmentOrganization(recruitmentId, organizationId, operator);
            }
            return null;
        });
    }
}
