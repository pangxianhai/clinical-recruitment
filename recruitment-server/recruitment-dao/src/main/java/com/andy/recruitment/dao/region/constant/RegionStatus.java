package com.andy.recruitment.dao.region.constant;

import com.soyoung.base.type.BaseType;

/**
 * 地区状态定义
 *
 * @author 庞先海 2018-12-26
 */
public class RegionStatus extends BaseType {

    private static final long serialVersionUID = - 8335119225348521737L;

    public final static RegionStatus INVALID = new RegionStatus(0, "无效");

    public final static RegionStatus VALID = new RegionStatus(1, "有效");


    protected RegionStatus(int code, String desc) {
        super(code, desc);
    }
}
