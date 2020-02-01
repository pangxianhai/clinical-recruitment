package com.andy.recruitment.biz.recruitment.service;

import com.andy.recruitment.biz.recruitment.entity.DepartmentInfoBO;
import com.andy.recruitment.dao.organization.dao.OrganizationDAO;
import com.andy.recruitment.dao.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.dao.recruitment.dao.RecruitmentDAO;
import com.andy.recruitment.dao.recruitment.dao.RecruitmentOrganizationDAO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentOrganizationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void addRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, List<List<Long>> organizationDepartmentList,
        String operator) {
        transactionTemplate.execute((status) -> {
            Long recruitmentId = this.recruitmentDAO.addRecruitmentInfo(recruitmentInfoDo, operator);
            if (CollectionUtils.isEmpty(organizationDepartmentList)) {
                return null;
            }
            for (List<Long> organizationDepartmentId : organizationDepartmentList) {
                this.recruitmentOrganizationDAO.addRecruitmentOrganization(recruitmentId,
                    organizationDepartmentId.get(0), organizationDepartmentId.get(1), operator);
            }
            return null;
        });
    }

    @Override
    public RecruitmentInfoDO getRecruitmentInfo(Long recruitmentId) {
        return this.recruitmentDAO.getRecruitmentInfoById(recruitmentId);
    }

    @Override
    public List<DepartmentInfoBO> getOrganizationByRecruitment(Long recruitmentId) {
        List<RecruitmentOrganizationDO> recruitmentOrganizationDoList = recruitmentOrganizationDAO.listOrganizationByRecruitment(
            recruitmentId);
        if (CollectionUtils.isEmpty(recruitmentOrganizationDoList)) {
            return null;
        }
        List<DepartmentInfoBO> departmentInfoBoList = new ArrayList<>(recruitmentOrganizationDoList.size());
        for (RecruitmentOrganizationDO recruitmentOrganizationDo : recruitmentOrganizationDoList) {
            DepartmentInfoBO departmentInfoBo = new DepartmentInfoBO();
            departmentInfoBo.setOrganizationId(recruitmentOrganizationDo.getOrganizationId());
            departmentInfoBo.setDepartmentId(recruitmentOrganizationDo.getDepartmentId());
            departmentInfoBoList.add(departmentInfoBo);
        }
        return departmentInfoBoList;
    }

    @Override
    public void updateRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, List<List<Long>> organizationDepartmentList,
        String operator) {
        List<Long> sourceDepartmentIdList = recruitmentOrganizationDAO.listDepartmentByRecruitment(
            recruitmentInfoDo.getId());
        if (sourceDepartmentIdList == null) {
            sourceDepartmentIdList = new ArrayList<>(0);
        }
        if (organizationDepartmentList == null) {
            organizationDepartmentList = new ArrayList<>(0);
        }
        List<Long> departmentIdList = new ArrayList<>();
        //科室ID与机构ID对应 key为科室ID value为机构ID
        Map<Long, Long> departmentOrganizationMap = new HashMap<>(organizationDepartmentList.size());
        for (List<Long> organizationDepartmentId : organizationDepartmentList) {
            departmentIdList.add(organizationDepartmentId.get(1));
            departmentOrganizationMap.put(organizationDepartmentId.get(1), organizationDepartmentId.get(0));
        }

        List<Long> needAddDepartmentIdList = new ArrayList<>(departmentIdList);
        departmentIdList.removeAll(sourceDepartmentIdList);
        List<Long> needDeleteOrganizationIdList = new ArrayList<>(sourceDepartmentIdList);
        needDeleteOrganizationIdList.removeAll(departmentIdList);
        transactionTemplate.execute((status) -> {
            this.recruitmentDAO.updateRecruitmentInfo(recruitmentInfoDo, operator);
            for (Long departmentId : needAddDepartmentIdList) {
                this.recruitmentOrganizationDAO.addRecruitmentOrganization(recruitmentInfoDo.getId(),
                    departmentOrganizationMap.get(departmentId), departmentId, operator);
            }
            for (Long departmentId : needDeleteOrganizationIdList) {
                this.recruitmentOrganizationDAO.deleteRecruitmentOrganization(recruitmentInfoDo.getId(), departmentId);
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
