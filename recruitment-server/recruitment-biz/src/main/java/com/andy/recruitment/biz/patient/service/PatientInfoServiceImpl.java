package com.andy.recruitment.biz.patient.service;

import com.andy.recruitment.api.patient.response.PatientInfoDetailRes;
import com.andy.recruitment.api.patient.response.PatientInfoRes;
import com.andy.recruitment.api.user.response.UserInfoRes;
import com.andy.recruitment.biz.patient.util.PatientInfoUtil;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.common.patient.constant.PatientStatus;
import com.andy.recruitment.dao.patient.dao.PatientInfoDAO;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.patient.entity.PatientQuery;
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
 * 患者信息服务接口实现
 *
 * @author 庞先海 2020-02-16
 */
@Service
public class PatientInfoServiceImpl implements PatientInfoService {

    private final PatientInfoDAO patientInfoDAO;

    private final TransactionTemplate transactionTemplate;

    private final RegionService regionService;

    private final UserService userService;

    @Autowired
    public PatientInfoServiceImpl(PatientInfoDAO patientInfoDAO, TransactionTemplate transactionTemplate,
        RegionService regionService, UserService userService) {
        this.patientInfoDAO = patientInfoDAO;
        this.transactionTemplate = transactionTemplate;
        this.regionService = regionService;
        this.userService = userService;
    }

    @Override
    public Long registerPatient(PatientInfoDO patientInfoDo, UserInfoDO userInfoDo, String operator) {
        return transactionTemplate.execute((status) -> {
            Long userId = this.userService.registerUser(userInfoDo, operator);
            patientInfoDo.setUserId(userId);
            PatientInfoDO existPatientInfoDo = this.patientInfoDAO.getPatientInfoByUserId(userId);
            if (existPatientInfoDo == null) {
                patientInfoDo.setStatus(PatientStatus.NORMAL);
                this.patientInfoDAO.addPatientInfo(patientInfoDo, operator);
            } else {
                patientInfoDo.setId(existPatientInfoDo.getId());
                this.patientInfoDAO.updatePatientInfo(patientInfoDo, operator);
            }
            return userId;
        });
    }

    @Override
    public void updatePatient(PatientInfoDO patientInfoDo, UserInfoDO userInfoDo, String operator) {
        PatientInfoDO existPatientInfoDo = this.patientInfoDAO.getPatientInfoById(patientInfoDo.getId());
        AssertUtil.assertNull(existPatientInfoDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.PATIENT_NOT_EXIST);
        });
        transactionTemplate.execute((status) -> {
            this.patientInfoDAO.updatePatientInfo(patientInfoDo, operator);
            userInfoDo.setId(existPatientInfoDo.getUserId());
            this.userService.updateUserInfo(userInfoDo, operator);
            return null;
        });
    }

    @Override
    public void updatePatientStatus(Long patientId, PatientStatus status, String operator) {
        PatientInfoDO patientInfoDo = new PatientInfoDO();
        patientInfoDo.setId(patientId);
        patientInfoDo.setStatus(status);
        this.patientInfoDAO.updatePatientInfo(patientInfoDo, operator);
    }

    @Override
    public PageResult<PatientInfoDetailRes> getPatient(PatientQuery query, Paginator paginator) {
        PageResult<PatientInfoDO> pageResult = this.patientInfoDAO.getPatientInfo(query, paginator);
        List<PatientInfoDetailRes> detailResList = this.transformReferenceDetailRes(pageResult.getData());
        return new PageResult<>(detailResList, pageResult.getPaginator());
    }

    @Override
    public PatientInfoDetailRes getPatientById(Long patientId) {
        PatientInfoDO patientInfoDo = this.patientInfoDAO.getPatientInfoById(patientId);
        List<PatientInfoDetailRes> detailResList = this.transformReferenceDetailRes(
            Collections.singletonList(patientInfoDo));
        if (CollectionUtils.isEmpty(detailResList)) {
            return null;
        }
        return detailResList.get(0);
    }

    @Override
    public PatientInfoRes getPatientByUserId(Long userId) {
        PatientInfoDO patientInfoDo = this.patientInfoDAO.getPatientInfoByUserId(userId);
        return PatientInfoUtil.transformReferenceRes(patientInfoDo, regionService);
    }

    @Override
    public PatientInfoDO getPatientDoByUserId(Long userId) {
        return this.patientInfoDAO.getPatientInfoByUserId(userId);
    }

    @Override
    public List<PatientInfoDO> getPatientInfoByUserIdList(List<Long> userIdList) {
        return this.patientInfoDAO.getPatientInfoByUserIdList(userIdList);
    }

    @Override
    public Map<Long, PatientInfoDetailRes> getReferenceDetailRes(List<Long> userIdList) {
        List<PatientInfoDO> patientInfoDoList = this.patientInfoDAO.getPatientInfoByUserIdList(userIdList);
        List<PatientInfoDetailRes> patientInfoDetailResList = transformReferenceDetailRes(patientInfoDoList);
        if (CollectionUtils.isEmpty(patientInfoDetailResList)) {
            return Collections.emptyMap();
        }
        return patientInfoDetailResList.stream().collect(
            Collectors.toMap(PatientInfoDetailRes::getUserId, Function.identity(), (d1, d2) -> d1));

    }

    public List<PatientInfoDetailRes> transformReferenceDetailRes(List<PatientInfoDO> patientInfoDoList) {
        if (CollectionUtils.isEmpty(patientInfoDoList)) {
            return Collections.emptyList();
        }
        List<Long> userIdList = new ArrayList<>(patientInfoDoList.size());
        for (PatientInfoDO patientInfoDo : patientInfoDoList) {
            userIdList.add(patientInfoDo.getUserId());
        }
        Map<Long, UserInfoRes> userInfoResMap = this.userService.getUserInfoRes(userIdList);
        return patientInfoDoList.stream().map(
            referenceInfoDo -> this.transformReferenceDetailRes(referenceInfoDo, userInfoResMap)).filter(
            Objects::nonNull).collect(Collectors.toList());
    }

    private PatientInfoDetailRes transformReferenceDetailRes(PatientInfoDO patientInfoDo,
        Map<Long, UserInfoRes> userInfoResMap) {
        if (patientInfoDo == null) {
            return null;
        }
        PatientInfoDetailRes patientInfoDetailRes = new PatientInfoDetailRes();
        BeanUtils.copyProperties(patientInfoDo, patientInfoDetailRes);
        patientInfoDetailRes.setPatientId(patientInfoDo.getId());
        patientInfoDetailRes.setUserInfoRes(userInfoResMap.get(patientInfoDo.getUserId()));
        patientInfoDetailRes.setAddress(
            this.regionService.parseAddressName(patientInfoDo.getProvinceId(), patientInfoDo.getCityId(),
                patientInfoDo.getDistrictId()));
        return patientInfoDetailRes;
    }
}
