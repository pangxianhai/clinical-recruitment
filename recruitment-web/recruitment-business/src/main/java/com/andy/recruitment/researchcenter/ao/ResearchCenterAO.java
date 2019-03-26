package com.andy.recruitment.researchcenter.ao;

import com.andy.recruitment.researchcenter.model.ResearchCenterInfo;
import java.util.List;

/**
 * 研究中心信息AO
 *
 * @author 庞先海 2019-01-09
 */
public interface ResearchCenterAO {

    /**
     * 添加招募信息的研究中心
     *
     * @param recruitmentId  招募ID
     * @param centerInfoList 研究中心列表
     * @param operator       操作人
     */
    void addResearchCenter(Long recruitmentId, List<ResearchCenterInfo> centerInfoList, String operator);

    /**
     * 更新研究中心
     *
     * @param recruitmentId  招募信息ID
     * @param centerInfoList 研究中心列表
     * @param operator       操作人
     */
    void updateResearchCenter(Long recruitmentId, List<ResearchCenterInfo> centerInfoList, String operator);

    /**
     * 根据招募ID查询研究中心
     *
     * @param recruitmentId 招募ID
     * @return 研究中心信息
     */
    List<ResearchCenterInfo> getResearchCenterByRecruitmentId(Long recruitmentId);
}
