package com.andy.recruitment.region.model;

import com.andy.recruitment.region.constant.RegionStatus;
import com.andy.recruitment.region.constant.RegionType;
import com.xgimi.base.BaseDO;

/**
 * 地区信息DO
 *
 * @author 庞先海 2018-12-26
 */
public class RegionDO extends BaseDO {

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 地区名字
     */
    private String regionName;

    /**
     * 地区类型
     */
    private RegionType regionType;

    /**
     * 地区状态
     */
    private RegionStatus regionStatus;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public RegionType getRegionType() {
        return regionType;
    }

    public void setRegionType(RegionType regionType) {
        this.regionType = regionType;
    }

    public RegionStatus getRegionStatus() {
        return regionStatus;
    }

    public void setRegionStatus(RegionStatus regionStatus) {
        this.regionStatus = regionStatus;
    }
}
