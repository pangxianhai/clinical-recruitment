package com.andy.recruitment.researchcenter.ao;

import com.andy.recruitment.researchcenter.model.ResearchCenterInfo;
import com.andy.recruitment.researchcenter.service.ResearchCenterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 研究中心信息AO实现
 *
 * @author 庞先海 2019-01-09
 */
@Component
public class ResearchCenterAOImpl implements ResearchCenterAO {

    private final ResearchCenterService researchCenterService;

    @Autowired
    public ResearchCenterAOImpl(ResearchCenterService researchCenterService) {
        this.researchCenterService = researchCenterService;
    }

    @Override
    public void addResearchCenter(ResearchCenterInfo centerInfo, String operator) {
        this.researchCenterService.addResearchCenter(centerInfo, operator);
    }

    @Override
    public void updateResearchCenter(ResearchCenterInfo centerInfo, String operator) {
        this.researchCenterService.updateResearchCenter(centerInfo, operator);
    }

    @Override
    public void deleteResearchCenterInfo(Long centerId) {
        this.researchCenterService.deleteResearchCenterInfo(centerId);
    }

    @Override
    public List<ResearchCenterInfo> getResearchCenterByRecruitmentId(Long recruitmentId) {
        return this.researchCenterService.getResearchCenterByRecruitmentId(recruitmentId);
    }
}
