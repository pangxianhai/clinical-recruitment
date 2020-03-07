package com.andy.recruitment.biz.organization.service;

import com.andy.recruitment.api.organization.response.OrganizationDepartmentDetailRes;
import com.andy.recruitment.api.organization.response.OrganizationDepartmentRes;
import com.andy.recruitment.api.organization.response.OrganizationRes;
import com.andy.recruitment.dao.organization.dao.OrganizationDepartmentDAO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构科室服务接口实现
 *
 * @author 庞先海 2020-01-31
 */
@Service
public class OrganizationDepartmentServiceImpl implements OrganizationDepartmentService {

    private final OrganizationDepartmentDAO organizationDepartmentDAO;

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationDepartmentServiceImpl(OrganizationDepartmentDAO organizationDepartmentDAO,
        OrganizationService organizationService) {
        this.organizationDepartmentDAO = organizationDepartmentDAO;
        this.organizationService = organizationService;
    }

    @Override
    public void addOrganizationDepartment(OrganizationDepartmentDO organizationDepartmentDo, String operator) {
        this.organizationDepartmentDAO.addOrganizationDepartment(organizationDepartmentDo, operator);
    }

    @Override
    public void updateOrganizationDepartment(OrganizationDepartmentDO organizationDepartmentDo, String operator) {
        this.organizationDepartmentDAO.updateOrganizationDepartment(organizationDepartmentDo, operator);
    }

    @Override
    public PageResult<OrganizationDepartmentDO> getOrganizationDepartment(OrganizationDepartmentQuery query,
        Paginator paginator) {
        return this.organizationDepartmentDAO.getOrganizationDepartment(query, paginator);
    }

    @Override
    public List<OrganizationDepartmentDO> getOrganizationDepartmentByOrganization(Long organizationId) {
        return this.organizationDepartmentDAO.getOrganizationDepartmentByOrganization(organizationId);
    }

    @Override
    public List<OrganizationDepartmentDO> getOrganizationDepartment(List<Long> departmentIdList) {
        return this.organizationDepartmentDAO.getOrganizationDepartment(departmentIdList);
    }

    @Override
    public OrganizationDepartmentDO getOrganizationDepartmentById(Long departmentId) {
        return this.organizationDepartmentDAO.getOrganizationDepartmentById(departmentId);
    }

    @Override
    public Map<Long, OrganizationDepartmentDetailRes> getOrganizationDepartmentDetailRes(List<Long> departmentIdList) {
        List<OrganizationDepartmentDetailRes> departmentDetailResList = this.getOrganizationDepartmentDetailList(
            departmentIdList);
        if (CollectionUtils.isEmpty(departmentDetailResList)) {
            return Collections.emptyMap();
        }
        return departmentDetailResList.stream().collect(
            Collectors.toMap(OrganizationDepartmentRes::getDepartmentId, Function.identity(), (d1, d2) -> d1));
    }

    @Override
    public List<OrganizationDepartmentDetailRes> getOrganizationDepartmentDetailList(List<Long> departmentIdList) {
        List<OrganizationDepartmentDO> organizationDepartmentDoList = this.organizationDepartmentDAO.getOrganizationDepartment(
            departmentIdList);
        if (CollectionUtils.isEmpty(organizationDepartmentDoList)) {
            return Collections.emptyList();
        }
        return transformOrganizationDepartmentDetailRes(organizationDepartmentDoList);
    }

    private List<OrganizationDepartmentDetailRes> transformOrganizationDepartmentDetailRes(
        List<OrganizationDepartmentDO> departmentDoList) {
        if (CollectionUtils.isEmpty(departmentDoList)) {
            return new ArrayList<>(0);
        }
        List<Long> organizationIdList = departmentDoList.stream().map(
            OrganizationDepartmentDO::getOrganizationId).collect(Collectors.toList());
        Map<Long, OrganizationRes> organizationResMap = organizationService.getOrganizationRes(organizationIdList);
        return departmentDoList.stream().map(
            departmentDo -> transformOrganizationDepartmentDetailRes(departmentDo, organizationResMap)).collect(
            Collectors.toList());
    }

    private static OrganizationDepartmentDetailRes transformOrganizationDepartmentDetailRes(
        OrganizationDepartmentDO departmentDo, Map<Long, OrganizationRes> organizationResMap) {
        OrganizationDepartmentDetailRes departmentDetailRes = new OrganizationDepartmentDetailRes();
        BeanUtils.copyProperties(departmentDo, departmentDetailRes);
        departmentDetailRes.setDepartmentId(departmentDo.getId());
        if (organizationResMap != null) {
            departmentDetailRes.setOrganizationRes(organizationResMap.get(departmentDetailRes.getOrganizationId()));
        }
        return departmentDetailRes;
    }
}
