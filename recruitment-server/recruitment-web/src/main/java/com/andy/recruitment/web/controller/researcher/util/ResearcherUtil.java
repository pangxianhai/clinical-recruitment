package com.andy.recruitment.web.controller.researcher.util;

import com.andy.recruitment.biz.organization.service.OrganizationDepartmentService;
import com.andy.recruitment.biz.organization.service.OrganizationService;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.dao.organization.entity.OrganizationDO;
import com.andy.recruitment.dao.organization.entity.OrganizationDepartmentDO;
import com.andy.recruitment.dao.researcher.constant.ResearcherStatus;
import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.researcher.entity.ResearcherQuery;
import com.andy.recruitment.dao.user.constant.Gender;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.organization.response.OrganizationDepartmentRes;
import com.andy.recruitment.web.controller.organization.response.OrganizationRes;
import com.andy.recruitment.web.controller.organization.util.OrganizationDepartmentUtil;
import com.andy.recruitment.web.controller.organization.util.OrganizationUtil;
import com.andy.recruitment.web.controller.researcher.request.ResearcherAddReq;
import com.andy.recruitment.web.controller.researcher.request.ResearcherQueryReq;
import com.andy.recruitment.web.controller.researcher.response.ResearcherInfoRes;
import com.andy.recruitment.web.controller.user.response.UserInfoRes;
import com.andy.recruitment.web.controller.user.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 研究员工具
 *
 * @author 庞先海 2020-01-31
 */
@Component
public class ResearcherUtil {

    private static RegionService regionService;

    private static UserService userService;

    private static OrganizationService organizationService;

    private static OrganizationDepartmentService organizationDepartmentService;

    @Autowired
    public ResearcherUtil(RegionService regionService, UserService userService, OrganizationService organizationService,
        OrganizationDepartmentService organizationDepartmentService) {
        ResearcherUtil.regionService = regionService;
        ResearcherUtil.userService = userService;
        ResearcherUtil.organizationService = organizationService;
        ResearcherUtil.organizationDepartmentService = organizationDepartmentService;
    }

    public static ResearcherInfoDO transformResearcherInfo(ResearcherAddReq researcherAddReq) {
        if (null == researcherAddReq) {
            return null;
        }
        ResearcherInfoDO researcherInfoDo = new ResearcherInfoDO();
        BeanUtils.copyProperties(researcherAddReq, researcherInfoDo);
        return researcherInfoDo;
    }


    public static UserInfoDO transformUserInfo(ResearcherAddReq researcherAddReq) {
        if (null == researcherAddReq) {
            return null;
        }
        UserInfoDO userInfoDo = new UserInfoDO();
        BeanUtils.copyProperties(researcherAddReq, userInfoDo);
        userInfoDo.setGender(Gender.parse(researcherAddReq.getGender()));
        userInfoDo.setRealName(researcherAddReq.getName());
        return userInfoDo;
    }

    public static ResearcherQuery transformResearcherQuery(ResearcherQueryReq queryReq) {
        if (queryReq == null) {
            return null;
        }
        ResearcherQuery query = new ResearcherQuery();
        BeanUtils.copyProperties(queryReq, query);
        query.setStatus(ResearcherStatus.parse(queryReq.getStatus()));
        return query;
    }

    public static ResearcherInfoRes transformResearcherInfoRes(ResearcherInfoDO researcherInfoDo) {
        List<ResearcherInfoDO> researcherInfoDoList = Collections.singletonList(researcherInfoDo);
        List<ResearcherInfoRes> researcherInfoResList = ResearcherUtil.transformResearcherInfoRes(researcherInfoDoList);
        if (CollectionUtils.isEmpty(researcherInfoResList)) {
            return null;
        }
        return researcherInfoResList.get(0);
    }

    public static List<ResearcherInfoRes> transformResearcherInfoRes(List<ResearcherInfoDO> researcherInfoDoList) {
        if (CollectionUtils.isEmpty(researcherInfoDoList)) {
            return new ArrayList<>(0);
        }
        List<Long> userIdList = new ArrayList<>(researcherInfoDoList.size());
        List<Long> organizationIdList = new ArrayList<>(researcherInfoDoList.size());
        List<Long> departmentIdList = new ArrayList<>(researcherInfoDoList.size());
        for (ResearcherInfoDO researcherInfoDo : researcherInfoDoList) {
            userIdList.add(researcherInfoDo.getUserId());
            organizationIdList.add(researcherInfoDo.getOrganizationId());
            departmentIdList.add(researcherInfoDo.getDepartmentId());
        }
        List<UserInfoDO> userInfoDoList = userService.getUserInfo(userIdList);
        List<UserInfoRes> userInfoResList = UserInfoUtil.transformUserInfoRes(userInfoDoList);
        Map<Long, UserInfoRes> userInfoResMap = userInfoResList.stream().collect(
            Collectors.toMap(UserInfoRes::getUserId, Function.identity(), (u1, u2) -> u1));

        List<OrganizationDO> organizationDoList = organizationService.getOrganization(organizationIdList);
        List<OrganizationRes> organizationResList = OrganizationUtil.transformOrganizationRes(organizationDoList);
        Map<Long, OrganizationRes> organizationResMap = organizationResList.stream().collect(
            Collectors.toMap(OrganizationRes::getOrganizationId, Function.identity(), (o1, o2) -> o1));

        List<OrganizationDepartmentDO> organizationDepartmentDoList = organizationDepartmentService.getOrganizationDepartment(
            departmentIdList);
        List<OrganizationDepartmentRes> organizationDepartmentResList = OrganizationDepartmentUtil.transformOrganizationDepartmentRes(
            organizationDepartmentDoList);
        Map<Long, OrganizationDepartmentRes> organizationDepartmentResMap = organizationDepartmentResList.stream().collect(
            Collectors.toMap(OrganizationDepartmentRes::getDepartmentId, Function.identity(), (d1, d2) -> d1));

        return researcherInfoDoList.stream().map(
            researcherInfoDo -> ResearcherUtil.transformResearcherInfoRes(researcherInfoDo, userInfoResMap,
                organizationResMap, organizationDepartmentResMap)).collect(Collectors.toList());
    }

    private static ResearcherInfoRes transformResearcherInfoRes(ResearcherInfoDO researcherInfoDo,
        Map<Long, UserInfoRes> userInfoResMap, Map<Long, OrganizationRes> organizationResMap,
        Map<Long, OrganizationDepartmentRes> organizationDepartmentResMap) {
        if (researcherInfoDo == null) {
            return null;
        }
        ResearcherInfoRes researcherInfoRes = new ResearcherInfoRes();
        BeanUtils.copyProperties(researcherInfoDo, researcherInfoRes);
        researcherInfoRes.setUserInfoRes(userInfoResMap.get(researcherInfoRes.getUserId()));
        researcherInfoRes.setOrganizationRes(organizationResMap.get(researcherInfoRes.getOrganizationId()));
        researcherInfoRes.setOrganizationDepartmentRes(
            organizationDepartmentResMap.get(researcherInfoRes.getDepartmentId()));
        researcherInfoRes.setResearcherId(researcherInfoDo.getId());
        return researcherInfoRes;
    }
}
