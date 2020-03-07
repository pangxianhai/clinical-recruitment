package com.andy.recruitment.biz.reference.service;

import com.andy.recruitment.api.reference.response.ReferenceDetailInfoRes;
import com.andy.recruitment.api.user.response.UserInfoRes;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.reference.dao.ReferenceDAO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 推荐人服务接口实现
 *
 * @author 庞先海 2020-02-15
 */
@Service
public class ReferenceServiceImpl implements ReferenceService {

    private final ReferenceDAO referenceDAO;

    private final UserService userService;

    private final RegionService regionService;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public ReferenceServiceImpl(ReferenceDAO referenceDAO, UserService userService, RegionService regionService,
        TransactionTemplate transactionTemplate) {
        this.referenceDAO = referenceDAO;
        this.userService = userService;
        this.regionService = regionService;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void registerReference(ReferenceInfoDO referenceInfoDo, UserInfoDO userInfoDo, String operator) {
        transactionTemplate.execute((status) -> {
            Long userId = this.userService.registerUser(userInfoDo, operator);
            referenceInfoDo.setUserId(userId);
            ReferenceInfoDO existReferenceInfoDo = this.referenceDAO.getReferenceInfoByUserId(userId);
            if (existReferenceInfoDo == null) {
                this.referenceDAO.addReferenceInfo(referenceInfoDo, operator);
            } else {
                referenceInfoDo.setId(existReferenceInfoDo.getId());
                this.referenceDAO.updateReferenceInfo(referenceInfoDo, operator);
            }
            return null;
        });
    }

    @Override
    public void updateReference(ReferenceInfoDO referenceInfoDo, UserInfoDO userInfoDo, String operator) {
        ReferenceInfoDO sourceReferenceInfoDo = this.referenceDAO.getReferenceInfoById(referenceInfoDo.getId());
        AssertUtil.assertNull(sourceReferenceInfoDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.REFERENCE_NOT_EXIST);
        });
        transactionTemplate.execute((status) -> {
            this.referenceDAO.updateReferenceInfo(referenceInfoDo, operator);
            userInfoDo.setId(sourceReferenceInfoDo.getUserId());
            this.userService.updateUserInfo(userInfoDo, operator);
            return null;
        });
    }

    @Override
    public PageResult<ReferenceInfoDO> getReference(ReferenceInfoQuery query, Paginator paginator) {
        return this.referenceDAO.getReferenceInfo(query, paginator);
    }

    @Override
    public ReferenceInfoDO getReference(Long referenceId) {
        return this.referenceDAO.getReferenceInfoById(referenceId);
    }

    @Override
    public ReferenceInfoDO getReferenceByUserId(Long userId) {
        return this.referenceDAO.getReferenceInfoByUserId(userId);
    }

    @Override
    public List<ReferenceInfoDO> getReferenceInfoByUserIdList(List<Long> userIdList) {
        return this.referenceDAO.getReferenceInfoByUserIdList(userIdList);
    }

    @Override
    public ReferenceInfoDO getReferenceByPhone(String phone) {
        UserInfoDO userInfoDo = this.userService.getUserInfoByPhone(phone);
        if (userInfoDo == null) {
            return null;
        }
        return this.getReferenceByUserId(userInfoDo.getId());
    }

    @Override
    public Map<Long, ReferenceDetailInfoRes> getReferenceDetailInfoRes(List<Long> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)) {
            return Collections.emptyMap();
        }
        List<ReferenceInfoDO> referenceInfoDoList = this.referenceDAO.getReferenceInfoByUserIdList(userIdList);
        List<ReferenceDetailInfoRes> detailInfoResList = transformReferenceDetailRes(referenceInfoDoList);
        return detailInfoResList.stream().collect(
            Collectors.toMap(ReferenceDetailInfoRes::getUserId, Function.identity(), (r1, r2) -> r1));
    }

    private List<ReferenceDetailInfoRes> transformReferenceDetailRes(List<ReferenceInfoDO> referenceInfoDoList) {
        if (CollectionUtils.isEmpty(referenceInfoDoList)) {
            return Collections.emptyList();
        }
        List<Long> userIdList = new ArrayList<>(referenceInfoDoList.size());
        for (ReferenceInfoDO referenceInfoDO : referenceInfoDoList) {
            userIdList.add(referenceInfoDO.getUserId());
        }
        Map<Long, UserInfoRes> userInfoResMap = this.userService.getUserInfoRes(userIdList);
        return referenceInfoDoList.stream().map(
            referenceInfoDo -> this.transformReferenceDetailRes(referenceInfoDo, userInfoResMap)).filter(
            Objects::nonNull).collect(Collectors.toList());
    }

    private ReferenceDetailInfoRes transformReferenceDetailRes(ReferenceInfoDO referenceInfoDo,
        Map<Long, UserInfoRes> userInfoResMap) {
        if (referenceInfoDo == null) {
            return null;
        }
        ReferenceDetailInfoRes referenceDetailInfoRes = new ReferenceDetailInfoRes();
        BeanUtils.copyProperties(referenceInfoDo, referenceDetailInfoRes);
        referenceDetailInfoRes.setReferenceId(referenceInfoDo.getId());
        referenceDetailInfoRes.setUserInfoRes(userInfoResMap.get(referenceInfoDo.getUserId()));
        referenceDetailInfoRes.setAddress(
            this.regionService.parseAddressName(referenceInfoDo.getProvinceId(), referenceInfoDo.getCityId(),
                referenceInfoDo.getDistrictId()));
        return referenceDetailInfoRes;
    }
}
