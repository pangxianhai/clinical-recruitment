package com.andy.recruitment.web.controller.organization.util;

import com.andy.recruitment.biz.organization.service.OrganizationDepartmentService;
import com.andy.recruitment.biz.organization.service.OrganizationService;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentQuery;
import com.andy.recruitment.web.controller.organization.request.OrganizationDepartmentAddReq;
import com.andy.recruitment.web.controller.organization.request.OrganizationDepartmentQueryReq;
import com.andy.recruitment.web.controller.organization.response.OrganizationDepartmentDetailRes;
import com.andy.recruitment.web.controller.organization.response.OrganizationDepartmentRes;
import com.andy.recruitment.web.controller.organization.response.OrganizationRes;
import java.util.ArrayList;
import java.util.Collections;
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
 * 机构科室工具
 *
 * @author 庞先海 2020-01-31
 */
@Component
public class OrganizationDepartmentUtil {

    private static OrganizationService organizationService;

    private static OrganizationDepartmentService organizationDepartmentService;

    @Autowired
    public OrganizationDepartmentUtil(OrganizationService organizationService,
        OrganizationDepartmentService organizationDepartmentService) {
        OrganizationDepartmentUtil.organizationService = organizationService;
        OrganizationDepartmentUtil.organizationDepartmentService = organizationDepartmentService;
    }

    public static OrganizationDepartmentDO transformOrganizationDepartmentDo(
        OrganizationDepartmentAddReq departmentAddReq) {
        if (departmentAddReq == null) {
            return null;
        }
        OrganizationDepartmentDO organizationDepartmentDo = new OrganizationDepartmentDO();
        BeanUtils.copyProperties(departmentAddReq, organizationDepartmentDo);
        return organizationDepartmentDo;
    }

    public static OrganizationDepartmentQuery transformOrganizationDepartmentQuery(
        OrganizationDepartmentQueryReq queryReq) {
        if (queryReq == null) {
            return null;
        }
        OrganizationDepartmentQuery departmentQuery = new OrganizationDepartmentQuery();
        BeanUtils.copyProperties(queryReq, departmentQuery);
        return departmentQuery;
    }

    public static List<OrganizationDepartmentDetailRes> transformOrganizationDepartmentDetailRes(
        List<OrganizationDepartmentDO> departmentDoList) {
        if (CollectionUtils.isEmpty(departmentDoList)) {
            return new ArrayList<>(0);
        }
        List<Long> organizationIdList = departmentDoList.stream().map(
            OrganizationDepartmentDO::getOrganizationId).collect(Collectors.toList());
        Map<Long, OrganizationRes> organizationResMap = OrganizationUtil.getOrganizationRes(organizationIdList);
        return departmentDoList.stream().map(
            departmentDo -> transformOrganizationDepartmentDetailRes(departmentDo, organizationResMap)).collect(
            Collectors.toList());
    }

    public static Map<Long, OrganizationDepartmentRes> getOrganizationDepartmentRes(List<Long> departmentIdList) {
        List<OrganizationDepartmentDO> organizationDepartmentDoList = organizationDepartmentService.getOrganizationDepartment(
            departmentIdList);
        if (CollectionUtils.isEmpty(organizationDepartmentDoList)) {
            return Collections.emptyMap();
        }
        List<OrganizationDepartmentRes> organizationDepartmentResList = organizationDepartmentDoList.stream().map(
            OrganizationDepartmentUtil::transformOrganizationDepartmentRes).filter(Objects::nonNull).collect(
            Collectors.toList());
        return organizationDepartmentResList.stream().collect(
            Collectors.toMap(OrganizationDepartmentRes::getDepartmentId, Function.identity(), (d1, d2) -> d1));
    }

    public static List<OrganizationDepartmentRes> transformOrganizationDepartmentRes(
        List<OrganizationDepartmentDO> departmentDoList) {
        if (CollectionUtils.isEmpty(departmentDoList)) {
            return Collections.emptyList();
        }
        return departmentDoList.stream().map(OrganizationDepartmentUtil::transformOrganizationDepartmentRes).filter(
            Objects::nonNull).collect(Collectors.toList());
    }

    public static OrganizationDepartmentRes transformOrganizationDepartmentRes(OrganizationDepartmentDO departmentDo) {
        if (departmentDo == null) {
            return null;
        }
        OrganizationDepartmentRes departmentRes = new OrganizationDepartmentRes();
        BeanUtils.copyProperties(departmentDo, departmentRes);
        departmentRes.setDepartmentId(departmentDo.getId());
        return departmentRes;
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
