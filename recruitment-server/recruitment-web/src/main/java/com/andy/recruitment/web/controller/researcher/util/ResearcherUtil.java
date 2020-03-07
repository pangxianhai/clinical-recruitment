package com.andy.recruitment.web.controller.researcher.util;

import com.andy.recruitment.biz.organization.service.OrganizationDepartmentService;
import com.andy.recruitment.biz.organization.service.OrganizationService;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.common.researcher.constant.ResearcherStatus;
import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.researcher.entity.ResearcherQuery;
import com.andy.recruitment.common.user.constant.Gender;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.organization.response.OrganizationDepartmentRes;
import com.andy.recruitment.web.controller.organization.response.OrganizationRes;
import com.andy.recruitment.web.controller.organization.util.OrganizationDepartmentUtil;
import com.andy.recruitment.web.controller.organization.util.OrganizationUtil;
import com.andy.recruitment.web.controller.researcher.request.ResearcherAddReq;
import com.andy.recruitment.web.controller.researcher.request.ResearcherQueryReq;
import com.andy.recruitment.web.controller.researcher.request.ResearcherRegisterReq;
import com.andy.recruitment.web.controller.researcher.response.ResearcherInfoDetailRes;
import com.andy.recruitment.web.controller.researcher.response.ResearcherInfoRes;
import com.andy.recruitment.web.controller.user.response.UserInfoRes;
import com.andy.recruitment.web.controller.user.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    public static ResearcherInfoDO transformResearcherInfo(ResearcherRegisterReq researcherRegisterReq) {
        if (null == researcherRegisterReq) {
            return null;
        }
        ResearcherInfoDO researcherInfoDo = new ResearcherInfoDO();
        BeanUtils.copyProperties(researcherRegisterReq, researcherInfoDo);
        return researcherInfoDo;
    }

    public static UserInfoDO transformUserInfo(ResearcherRegisterReq researcherRegisterReq) {
        if (null == researcherRegisterReq) {
            return null;
        }
        UserInfoDO userInfoDo = new UserInfoDO();
        BeanUtils.copyProperties(researcherRegisterReq, userInfoDo);
        userInfoDo.setGender(Gender.parse(researcherRegisterReq.getGender()));
        userInfoDo.setRealName(researcherRegisterReq.getName());
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

    public static ResearcherInfoDetailRes transformResearcherDetailRes(ResearcherInfoDO researcherInfoDo) {
        if (researcherInfoDo == null) {
            return null;
        }
        List<ResearcherInfoDO> researcherInfoDoList = Collections.singletonList(researcherInfoDo);
        List<ResearcherInfoDetailRes> researcherInfoDetailResList = ResearcherUtil.transformResearcherDetailRes(
            researcherInfoDoList);
        if (CollectionUtils.isEmpty(researcherInfoDetailResList)) {
            return null;
        }
        return researcherInfoDetailResList.get(0);
    }

    public static List<ResearcherInfoDetailRes> transformResearcherDetailRes(
        List<ResearcherInfoDO> researcherInfoDoList) {
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
        Map<Long, UserInfoRes> userInfoResMap = UserInfoUtil.getUserInfoRes(userIdList);
        Map<Long, OrganizationRes> organizationResMap = OrganizationUtil.getOrganizationRes(organizationIdList);
        Map<Long, OrganizationDepartmentRes> organizationDepartmentResMap = OrganizationDepartmentUtil.getOrganizationDepartmentRes(
            departmentIdList);

        return researcherInfoDoList.stream().map(
            researcherInfoDo -> ResearcherUtil.transformResearcherDetailRes(researcherInfoDo, userInfoResMap,
                organizationResMap, organizationDepartmentResMap)).collect(Collectors.toList());
    }

    public static ResearcherInfoRes transformResearcherRes(ResearcherInfoDO researcherInfoDo) {
        if (researcherInfoDo == null) {
            return null;
        }
        ResearcherInfoRes researcherInfoRes = new ResearcherInfoRes();
        BeanUtils.copyProperties(researcherInfoDo, researcherInfoRes);
        researcherInfoRes.setResearcherId(researcherInfoDo.getId());
        return researcherInfoRes;
    }

    private static ResearcherInfoDetailRes transformResearcherDetailRes(ResearcherInfoDO researcherInfoDo,
        Map<Long, UserInfoRes> userInfoResMap, Map<Long, OrganizationRes> organizationResMap,
        Map<Long, OrganizationDepartmentRes> organizationDepartmentResMap) {
        if (researcherInfoDo == null) {
            return null;
        }
        ResearcherInfoDetailRes researcherInfoDetailRes = new ResearcherInfoDetailRes();
        BeanUtils.copyProperties(researcherInfoDo, researcherInfoDetailRes);
        researcherInfoDetailRes.setUserInfoRes(userInfoResMap.get(researcherInfoDetailRes.getUserId()));
        researcherInfoDetailRes.setOrganizationRes(organizationResMap.get(researcherInfoDetailRes.getOrganizationId()));
        researcherInfoDetailRes.setOrganizationDepartmentRes(
            organizationDepartmentResMap.get(researcherInfoDetailRes.getDepartmentId()));
        researcherInfoDetailRes.setResearcherId(researcherInfoDo.getId());
        return researcherInfoDetailRes;
    }
}
