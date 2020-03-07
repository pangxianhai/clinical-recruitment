package com.andy.recruitment.biz.recruitment.service;

import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.dao.organization.dao.OrganizationDAO;
import com.andy.recruitment.dao.organization.dao.OrganizationDepartmentDAO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.dao.recruitment.dao.RecruitmentDAO;
import com.andy.recruitment.dao.recruitment.dao.RecruitmentOrganizationDAO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentOrganizationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    private final OrganizationDepartmentDAO organizationDepartmentDAO;

    private final RegionService regionService;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public RecruitmentServiceImpl(RecruitmentDAO recruitmentDAO, RecruitmentOrganizationDAO recruitmentOrganizationDAO,
        OrganizationDAO organizationDAO, OrganizationDepartmentDAO organizationDepartmentDAO,
        RegionService regionService, TransactionTemplate transactionTemplate) {
        this.recruitmentDAO = recruitmentDAO;
        this.recruitmentOrganizationDAO = recruitmentOrganizationDAO;
        this.organizationDAO = organizationDAO;
        this.organizationDepartmentDAO = organizationDepartmentDAO;
        this.regionService = regionService;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public PageResult<RecruitmentInfoDO> getRecruitmentInfo(RecruitmentQuery queryParam, Paginator paginator) {
        return this.recruitmentDAO.getRecruitmentInfo(queryParam, paginator);
    }

    @Override
    public List<RecruitmentInfoDO> getRecruitmentInfo(List<Long> recruitmentIdList) {
        return this.recruitmentDAO.getRecruitmentInfo(recruitmentIdList);
    }

    @Override
    public void addRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, List<List<Long>> organizationDepartmentList,
        String operator) {
        transactionTemplate.execute((status) -> {
            Long recruitmentId = this.recruitmentDAO.addRecruitmentInfo(recruitmentInfoDo, operator);
            if (CollectionUtils.isEmpty(organizationDepartmentList)) {
                return null;
            }
            this.updateOrganizationDepartment(recruitmentId, organizationDepartmentList, operator);
            return null;
        });
    }

    @Override
    public RecruitmentInfoDO getRecruitmentInfo(Long recruitmentId) {
        return this.recruitmentDAO.getRecruitmentInfoById(recruitmentId);
    }

    @Override
    public List<OrganizationDepartmentDO> getOrganizationByRecruitment(Long recruitmentId) {
        List<RecruitmentOrganizationDO> recruitmentOrganizationDoList = recruitmentOrganizationDAO.listOrganizationByRecruitment(
            recruitmentId);
        if (CollectionUtils.isEmpty(recruitmentOrganizationDoList)) {
            return null;
        }
        List<Long> departmentIdList = recruitmentOrganizationDoList.stream().map(
            RecruitmentOrganizationDO::getDepartmentId).collect(Collectors.toList());
        return organizationDepartmentDAO.getOrganizationDepartment(departmentIdList);
    }

    @Override
    public void updateRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, List<List<Long>> organizationDepartmentList,
        String operator) {
        transactionTemplate.execute((status) -> {
            this.recruitmentDAO.updateRecruitmentInfo(recruitmentInfoDo, operator);
            this.updateOrganizationDepartment(recruitmentInfoDo.getId(), organizationDepartmentList, operator);
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

    @Override
    public List<Long> listDepartmentByRecruitment(Long recruitmentId) {
        return this.recruitmentOrganizationDAO.listDepartmentByRecruitment(recruitmentId);
    }

    private void updateOrganizationDepartment(Long recruitmentId, List<List<Long>> organizationDepartmentList,
        String operator) {
        List<Long> sourceDepartmentIdList = recruitmentOrganizationDAO.listDepartmentByRecruitment(recruitmentId);
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
        needAddDepartmentIdList.removeAll(sourceDepartmentIdList);
        List<Long> needDeleteOrganizationIdList = new ArrayList<>(sourceDepartmentIdList);
        needDeleteOrganizationIdList.removeAll(departmentIdList);
        for (Long departmentId : needAddDepartmentIdList) {
            this.recruitmentOrganizationDAO.addRecruitmentOrganization(recruitmentId,
                departmentOrganizationMap.get(departmentId), departmentId, operator);
        }
        for (Long departmentId : needDeleteOrganizationIdList) {
            this.recruitmentOrganizationDAO.deleteRecruitmentOrganization(recruitmentId, departmentId);
        }
    }
}
