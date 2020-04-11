package com.andy.recruitment.biz.reference.service;

import com.andy.recruitment.api.hospital.response.DepartmentDetailRes;
import com.andy.recruitment.api.reference.response.ReferenceDetailInfoRes;
import com.andy.recruitment.api.reference.response.ReferenceInfoRes;
import com.andy.recruitment.api.user.response.UserInfoRes;
import com.andy.recruitment.biz.hospital.service.DepartmentService;
import com.andy.recruitment.biz.reference.util.ReferenceUtil;
import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.common.reference.constant.ReferenceStatus;
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
import org.apache.commons.collections4.MapUtils;
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

    private final TransactionTemplate transactionTemplate;

    private final DepartmentService departmentService;

    @Autowired
    public ReferenceServiceImpl(ReferenceDAO referenceDAO, UserService userService,
        TransactionTemplate transactionTemplate, DepartmentService departmentService) {
        this.referenceDAO = referenceDAO;
        this.userService = userService;
        this.departmentService = departmentService;
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
    public void updateReferenceStatus(Long referenceId, ReferenceStatus referenceStatus, String operator) {
        ReferenceInfoDO sourceReferenceInfoDo = this.referenceDAO.getReferenceInfoById(referenceId);
        AssertUtil.assertNull(sourceReferenceInfoDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.REFERENCE_NOT_EXIST);
        });
        ReferenceInfoDO referenceInfoDo = new ReferenceInfoDO();
        referenceInfoDo.setId(referenceId);
        referenceInfoDo.setStatus(referenceStatus);
        this.referenceDAO.updateReferenceInfo(referenceInfoDo, operator);
    }

    @Override
    public PageResult<ReferenceDetailInfoRes> getReference(ReferenceInfoQuery query, Paginator paginator) {
        PageResult<ReferenceInfoDO> pageResult = this.referenceDAO.getReferenceInfo(query, paginator);
        List<ReferenceDetailInfoRes> detailInfoResList = this.transformReferenceDetailRes(pageResult.getData());
        return new PageResult<>(detailInfoResList, pageResult.getPaginator());
    }

    @Override
    public ReferenceDetailInfoRes getReference(Long referenceId) {
        ReferenceInfoDO referenceInfoDo = this.referenceDAO.getReferenceInfoById(referenceId);
        List<ReferenceDetailInfoRes> detailInfoResList = this.transformReferenceDetailRes(
            Collections.singletonList(referenceInfoDo));
        if (CollectionUtils.isEmpty(detailInfoResList)) {
            return null;
        }
        return detailInfoResList.get(0);
    }

    @Override
    public ReferenceInfoRes getReferenceByUserId(Long userId) {
        ReferenceInfoDO referenceInfoDo = this.referenceDAO.getReferenceInfoByUserId(userId);
        return ReferenceUtil.transformReferenceRes(referenceInfoDo);
    }

    @Override
    public ReferenceInfoDO getReferenceDoByUserId(Long userId) {
        return this.referenceDAO.getReferenceInfoByUserId(userId);
    }

    @Override
    public List<ReferenceInfoDO> getReferenceInfoByUserIdList(List<Long> userIdList) {
        return this.referenceDAO.getReferenceInfoByUserIdList(userIdList);
    }

    @Override
    public ReferenceInfoDO getReferenceByPhone(String phone) {
        UserInfoDO userInfoDo = this.userService.getUserInfoDoByPhone(phone);
        if (userInfoDo == null) {
            return null;
        }
        return this.referenceDAO.getReferenceInfoByUserId(userInfoDo.getId());
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
        List<Long> departmentIdList = new ArrayList<>(referenceInfoDoList.size());
        for (ReferenceInfoDO referenceInfoDO : referenceInfoDoList) {
            userIdList.add(referenceInfoDO.getUserId());
            departmentIdList.add(referenceInfoDO.getDepartmentId());
        }
        Map<Long, UserInfoRes> userInfoResMap = this.userService.getUserInfoRes(userIdList);
        Map<Long, DepartmentDetailRes> departmentDetailResMap = this.departmentService.getDepartmentDetailRes(
            departmentIdList);
        return referenceInfoDoList.stream().map(
            referenceInfoDo -> this.transformReferenceDetailRes(referenceInfoDo, userInfoResMap,
                departmentDetailResMap)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private ReferenceDetailInfoRes transformReferenceDetailRes(ReferenceInfoDO referenceInfoDo,
        Map<Long, UserInfoRes> userInfoResMap, Map<Long, DepartmentDetailRes> departmentDetailResMap) {
        if (referenceInfoDo == null) {
            return null;
        }
        ReferenceDetailInfoRes referenceDetailInfoRes = new ReferenceDetailInfoRes();
        BeanUtils.copyProperties(referenceInfoDo, referenceDetailInfoRes);
        referenceDetailInfoRes.setReferenceId(referenceInfoDo.getId());
        if (MapUtils.isNotEmpty(userInfoResMap)) {
            referenceDetailInfoRes.setUserInfoRes(userInfoResMap.get(referenceInfoDo.getUserId()));
        }
        if (MapUtils.isNotEmpty(departmentDetailResMap)) {
            referenceDetailInfoRes.setDepartmentDetailRes(
                departmentDetailResMap.get(referenceInfoDo.getDepartmentId()));
        }

        return referenceDetailInfoRes;
    }
}
