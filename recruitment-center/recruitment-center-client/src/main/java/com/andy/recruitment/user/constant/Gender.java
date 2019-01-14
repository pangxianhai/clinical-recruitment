package com.andy.recruitment.user.constant;

import com.xgimi.commons.base.BaseType;
import com.xgimi.commons.util.NumberUtil;
import java.util.List;

/**
 * 性别定义
 *
 * @author 庞先海 2018-12-28
 */
public class Gender extends BaseType {

    public static final Gender MALE = new Gender(1, "男");

    public static final Gender FEMALE = new Gender(2, "女");

    public static final Gender UNKNOWN = new Gender(0, "未知");

    public Gender(int code, String desc) {
        super(code, desc);
    }

    public static List<Gender> getValues() {
        return BaseType.getValues(Gender.class);
    }

    public static Gender parse(Integer code) {
        return BaseType.parse(Gender.getValues(), code);
    }

    public static Gender parse(String code) {
        return Gender.parse(NumberUtil.parseInt(code));
    }
}
