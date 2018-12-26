package com.andy.recruitment.region.constant;

import com.xgimi.commons.base.BaseType;

/**
 * 地区状态定义
 *
 * @author 庞先海 2018-12-26
 */
public class RegionStatus extends BaseType {

    public final static RegionStatus INVALID = new RegionStatus(0, "无效");

    public final static RegionStatus VALID = new RegionStatus(1, "有效");

    protected RegionStatus(int code, String desc) {
        super(code, desc);
    }
}
