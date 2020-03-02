package com.andy.recruitment.biz.recruitment.service;

import com.andy.recruitment.dao.recruitment.dao.RecruitmentApplicationDAO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 招募申请记录服务实现
 *
 * @author 庞先海 2020-03-01
 */
@Service
public class RecruitmentApplicationServiceImpl implements RecruitmentApplicationService {

    private final RecruitmentApplicationDAO recruitmentApplicationDAO;

    @Autowired
    public RecruitmentApplicationServiceImpl(RecruitmentApplicationDAO recruitmentApplicationDAO) {
        this.recruitmentApplicationDAO = recruitmentApplicationDAO;
    }

    @Override
    public void addRecruitmentApplication(RecruitmentApplicationDO applicationDo, String operator) {
        this.recruitmentApplicationDAO.addRecruitmentApplication(applicationDo, operator);
    }

    @Override
    public PageResult<RecruitmentApplicationDO> getRecruitmentApplicationInfo(RecruitmentApplicationQuery queryParam,
        Paginator paginator) {
        return recruitmentApplicationDAO.getRecruitmentApplicationInfo(queryParam, paginator);
    }
}
