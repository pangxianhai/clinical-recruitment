package com.andy.recruitment.web.controller.organization.util;

import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationQuery;
import com.andy.recruitment.web.controller.organization.request.OrganizationAddReq;
import com.andy.recruitment.web.controller.organization.request.OrganizationQueryReq;
import com.andy.recruitment.web.controller.organization.response.OrganizationRes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

/**
 * 功能描述
 *
 * @author 庞先海 2020-01-30
 */
public class OrganizationUtil {

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

    public static OrganizationRes transformOrganizationRes(OrganizationDO organizationDo, RegionService regionService) {
        if (organizationDo == null) {
            return null;
        }
        OrganizationRes organizationRes = new OrganizationRes();
        BeanUtils.copyProperties(organizationDo, organizationRes);
        organizationRes.setProvinceName(regionService.getRegionNameById(organizationDo.getProvinceId()));
        organizationRes.setCityName(regionService.getRegionNameById(organizationDo.getCityId()));
        organizationRes.setDistrictName(regionService.getRegionNameById(organizationDo.getDistrictId()));
        return organizationRes;
    }

    public static List<OrganizationRes> transformOrganizationRes(List<OrganizationDO> organizationDoList,
        RegionService regionService) {
        if (CollectionUtils.isEmpty(organizationDoList)) {
            return new ArrayList<>(0);
        }
        return organizationDoList.stream().map(o -> OrganizationUtil.transformOrganizationRes(o, regionService)).filter(
            Objects::nonNull).collect(Collectors.toList());
    }
}
