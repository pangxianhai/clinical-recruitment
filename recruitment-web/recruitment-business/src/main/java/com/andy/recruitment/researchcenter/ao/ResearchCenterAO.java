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
     * 添加研究中心
     *
     * @param centerInfo 研究中心信息
     * @param operator   操作人
     */
    void addResearchCenter(ResearchCenterInfo centerInfo, String operator);

    /**
     * 更新研究中心信息
     *
     * @param centerInfo 研究中心信息
     * @param operator   操作人
     */
    void updateResearchCenter(ResearchCenterInfo centerInfo, String operator);

    /**
     * 删除研究中心信息
     *
     * @param centerId 研究中心ID
     */
    void deleteResearchCenterInfo(Long centerId);

    /**
     * 根据招募ID查询研究中心
     *
     * @param recruitmentId 招募ID
     * @return 研究中心信息
     */
    List<ResearchCenterInfo> getResearchCenterByRecruitmentId(Long recruitmentId);
}
