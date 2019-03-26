package com.andy.recruitment.recruitment.ao;

import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.recruitment.model.RecruitmentQueryParam;
import com.andy.recruitment.recruitment.service.RecruitmentService;
import com.andy.recruitment.researchcenter.model.ResearchCenterInfo;
import com.andy.recruitment.researchcenter.service.ResearchCenterService;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.StringUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 招募信息AO实现
 *
 * @author 庞先海 2019-01-06
 */
@Component
public class RecruitmentAOImpl implements RecruitmentAO {

    private final RecruitmentService recruitmentService;

    private final ResearchCenterService researchCenterService;

    @Autowired
    public RecruitmentAOImpl(RecruitmentService recruitmentService, ResearchCenterService researchCenterService) {
        this.recruitmentService = recruitmentService;
        this.researchCenterService = researchCenterService;
    }

    @Override
    public Long addRecruitmentInfo(RecruitmentInfo recruitmentInfo, String operator) {
        return this.recruitmentService.addRecruitmentInfo(recruitmentInfo, operator);
    }

    @Override
    public void updateRecruitmentInfo(RecruitmentInfo recruitmentInfo, String operator) {
        this.recruitmentService.updateRecruitmentInfo(recruitmentInfo, operator);
    }

    @Override
    public PageResult<RecruitmentInfo> getRecruitmentInfo(RecruitmentQueryParam queryParam, Paginator paginator) {
        if (StringUtil.isEmpty(paginator.getOrderSegment())) {
            paginator.setOrderSegment("created_time.desc");
        }
        PageResult<RecruitmentInfo> pageResult = this.recruitmentService.getRecruitmentInfo(queryParam, paginator);
        if (CollectionUtil.isNotEmpty(pageResult.getData())) {
            for (RecruitmentInfo recruitmentInfo : pageResult.getData()) {
                List<ResearchCenterInfo> centerInfoList = researchCenterService.getResearchCenterByRecruitmentId(
                    recruitmentInfo.getRecruitmentId());
                recruitmentInfo.setResearchCenterInfoList(centerInfoList);
            }
        }
        return pageResult;
    }

    @Override
    public RecruitmentInfo getRecruitmentInfoById(Long recruitmentId) {
        return this.recruitmentService.getRecruitmentInfoById(recruitmentId);
    }
}
