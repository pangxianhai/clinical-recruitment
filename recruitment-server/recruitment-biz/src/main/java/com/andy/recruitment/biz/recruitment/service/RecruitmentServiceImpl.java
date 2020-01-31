package com.andy.recruitment.biz.recruitment.service;

import com.andy.recruitment.dao.organization.dao.OrganizationDAO;
import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.dao.recruitment.dao.RecruitmentDAO;
import com.andy.recruitment.dao.recruitment.dao.RecruitmentOrganizationDAO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;
import java.util.ArrayList;
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

    private final OrganizationDAO organizationDAO;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public RecruitmentServiceImpl(RecruitmentDAO recruitmentDAO, RecruitmentOrganizationDAO recruitmentOrganizationDAO,
        OrganizationDAO organizationDAO, TransactionTemplate transactionTemplate) {
        this.recruitmentDAO = recruitmentDAO;
        this.recruitmentOrganizationDAO = recruitmentOrganizationDAO;
        this.organizationDAO = organizationDAO;
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

    @Override
    public RecruitmentInfoDO getRecruitmentInfo(Long recruitmentId) {
        return this.recruitmentDAO.getRecruitmentInfoById(recruitmentId);
    }

    @Override
    public List<OrganizationDO> getOrganizationByRecruitment(Long recruitmentId) {
        List<Long> organizationIdList = recruitmentOrganizationDAO.listOrganizationByRecruitment(recruitmentId);
        return this.organizationDAO.getOrganization(organizationIdList);
    }

    @Override
    public void updateRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, List<Long> organizationIdList,
        String operator) {
        List<Long> sourceOrganizationIdList = recruitmentOrganizationDAO.listOrganizationByRecruitment(
            recruitmentInfoDo.getId());
        if (sourceOrganizationIdList == null) {
            sourceOrganizationIdList = new ArrayList<>(0);
        }
        if (organizationIdList == null) {
            organizationIdList = new ArrayList<>(0);
        }
        List<Long> needAddOrganizationIdList = new ArrayList<>(organizationIdList);
        needAddOrganizationIdList.removeAll(sourceOrganizationIdList);
        List<Long> needDeleteOrganizationIdList = new ArrayList<>(sourceOrganizationIdList);
        needDeleteOrganizationIdList.removeAll(organizationIdList);
        transactionTemplate.execute((status) -> {
            this.recruitmentDAO.updateRecruitmentInfo(recruitmentInfoDo, operator);
            for (Long organizationId : needAddOrganizationIdList) {
                this.recruitmentOrganizationDAO.addRecruitmentOrganization(recruitmentInfoDo.getId(), organizationId,
                    operator);
            }
            for (Long organizationId : needDeleteOrganizationIdList) {
                this.recruitmentOrganizationDAO.deleteRecruitmentOrganization(recruitmentInfoDo.getId(),
                    organizationId);
            }
            return null;
        });
    }

    @Override
    public void updateRecruitmentInfoStatus(Long recruitmentId, RecruitmentStatus status, String operator) {
        RecruitmentInfoDO recruitmentInfoDo = new RecruitmentInfoDO();
        recruitmentInfoDo.setId(recruitmentId);
        recruitmentInfoDo.setStatus(status);
        this.recruitmentDAO.updateRecruitmentInfo(recruitmentInfoDo, operator);
    }
}
