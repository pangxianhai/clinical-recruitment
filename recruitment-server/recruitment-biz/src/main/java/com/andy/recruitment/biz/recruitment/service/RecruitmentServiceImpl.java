package com.andy.recruitment.biz.recruitment.service;

import com.andy.recruitment.dao.recruitment.dao.RecruitmentDAO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 招募项目服务接口实现
 *
 * @author 庞先海 2020-01-30
 */
@Service
public class RecruitmentServiceImpl implements RecruitmentService {

    private final RecruitmentDAO recruitmentDAO;

    @Autowired
    public RecruitmentServiceImpl(RecruitmentDAO recruitmentDAO) {
        this.recruitmentDAO = recruitmentDAO;
    }

    @Override
    public PageResult<RecruitmentInfoDO> getRecruitmentInfo(RecruitmentQuery queryParam, Paginator paginator) {
        return this.recruitmentDAO.getRecruitmentInfo(queryParam, paginator);
    }
}
