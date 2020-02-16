package com.andy.recruitment.web.controller.organization.util;

import com.andy.recruitment.biz.organization.service.OrganizationDepartmentService;
import com.andy.recruitment.biz.organization.service.OrganizationService;
import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentQuery;
import com.andy.recruitment.web.controller.organization.request.OrganizationDepartmentAddReq;
import com.andy.recruitment.web.controller.organization.request.OrganizationDepartmentQueryReq;
import com.andy.recruitment.web.controller.organization.response.OrganizationDepartmentRes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public static List<OrganizationDepartmentRes> transformOrganizationDepartmentRes(
        List<OrganizationDepartmentDO> departmentDoList) {
        if (CollectionUtils.isEmpty(departmentDoList)) {
            return new ArrayList<>(0);
        }
        List<Long> organizationIdList = departmentDoList.stream().map(
            OrganizationDepartmentDO::getOrganizationId).collect(Collectors.toList());
        List<OrganizationDO> organizationDoList = organizationService.getOrganization(organizationIdList);
        Map<Long, OrganizationDO> organizationDoMap = organizationDoList.stream().collect(
            Collectors.toMap(OrganizationDO::getId, Function.identity(), (o1, o2) -> o1));
        return departmentDoList.stream().map(departmentDo -> {
            OrganizationDepartmentRes organizationDepartmentRes = new OrganizationDepartmentRes();
            BeanUtils.copyProperties(departmentDo, organizationDepartmentRes);
            organizationDepartmentRes.setDepartmentId(departmentDo.getId());
            OrganizationDO organizationDo = organizationDoMap.get(departmentDo.getOrganizationId());
            if (organizationDo != null) {
                organizationDepartmentRes.setOrganizationName(organizationDo.getName());
            }
            return organizationDepartmentRes;
        }).collect(Collectors.toList());
    }

    public static Map<Long, OrganizationDepartmentRes> getOrganizationDepartmentRes(List<Long> departmentIdList) {
        List<OrganizationDepartmentDO> organizationDepartmentDoList = organizationDepartmentService.getOrganizationDepartment(
            departmentIdList);
        List<OrganizationDepartmentRes> organizationDepartmentResList = OrganizationDepartmentUtil.transformOrganizationDepartmentRes(
            organizationDepartmentDoList);
        return organizationDepartmentResList.stream().collect(
            Collectors.toMap(OrganizationDepartmentRes::getDepartmentId, Function.identity(), (d1, d2) -> d1));

    }
}
