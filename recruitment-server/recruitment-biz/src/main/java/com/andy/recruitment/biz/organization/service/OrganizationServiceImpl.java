package com.andy.recruitment.biz.organization.service;

import com.andy.recruitment.api.organization.response.OrganizationRes;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.dao.organization.dao.OrganizationDAO;
import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构服务实现
 *
 * @author 庞先海 2020-01-30
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationDAO organizationDAO;

    private final RegionService regionService;

    @Autowired
    public OrganizationServiceImpl(OrganizationDAO organizationDAO, RegionService regionService) {
        this.organizationDAO = organizationDAO;
        this.regionService = regionService;
    }

    @Override
    public void addOrganization(OrganizationDO organizationDo, String operator) {
        this.organizationDAO.addOrganization(organizationDo, operator);
    }

    @Override
    public void updateOrganization(OrganizationDO organizationDo, String operator) {
        this.organizationDAO.updateOrganization(organizationDo, operator);
    }

    @Override
    public PageResult<OrganizationDO> getOrganization(OrganizationQuery query, Paginator paginator) {
        if (StringUtils.isEmpty(paginator.getOrderSegment())) {
            paginator.setOrderSegment("created_time.desc");
        }
        return this.organizationDAO.getOrganization(query, paginator);
    }

    @Override
    public List<OrganizationDO> getOrganization(List<Long> organizationIdList) {
        return this.organizationDAO.getOrganization(organizationIdList);
    }

    @Override
    public OrganizationDO getOrganizationById(Long organizationId) {
        return this.organizationDAO.getOrganizationById(organizationId);
    }

    @Override
    public Map<Long, OrganizationRes> getOrganizationRes(List<Long> organizationIdList) {
        List<OrganizationDO> organizationDoList = this.organizationDAO.getOrganization(organizationIdList);
        List<OrganizationRes> organizationResList = this.transformOrganizationRes(organizationDoList);
        return organizationResList.stream().collect(
            Collectors.toMap(OrganizationRes::getOrganizationId, Function.identity(), (o1, o2) -> o1));

    }

    private List<OrganizationRes> transformOrganizationRes(List<OrganizationDO> organizationDoList) {
        if (CollectionUtils.isEmpty(organizationDoList)) {
            return new ArrayList<>(0);
        }
        return organizationDoList.stream().map(this::transformOrganizationRes).filter(Objects::nonNull).collect(
            Collectors.toList());
    }


    private OrganizationRes transformOrganizationRes(OrganizationDO organizationDo) {
        if (organizationDo == null) {
            return null;
        }
        OrganizationRes organizationRes = new OrganizationRes();
        BeanUtils.copyProperties(organizationDo, organizationRes);
        organizationRes.setProvinceName(regionService.getRegionNameById(organizationDo.getProvinceId()));
        organizationRes.setCityName(regionService.getRegionNameById(organizationDo.getCityId()));
        organizationRes.setDistrictName(regionService.getRegionNameById(organizationDo.getDistrictId()));
        organizationRes.setAddress(
            regionService.parseAddressName(organizationDo.getProvinceId(), organizationDo.getCityId(),
                organizationDo.getDistrictId()));
        organizationRes.setOrganizationId(organizationDo.getId());
        return organizationRes;
    }
}
