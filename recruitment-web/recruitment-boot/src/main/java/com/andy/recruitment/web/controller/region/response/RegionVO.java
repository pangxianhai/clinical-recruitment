package com.andy.recruitment.web.controller.region.response;

import com.xgimi.commons.util.JsonUtil;
import java.io.Serializable;
import java.util.List;

/**
 * 地区VO
 *
 * @author 庞先海 2019-01-10
 */
public class RegionVO implements Serializable {

    /**
     * 地区ID
     */
    private Long regionId;

    /**
     * 地区名字
     */
    private String regionName;

    /**
     * 子地区
     */
    private List<RegionVO> children;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public List<RegionVO> getChildren() {
        return children;
    }

    public void setChildren(List<RegionVO> children) {
        this.children = children;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
