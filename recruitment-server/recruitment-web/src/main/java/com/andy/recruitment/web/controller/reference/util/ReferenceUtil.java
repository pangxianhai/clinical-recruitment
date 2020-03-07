package com.andy.recruitment.web.controller.reference.util;

import com.andy.recruitment.biz.reference.service.ReferenceService;
import com.andy.recruitment.biz.region.entity.AddressInfo;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.reference.constant.ReferenceStatus;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoQuery;
import com.andy.recruitment.dao.user.constant.Gender;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.reference.request.ReferenceAddReq;
import com.andy.recruitment.web.controller.reference.request.ReferenceQueryReq;
import com.andy.recruitment.web.controller.reference.request.ReferenceRegisterReq;
import com.andy.recruitment.web.controller.reference.response.ReferenceDetailInfoRes;
import com.andy.recruitment.web.controller.reference.response.ReferenceInfoRes;
import com.andy.recruitment.web.controller.user.response.UserInfoRes;
import com.andy.recruitment.web.controller.user.util.UserInfoUtil;
import com.andy.spring.util.asserts.AssertUtil;
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
 * 推荐人功能键
 *
 * @author 庞先海 2020-02-15
 */
@Component
public class ReferenceUtil {

    private static RegionService regionService;

    private static ReferenceService referenceService;

    @Autowired
    public ReferenceUtil(RegionService regionService, ReferenceService referenceService) {
        ReferenceUtil.regionService = regionService;
        ReferenceUtil.referenceService = referenceService;
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

    public static ReferenceInfoDO transformReferenceInfoDo(ReferenceRegisterReq referenceRegisterReq) {
        if (null == referenceRegisterReq) {
            return null;
        }
        ReferenceInfoDO referenceInfoDo = new ReferenceInfoDO();
        BeanUtils.copyProperties(referenceRegisterReq, referenceInfoDo);
        AddressInfo addressInfo = regionService.parseAddressInfo(referenceRegisterReq.getAddress());
        AssertUtil.assertNull(addressInfo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.REFERENCE_REGISTER_REGION_ERROR);
        });
        AssertUtil.assertNull(addressInfo.getProvince(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.REFERENCE_REGISTER_REGION_ERROR);
        });
        AssertUtil.assertNull(addressInfo.getCity(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.REFERENCE_REGISTER_REGION_ERROR);
        });
        referenceInfoDo.setProvinceId(addressInfo.getProvince().getId());
        referenceInfoDo.setCityId(addressInfo.getCity().getId());
        return referenceInfoDo;
    }

    public static UserInfoDO transformUserInfo(ReferenceRegisterReq referenceRegisterReq) {
        if (null == referenceRegisterReq) {
            return null;
        }
        UserInfoDO userInfoDo = new UserInfoDO();
        BeanUtils.copyProperties(referenceRegisterReq, userInfoDo);
        userInfoDo.setGender(Gender.parse(referenceRegisterReq.getGender()));
        userInfoDo.setRealName(referenceRegisterReq.getName());
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

    public static List<ReferenceDetailInfoRes> transformReferenceDetailRes(List<ReferenceInfoDO> referenceInfoDoList) {
        if (CollectionUtils.isEmpty(referenceInfoDoList)) {
            return Collections.emptyList();
        }
        List<Long> userIdList = new ArrayList<>(referenceInfoDoList.size());
        for (ReferenceInfoDO referenceInfoDO : referenceInfoDoList) {
            userIdList.add(referenceInfoDO.getUserId());
        }
        Map<Long, UserInfoRes> userInfoResMap = UserInfoUtil.getUserInfoRes(userIdList);
        return referenceInfoDoList.stream().map(
            referenceInfoDo -> ReferenceUtil.transformReferenceDetailRes(referenceInfoDo, userInfoResMap)).filter(
            Objects::nonNull).collect(Collectors.toList());
    }

    public static Map<Long, ReferenceDetailInfoRes> getReferenceDetailInfoRes(List<Long> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)) {
            return Collections.emptyMap();
        }
        List<ReferenceInfoDO> referenceInfoDoList = referenceService.getReferenceInfoByUserIdList(userIdList);
        List<ReferenceDetailInfoRes> detailInfoResList = transformReferenceDetailRes(referenceInfoDoList);
        return detailInfoResList.stream().collect(
            Collectors.toMap(ReferenceDetailInfoRes::getUserId, Function.identity(), (r1, r2) -> r1));
    }

    public static ReferenceDetailInfoRes transformReferenceDetailRes(ReferenceInfoDO referenceInfoDo) {
        if (referenceInfoDo == null) {
            return null;
        }
        List<ReferenceInfoDO> referenceInfoDoList = Collections.singletonList(referenceInfoDo);
        List<ReferenceDetailInfoRes> referenceDetailInfoResList = ReferenceUtil.transformReferenceDetailRes(
            referenceInfoDoList);
        if (CollectionUtils.isEmpty(referenceDetailInfoResList)) {
            return null;
        }
        return referenceDetailInfoResList.get(0);
    }

    public static ReferenceInfoRes transformReferenceRes(ReferenceInfoDO referenceInfoDo) {
        if (referenceInfoDo == null) {
            return null;
        }
        ReferenceInfoRes referenceInfoRes = new ReferenceInfoRes();
        BeanUtils.copyProperties(referenceInfoDo, referenceInfoRes);
        referenceInfoRes.setReferenceId(referenceInfoDo.getId());
        referenceInfoRes.setAddress(
            regionService.parseAddressName(referenceInfoDo.getProvinceId(), referenceInfoDo.getCityId(),
                referenceInfoDo.getDistrictId()));
        return referenceInfoRes;
    }

    private static ReferenceDetailInfoRes transformReferenceDetailRes(ReferenceInfoDO referenceInfoDo,
        Map<Long, UserInfoRes> userInfoResMap) {
        if (referenceInfoDo == null) {
            return null;
        }
        ReferenceDetailInfoRes referenceDetailInfoRes = new ReferenceDetailInfoRes();
        BeanUtils.copyProperties(referenceInfoDo, referenceDetailInfoRes);
        referenceDetailInfoRes.setReferenceId(referenceInfoDo.getId());
        referenceDetailInfoRes.setUserInfoRes(userInfoResMap.get(referenceInfoDo.getUserId()));
        referenceDetailInfoRes.setAddress(
            regionService.parseAddressName(referenceInfoDo.getProvinceId(), referenceInfoDo.getCityId(),
                referenceInfoDo.getDistrictId()));
        return referenceDetailInfoRes;
    }
}
