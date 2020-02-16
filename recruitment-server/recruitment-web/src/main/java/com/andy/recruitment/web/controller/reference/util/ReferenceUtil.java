package com.andy.recruitment.web.controller.reference.util;

import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.dao.reference.constant.ReferenceStatus;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoQuery;
import com.andy.recruitment.dao.user.constant.Gender;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.reference.request.ReferenceAddReq;
import com.andy.recruitment.web.controller.reference.request.ReferenceQueryReq;
import com.andy.recruitment.web.controller.reference.response.ReferenceInfoRes;
import com.andy.recruitment.web.controller.user.response.UserInfoRes;
import com.andy.recruitment.web.controller.user.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 推荐人功能键
 *
 * @author 庞先海 2020-02-15
 */
@Component
public class ReferenceUtil {

    private static RegionService regionService;

    @Autowired
    public ReferenceUtil(RegionService regionService) {
        ReferenceUtil.regionService = regionService;
    }

    public static ReferenceInfoDO transformReferenceInfoDo(ReferenceAddReq referenceAddReq) {
        if (null == referenceAddReq) {
            return null;
        }
        ReferenceInfoDO referenceInfoDo = new ReferenceInfoDO();
        BeanUtils.copyProperties(referenceAddReq, referenceInfoDo);
        return referenceInfoDo;
    }

    public static UserInfoDO transformUserInfo(ReferenceAddReq referenceAddReq) {
        if (null == referenceAddReq) {
            return null;
        }
        UserInfoDO userInfoDo = new UserInfoDO();
        BeanUtils.copyProperties(referenceAddReq, userInfoDo);
        userInfoDo.setGender(Gender.parse(referenceAddReq.getGender()));
        userInfoDo.setRealName(referenceAddReq.getName());
        return userInfoDo;
    }

    public static ReferenceInfoQuery transformReferenceQuery(ReferenceQueryReq queryReq) {
        if (queryReq == null) {
            return null;
        }

        ReferenceInfoQuery query = new ReferenceInfoQuery();
        BeanUtils.copyProperties(queryReq, query);
        if (queryReq.getStatus() != null) {
            query.setStatus(ReferenceStatus.parse(queryReq.getStatus()));
        }
        return query;
    }

    public static List<ReferenceInfoRes> transformReferenceRes(List<ReferenceInfoDO> referenceInfoDoList) {
        if (CollectionUtils.isEmpty(referenceInfoDoList)) {
            return Collections.emptyList();
        }
        List<Long> userIdList = new ArrayList<>(referenceInfoDoList.size());
        for (ReferenceInfoDO referenceInfoDO : referenceInfoDoList) {
            userIdList.add(referenceInfoDO.getUserId());
        }
        Map<Long, UserInfoRes> userInfoResMap = UserInfoUtil.getUserInfoRes(userIdList);
        return referenceInfoDoList.stream().map(
            referenceInfoDo -> ReferenceUtil.transformReferenceRes(referenceInfoDo, userInfoResMap)).filter(
            Objects::nonNull).collect(Collectors.toList());
    }

    public static ReferenceInfoRes transformReferenceRes(ReferenceInfoDO referenceInfoDo) {
        List<ReferenceInfoDO> referenceInfoDoList = Collections.singletonList(referenceInfoDo);
        List<ReferenceInfoRes> referenceInfoResList = ReferenceUtil.transformReferenceRes(referenceInfoDoList);
        if (CollectionUtils.isEmpty(referenceInfoDoList)) {
            return null;
        }
        return referenceInfoResList.get(0);
    }

    private static ReferenceInfoRes transformReferenceRes(ReferenceInfoDO referenceInfoDo,
        Map<Long, UserInfoRes> userInfoResMap) {
        if (referenceInfoDo == null) {
            return null;
        }
        ReferenceInfoRes referenceInfoRes = new ReferenceInfoRes();
        BeanUtils.copyProperties(referenceInfoDo, referenceInfoRes);
        referenceInfoRes.setReferenceId(referenceInfoDo.getId());
        referenceInfoRes.setUserInfoRes(userInfoResMap.get(referenceInfoDo.getUserId()));
        referenceInfoRes.setAddress(
            regionService.parseAddressName(referenceInfoDo.getProvinceId(), referenceInfoDo.getCityId(),
                referenceInfoDo.getDistrictId()));
        return referenceInfoRes;
    }
}
