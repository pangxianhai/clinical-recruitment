package com.andy.recruitment.region.constant;

import com.xgimi.commons.base.BaseType;
import java.util.List;

/**
 * 地区类型定义
 *
 * @author 庞先海 2018-12-26
 */
public class RegionType extends BaseType {

    public final static RegionType COUNTRY = new RegionType(0, "国家");

    public final static RegionType PROVINCE = new RegionType(1, "省");

    public final static RegionType CITY = new RegionType(2, "城市");

    public final static RegionType CANTONAL = new RegionType(3, "地区");

    protected RegionType(int code, String desc) {
        super(code, desc);
    }

    public static RegionType parse(Integer code) {
        if (code == null) {
            return null;
        }
        return BaseType.parse(RegionType.getValues(), code);
    }

    public static List<RegionType> getValues() {
        return BaseType.getValues(RegionType.class);
    }
}
