package com.andy.recruitment.web.controller.recruitment.request;

import java.io.Serializable;

/**
 * 研究中心添加参数
 *
 * @author 庞先海 2019-02-06
 */
public class ResearchCenterAddRQ implements Serializable {

    /**
     * 研究中心名称
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
     * 地区ID
     */
    private Long districtId;

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
