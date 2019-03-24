package com.andy.recruitment.researchcenter.ao;

import com.andy.recruitment.researchcenter.model.ResearchCenterInfo;
import com.andy.recruitment.researchcenter.service.ResearchCenterService;
import com.xgimi.commons.util.CollectionUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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
    public void addResearchCenter(Long recruitmentId, List<ResearchCenterInfo> centerInfoList, String operator) {
        List<ResearchCenterInfo> researchCenterInfoList = this.researchCenterService.getResearchCenterByRecruitmentId(
            recruitmentId);
        if (CollectionUtil.isNotEmpty(researchCenterInfoList)) {
            for (ResearchCenterInfo researchCenterInfo : researchCenterInfoList) {
                this.researchCenterService.deleteResearchCenterInfo(researchCenterInfo.getCenterId());
            }
        }
        if (CollectionUtil.isNotEmpty(centerInfoList)) {
            for (ResearchCenterInfo researchCenterInfo : centerInfoList) {
                researchCenterInfo.setRecruitmentId(recruitmentId);
                this.researchCenterService.addResearchCenter(researchCenterInfo, operator);
            }
        }
    }

    @Override
    public void updateResearchCenter(Long recruitmentId, List<ResearchCenterInfo> centerInfoList, String operator) {
        List<ResearchCenterInfo> researchCenterInfoList = this.researchCenterService.getResearchCenterByRecruitmentId(
            recruitmentId);
        Set<Long> sourceCenterIdSet = new HashSet<>();
        if (CollectionUtil.isNotEmpty(researchCenterInfoList)) {
            sourceCenterIdSet = researchCenterInfoList.stream().map(ResearchCenterInfo::getCenterId).filter(
                Objects::nonNull).collect(Collectors.toSet());
        }
        Set<Long> updateIdSet = new HashSet<>();
        for (ResearchCenterInfo centerInfo : centerInfoList) {
            if (null == centerInfo.getCenterId()) {
                centerInfo.setRecruitmentId(recruitmentId);
                this.researchCenterService.addResearchCenter(centerInfo, operator);
            } else {
                this.researchCenterService.updateResearchCenter(centerInfo, operator);
                updateIdSet.add(centerInfo.getCenterId());
            }
        }
        sourceCenterIdSet.removeAll(updateIdSet);
        if (CollectionUtil.isNotEmpty(sourceCenterIdSet)) {
            for (Long centerId : sourceCenterIdSet) {
                this.researchCenterService.deleteResearchCenterInfo(centerId);
            }
        }
    }

    @Override
    public List<ResearchCenterInfo> getResearchCenterByRecruitmentId(Long recruitmentId) {
        return this.researchCenterService.getResearchCenterByRecruitmentId(recruitmentId);
    }
}
