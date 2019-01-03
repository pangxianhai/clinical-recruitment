package com.andy.recruitment.recruitment.service;

import com.andy.recruitment.exception.RecruitmentErrorCode;
import com.andy.recruitment.exception.RecruitmentException;
import com.andy.recruitment.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.recruitment.mapper.RecruitmentMapper;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.recruitment.model.RecruitmentInfoDO;
import com.andy.recruitment.recruitment.model.RecruitmentQueryParam;
import com.andy.recruitment.recruitment.util.RecruitmentUtil;
import com.xgimi.center.util.PageUtil;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.DateUtil;
import com.xgimi.commons.util.asserts.AssertUtil;
import com.xgimi.mybatis.paginator.Page;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 招募信息服务
 *
 * @author 庞先海 2018-12-29
 */
@Service
public class RecruitmentServiceImpl implements RecruitmentService {

    private final RecruitmentMapper recruitmentMapper;

    @Autowired
    public RecruitmentServiceImpl(RecruitmentMapper recruitmentMapper) {
        this.recruitmentMapper = recruitmentMapper;
    }

    @Override
    public void addRecruitmentInfo(RecruitmentInfo recruitmentInfo, String operator) {
        RecruitmentInfoDO recruitmentInfoDO = RecruitmentUtil.transformRecruitmentInfoDO(recruitmentInfo);
        recruitmentInfoDO.setStatus(RecruitmentStatus.IN_PROCESS);
        recruitmentInfoDO.setCreatedBy(operator);
        recruitmentInfoDO.setCreatedTime(new Timestamp(DateUtil.currentMilliseconds()));
        int count = this.recruitmentMapper.insert(recruitmentInfoDO);
        AssertUtil.assertBoolean(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_ADD_FAILE);
        });
    }

    @Override
    public void updateRecruitmentInfo(RecruitmentInfo recruitmentInfo, String operator) {
        AssertUtil.assertNull(recruitmentInfo.getRecruitmentId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_ID_EMPTY);
        });
        RecruitmentInfoDO recruitmentInfoDO = RecruitmentUtil.transformRecruitmentInfoDO(recruitmentInfo);
        recruitmentInfoDO.setUpdatedBy(operator);
        recruitmentInfoDO.setUpdatedTime(new Timestamp(DateUtil.currentMilliseconds()));
        int count = this.recruitmentMapper.update(recruitmentInfoDO);
        AssertUtil.assertBoolean(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.RECRUITMENT_UPDATE_FAILE);
        });
    }

    @Override
    public PageResult<RecruitmentInfo> getRecruitmentInfo(RecruitmentQueryParam queryParam, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<RecruitmentInfoDO> recruitmentInfoDOList = this.recruitmentMapper.select(queryParam, page);
        List<RecruitmentInfo> recruitmentInfoList = RecruitmentUtil.transformRecruitmentInfo(recruitmentInfoDOList);
        return new PageResult<>(recruitmentInfoList, PageUtil.transformToPaginator(page));
    }

    @Override
    public RecruitmentInfo getRecruitmentInfoById(Long recruitmentId) {
        if (null == recruitmentId) {
            return null;
        }
        RecruitmentQueryParam queryParam = new RecruitmentQueryParam();
        queryParam.setRecruitmentId(recruitmentId);
        List<RecruitmentInfoDO> recruitmentInfoDOList = this.recruitmentMapper.select(queryParam);
        return CollectionUtil.parseOne(recruitmentInfoDOList, RecruitmentUtil::transformRecruitmentInfo);
    }
}
