package com.andy.recruitment.researchcenter.model;

import java.io.Serializable;

/**
 * 研究中心查询条件
 *
 * @author 庞先海 2019-01-08
 */
public class ResearchCenterQueryParam implements Serializable {

    /**
     * 招募ID
     */
    private Long recruitmentId;

    public Long getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(Long recruitmentId) {
        this.recruitmentId = recruitmentId;
    }
}
