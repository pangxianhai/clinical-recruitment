package com.andy.recruitment.web.controller.organization.util;

import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationQuery;
import com.andy.recruitment.web.controller.organization.request.OrganizationAddReq;
import com.andy.recruitment.web.controller.organization.request.OrganizationQueryReq;
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
}
