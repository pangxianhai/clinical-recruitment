package com.andy.recruitment.web.controller.region.response;

import com.xgimi.commons.util.JsonUtil;
import java.util.List;

/**
 * 地区VO
 *
 * @author 庞先海 2019-01-10
 */
public class RegionVO {

    private Long regionId;

    private String label;

    private String value;

    private List<RegionVO> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
