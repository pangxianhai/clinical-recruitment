package com.andy.recruitment.recruitment.service;

import com.andy.recruitment.recruitment.mapper.RecruitmentMapper;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.recruitment.model.RecruitmentQueryParam;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
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

    }

    @Override
    public void updateRecruitmentInfo(RecruitmentInfo recruitmentInfo, String operator) {

    }

    @Override
    public PageResult<RecruitmentInfo> getRecruitmentInfo(RecruitmentQueryParam queryParam, Paginator paginator) {
        return null;
    }

    @Override
    public RecruitmentInfo getRecruitmentInfoById(Long recruitmentId) {
        return null;
    }
}
