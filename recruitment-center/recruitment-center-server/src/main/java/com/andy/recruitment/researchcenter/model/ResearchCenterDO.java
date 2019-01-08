package com.andy.recruitment.researchcenter.model;

import com.xgimi.base.BaseDO;
import com.xgimi.commons.util.JsonUtil;

/**
 * 研究中心DO
 *
 * @author 庞先海 2019-01-08
 */
public class ResearchCenterDO extends BaseDO {

    /**
     * 招募ID
     */
    private Long recruitmentId;
    /**
     * 中心名称
     */
    private String name;
    /**
     * 省ID
     */
    private Long provinceId;
    /**
     * 城市ID
     */
    private Long cityId;
    /**
     * 区ID
     */
    private Long districtId;

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

    public Long getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(Long recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }
}
