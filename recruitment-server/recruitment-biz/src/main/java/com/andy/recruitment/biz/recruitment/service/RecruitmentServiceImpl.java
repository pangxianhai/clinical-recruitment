package com.andy.recruitment.biz.recruitment.service;

import com.andy.recruitment.api.hospital.response.DepartmentDetailRes;
import com.andy.recruitment.api.recruitment.response.RecruitmentInfoDetailRes;
import com.andy.recruitment.api.recruitment.response.RecruitmentInfoRes;
import com.andy.recruitment.biz.hospital.service.DepartmentService;
import com.andy.recruitment.biz.recruitment.util.RecruitmentUtil;
import com.andy.recruitment.common.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.dao.hospital.entity.DepartmentDO;
import com.andy.recruitment.dao.recruitment.dao.RecruitmentDAO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentDepartmentDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
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

    private final RecruitmentDepartmentService recruitmentDepartmentService;

    private final DepartmentService departmentService;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public RecruitmentServiceImpl(RecruitmentDAO recruitmentDAO,
        RecruitmentDepartmentService recruitmentDepartmentService, DepartmentService departmentService,
        TransactionTemplate transactionTemplate) {
        this.recruitmentDAO = recruitmentDAO;
        this.recruitmentDepartmentService = recruitmentDepartmentService;
        this.departmentService = departmentService;
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
    public Map<Long, RecruitmentInfoRes> getRecruitmentInfoRes(List<Long> recruitmentIdList) {
        if (CollectionUtils.isEmpty(recruitmentIdList)) {
            return Collections.emptyMap();
        }
        List<RecruitmentInfoDO> recruitmentInfoDoList = this.recruitmentDAO.getRecruitmentInfo(recruitmentIdList);
        List<RecruitmentInfoRes> recruitmentInfoResList = RecruitmentUtil.transformRecruitmentInfoRes(
            recruitmentInfoDoList);
        if (CollectionUtils.isEmpty(recruitmentInfoResList)) {
            return Collections.emptyMap();
        }
        return recruitmentInfoResList.stream().collect(
            Collectors.toMap(RecruitmentInfoRes::getRecruitmentId, Function.identity(), (d1, d2) -> d1));

    }

    @Override
    public void addRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, List<DepartmentDO> departmentDoList,
        String operator) {
        transactionTemplate.execute((status) -> {
            Long recruitmentId = this.recruitmentDAO.addRecruitmentInfo(recruitmentInfoDo, operator);
            if (CollectionUtils.isEmpty(departmentDoList)) {
                return null;
            }
            this.recruitmentDepartmentService.updateRecruitmentDepartment(recruitmentId, departmentDoList, operator);
            return null;
        });
    }

    @Override
    public RecruitmentInfoDO getRecruitmentInfo(Long recruitmentId) {
        return this.recruitmentDAO.getRecruitmentInfoById(recruitmentId);
    }

    @Override
    public RecruitmentInfoDetailRes getRecruitmentDetailInfo(Long recruitmentId) {
        RecruitmentInfoDO recruitmentInfoDo = this.recruitmentDAO.getRecruitmentInfoById(recruitmentId);
        RecruitmentInfoRes recruitmentInfoRes = RecruitmentUtil.transformRecruitmentInfoRes(recruitmentInfoDo);
        RecruitmentInfoDetailRes recruitmentInfoDetailRes = new RecruitmentInfoDetailRes();
        BeanUtils.copyProperties(recruitmentInfoRes, recruitmentInfoDetailRes);
        List<RecruitmentDepartmentDO> recruitmentOrganizationDoList = this.recruitmentDepartmentService.listDepartmentByRecruitment(
            recruitmentId);
        if (CollectionUtils.isNotEmpty(recruitmentOrganizationDoList)) {
            List<Long> departmentIdList = recruitmentOrganizationDoList.stream().map(
                RecruitmentDepartmentDO::getDepartmentId).collect(Collectors.toList());
            List<DepartmentDetailRes> departmentDetailResList = this.departmentService.getDepartmentDetailList(
                departmentIdList);
            recruitmentInfoDetailRes.setDepartmentDetailResList(departmentDetailResList);
        }
        return recruitmentInfoDetailRes;
    }

    @Override
    public void updateRecruitmentInfo(RecruitmentInfoDO recruitmentInfoDo, List<DepartmentDO> departmentDoList,
        String operator) {
        transactionTemplate.execute((status) -> {
            this.recruitmentDAO.updateRecruitmentInfo(recruitmentInfoDo, operator);
            this.recruitmentDepartmentService.updateRecruitmentDepartment(recruitmentInfoDo.getId(), departmentDoList,
                operator);
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
