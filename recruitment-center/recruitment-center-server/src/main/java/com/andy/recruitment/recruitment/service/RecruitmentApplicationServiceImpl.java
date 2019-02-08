package com.andy.recruitment.recruitment.service;

import com.andy.recruitment.exception.RecruitmentErrorCode;
import com.andy.recruitment.exception.RecruitmentException;
import com.andy.recruitment.recruitment.constant.RecruitmentApplicationStatus;
import com.andy.recruitment.recruitment.mapper.RecruitmentApplicationMapper;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationDO;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationInfo;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationQueryParam;
import com.andy.recruitment.recruitment.util.RecruitmentUtil;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.DateUtil;
import com.xgimi.commons.util.asserts.AssertUtil;
import com.xgimi.mybatis.paginator.Page;
import com.xgimi.util.PageUtil;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 招募信息申请服务
 *
 * @author 庞先海 2019-01-24
 */
@Service
public class RecruitmentApplicationServiceImpl implements RecruitmentApplicationService {

    private final RecruitmentApplicationMapper recruitmentApplicationMapper;

    @Autowired
    public RecruitmentApplicationServiceImpl(RecruitmentApplicationMapper recruitmentApplicationMapper) {
        this.recruitmentApplicationMapper = recruitmentApplicationMapper;
    }

    @Override
    public void addRecruitmentApplication(RecruitmentApplicationInfo applicationInfo, String operator) {
        RecruitmentApplicationInfo existApplicationInfo = getRecruitmentApplicationInfo(
            applicationInfo.getRecruitmentId(), applicationInfo.getPatientId());
        AssertUtil.assertBoolean(null == existApplicationInfo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_APPLICATION_HAS_APPLICATION);
        });
        RecruitmentApplicationDO applicationDO = RecruitmentUtil.transformApplicationDO(applicationInfo);
        applicationDO.setCreatedBy(operator);
        applicationDO.setCreatedTime(new Timestamp(DateUtil.currentMilliseconds()));
        applicationDO.setStatus(RecruitmentApplicationStatus.NOT_ACCEDE);
        int count = this.recruitmentApplicationMapper.insert(applicationDO);
        AssertUtil.assertBoolean(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_APPLICATION_ADD_FAILED);
        });
    }

    @Override
    public void updateRecruitmentApplication(RecruitmentApplicationInfo applicationInfo, String operator) {
        AssertUtil.assertNull(applicationInfo.getApplicationId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_APPLICATION_ID_EMPTY);
        });
        RecruitmentApplicationDO applicationDO = RecruitmentUtil.transformApplicationDO(applicationInfo);
        applicationDO.setUpdatedBy(operator);
        applicationDO.setUpdatedTime(new Timestamp(DateUtil.currentMilliseconds()));
        int count = this.recruitmentApplicationMapper.update(applicationDO);
        AssertUtil.assertBoolean(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_APPLICATION_UPDATE_FAILED);
        });
    }

    @Override
    public PageResult<RecruitmentApplicationInfo> getRecruitmentApplicationInfo(
        RecruitmentApplicationQueryParam queryParam, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<RecruitmentApplicationDO> applicationDOList = this.recruitmentApplicationMapper.select(queryParam, page);
        List<RecruitmentApplicationInfo> applicationInfoList = RecruitmentUtil.transformApplicationInfo(
            applicationDOList);
        return new PageResult<>(applicationInfoList, PageUtil.transformToPaginator(page));
    }

    @Override
    public RecruitmentApplicationInfo getRecruitmentApplicationInfo(Long applicationId) {
        if (null == applicationId) {
            return null;
        }
        RecruitmentApplicationQueryParam queryParam = new RecruitmentApplicationQueryParam();
        queryParam.setApplicationId(applicationId);
        List<RecruitmentApplicationDO> applicationDOList = this.recruitmentApplicationMapper.select(queryParam);
        return CollectionUtil.parseOne(applicationDOList, RecruitmentUtil::transformApplicationInfo);
    }

    private RecruitmentApplicationInfo getRecruitmentApplicationInfo(Long recruitmentId, Long patientId) {
        if (null == recruitmentId || null == patientId) {
            return null;
        }
        RecruitmentApplicationQueryParam queryParam = new RecruitmentApplicationQueryParam();
        queryParam.setPatientId(patientId);
        queryParam.setRecruitmentId(recruitmentId);
        List<RecruitmentApplicationDO> applicationDOList = this.recruitmentApplicationMapper.select(queryParam);
        return CollectionUtil.parseOne(applicationDOList, RecruitmentUtil::transformApplicationInfo);
    }
}
