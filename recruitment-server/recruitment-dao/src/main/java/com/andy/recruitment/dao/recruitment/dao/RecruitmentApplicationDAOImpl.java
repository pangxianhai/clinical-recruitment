package com.andy.recruitment.dao.recruitment.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.recruitment.constant.RecruitmentApplicationStatus;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationQuery;
import com.andy.recruitment.dao.recruitment.mapper.RecruitmentApplicationMapper;
import com.soyoung.base.mybatis.paginator.Page;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.PageUtil;
import com.soyoung.base.page.Paginator;
import com.soyoung.base.util.CollectionUtil;
import com.soyoung.base.util.asserts.AssertUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 招募申请记录DAO实现
 *
 * @author 庞先海 2020-01-26
 */
@Service
public class RecruitmentApplicationDAOImpl implements RecruitmentApplicationDAO {

    private final RecruitmentApplicationMapper recruitmentApplicationMapper;

    @Autowired
    public RecruitmentApplicationDAOImpl(RecruitmentApplicationMapper recruitmentApplicationMapper) {
        this.recruitmentApplicationMapper = recruitmentApplicationMapper;
    }

    @Override
    public void addRecruitmentApplication(RecruitmentApplicationDO applicationDo, String operator) {
        RecruitmentApplicationDO existApplicationInfo = this.getRecruitmentApplicationInfo(
            applicationDo.getPatientUserId(), applicationDo.getRecruitmentId());
        AssertUtil.assertTrue(null == existApplicationInfo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_APPLICATION_HAS_APPLICATION);
        });
        applicationDo.setCreatedBy(operator);
        applicationDo.setCreatedTime(LocalDateTime.now());
        applicationDo.setStatus(RecruitmentApplicationStatus.NOT_ACCEDE);
        int count = this.recruitmentApplicationMapper.insert(applicationDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_APPLICATION_ADD_FAILED);
        });
    }

    @Override
    public void updateRecruitmentApplication(RecruitmentApplicationDO applicationDo, String operator) {
        AssertUtil.assertNull(applicationDo.getId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_APPLICATION_ID_EMPTY);
        });
        applicationDo.setUpdatedBy(operator);
        applicationDo.setUpdatedTime(LocalDateTime.now());
        int count = this.recruitmentApplicationMapper.update(applicationDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_APPLICATION_UPDATE_FAILED);
        });
    }

    @Override
    public PageResult<RecruitmentApplicationDO> getRecruitmentApplicationInfo(RecruitmentApplicationQuery queryParam,
        Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<RecruitmentApplicationDO> applicationDoList = this.recruitmentApplicationMapper.select(queryParam, page);
        return new PageResult<>(applicationDoList, PageUtil.transformToPaginator(page));
    }

    @Override
    public RecruitmentApplicationDO getRecruitmentApplicationInfo(Long applicationId) {
        if (null == applicationId) {
            return null;
        }
        RecruitmentApplicationQuery queryParam = new RecruitmentApplicationQuery();
        queryParam.setApplicationId(applicationId);
        List<RecruitmentApplicationDO> applicationDoList = this.recruitmentApplicationMapper.select(queryParam);
        return CollectionUtil.parseOne(applicationDoList, t -> t);

    }

    @Override
    public RecruitmentApplicationDO getRecruitmentApplicationInfo(Long patientUserId, Long recruitmentId) {
        if (null == patientUserId || recruitmentId == null) {
            return null;
        }
        RecruitmentApplicationQuery queryParam = new RecruitmentApplicationQuery();
        queryParam.setPatientUserId(patientUserId);
        queryParam.setRecruitmentId(recruitmentId);
        List<RecruitmentApplicationDO> applicationDoList = this.recruitmentApplicationMapper.select(queryParam);
        return CollectionUtil.parseOne(applicationDoList, t -> t);

    }
}
