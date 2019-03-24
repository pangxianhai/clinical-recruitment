package com.andy.recruitment.web.controller.recruitment.response;

/**
 * 研究中心VO
 *
 * @author 庞先海 2019-02-06
 */
public class ResearchCenterVO {

    /**
     * 中心ID
     */
    private Long centerId;

    /**
     * 研究中心名称
     */
    private String name;

    /**
     * 研究中心地址
     */
    private String address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
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
