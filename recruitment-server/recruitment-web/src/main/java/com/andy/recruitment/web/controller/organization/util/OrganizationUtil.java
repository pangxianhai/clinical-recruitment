package com.andy.recruitment.web.controller.organization.util;

import com.andy.recruitment.biz.organization.service.OrganizationService;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationQuery;
import com.andy.recruitment.web.controller.organization.request.OrganizationAddReq;
import com.andy.recruitment.web.controller.organization.request.OrganizationQueryReq;
import com.andy.recruitment.web.controller.organization.response.OrganizationRes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 功能描述
 *
 * @author 庞先海 2020-01-30
 */
@Component
public class OrganizationUtil {

    private static RegionService regionService;

    private static OrganizationService organizationService;

    @Autowired
    public OrganizationUtil(RegionService regionService, OrganizationService organizationService) {
        OrganizationUtil.regionService = regionService;
        OrganizationUtil.organizationService = organizationService;
    }

    public static OrganizationQuery transformOrganizationQuery(OrganizationQueryReq queryReq) {
        if (queryReq == null) {
            return null;
        }
        OrganizationQuery organizationQuery = new OrganizationQuery();
        BeanUtils.copyProperties(queryReq, organizationQuery);
        return organizationQuery;
    }

    public static OrganizationDO transformOrganization(OrganizationAddReq addReq) {
        if (addReq == null) {
            return null;
        }
        OrganizationDO organizationDo = new OrganizationDO();
        BeanUtils.copyProperties(addReq, organizationDo);
        return organizationDo;
    }

    public static OrganizationRes transformOrganizationRes(OrganizationDO organizationDo) {
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

    public static List<OrganizationRes> transformOrganizationRes(List<OrganizationDO> organizationDoList) {
        if (CollectionUtils.isEmpty(organizationDoList)) {
            return new ArrayList<>(0);
        }
        return organizationDoList.stream().map(OrganizationUtil::transformOrganizationRes).filter(
            Objects::nonNull).collect(Collectors.toList());
    }

    public static Map<Long, OrganizationRes> getOrganizationRes(List<Long> organizationIdList) {
        List<OrganizationDO> organizationDoList = organizationService.getOrganization(organizationIdList);
        List<OrganizationRes> organizationResList = OrganizationUtil.transformOrganizationRes(organizationDoList);
        return organizationResList.stream().collect(
            Collectors.toMap(OrganizationRes::getOrganizationId, Function.identity(), (o1, o2) -> o1));

    }
}
